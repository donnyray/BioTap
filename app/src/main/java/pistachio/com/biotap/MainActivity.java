package pistachio.com.biotap;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    // Passing score.
    public static double score = 8.0;

    protected Button button;

    protected View grid;

    protected ArrayList<TapInterface> taps;

    protected List<TapInterface> master;

    protected List<TapInterface> stdDev;

    protected boolean listening;

    protected long absoluteTapTime = 0;

    protected TapInterface currentTap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button = (Button)findViewById(R.id.start_btn);
        this.grid = findViewById(R.id.grid);
        this.taps = new ArrayList<>();
        this.listening = false;
        this.button.setOnClickListener(buttonListener);
        this.grid.setOnTouchListener(gridListener);


        FileInputStream in = null;
        try {

        in = openFileInput("average");
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String avg, dev;

            if (! bufferedReader.ready()) {
                in.close();
                throw new FileNotFoundException();
            }

            avg = bufferedReader.readLine();
            dev = bufferedReader.readLine();

            in.close();

            this.master = mapToSequence(avg);
            this.stdDev = mapToSequence(dev);
        } catch (FileNotFoundException e) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("There isn't a tap sequence registered.  Go back and hit register.")
                    .setCancelable(false)
                    .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Dialog d = (Dialog) dialog;
                            Intent myIntent = new Intent(((Dialog) dialog).getContext(), pistachio.com.biotap.Menu.class);
                            ((Dialog) dialog).getContext().startActivity(myIntent);
                    }
        });
            builder.create().show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    View.OnClickListener buttonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (listening) {
                Log.d("Button", "Stopped listening for taps...");
                startButton();

                analyzeTaps();

                taps = new ArrayList<>();
                absoluteTapTime = 0;
                currentTap = null;
            } else {
                Log.d("Button", "Started listening for taps.");
                stopButton();
            }

            listening = ! listening;
        }
    };

    View.OnTouchListener gridListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent e) {

            if (listening) {
                switch(e.getAction()) {
                    case android.view.MotionEvent.ACTION_DOWN:
                        if (absoluteTapTime == 0) {
                            absoluteTapTime = e.getEventTime();
                        }
                        currentTap = new Tap(e.getEventTime() - absoluteTapTime, (int)e.getX(), (int)e.getY());
                        Log.d("Touch", "Down: " + currentTap.toString());
                        break;
                    case android.view.MotionEvent.ACTION_UP:
                        currentTap.setDuration((e.getEventTime() - absoluteTapTime) - currentTap.getTime());
                        taps.add(currentTap);
                        Log.d("Touch", "Up: " + currentTap.toString());
                        break;
                }

                return true;
            }

            return false;
        }
    };

    protected void startButton() {
        button.setText("Start");
        button.setBackgroundColor(0xFF4CAF50);
    }

    protected void stopButton() {
        button.setText("Stop");
        button.setBackgroundColor(0xFFF44336);
    }

    protected void analyzeTaps() {

        if (this.taps.size() == 0) {
            return;
        }

        double score = Compare.dissimilarityScore(this.master, this.taps, this.stdDev);

        String message = "";
        String confirmation = "";


        if (score < MainActivity.score) {
            message = "You've been successfully authenticated.";
            confirmation = "OKAY";
            Statistics.success++;
            Statistics.score = (Statistics.score + score) / 2;
        } else if (score < 15.00) {
            message = "Hmm... Maybe try again...";
            confirmation = "If I must...";
            Statistics.fail++;
        } else {
            message = "Error, unrecognized tap sequence.";
            confirmation = "OKAY";
            Statistics.fail++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(confirmation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    /**
     * Map a string to sequence.
     *
     * @param line
     * @return
     */
    protected List<TapInterface> mapToSequence(String line) {
        ArrayList<TapInterface> sequence = new ArrayList<>();

        String[] taps = line.split(",");
        String[] unparsedTap;

        for (String tap : taps) {
            unparsedTap = tap.split("/");

            sequence.add(
                    new Tap(
                            Long.parseLong(unparsedTap[0]),
                            Long.parseLong(unparsedTap[1]),
                            Integer.parseInt(unparsedTap[2]),
                            Integer.parseInt(unparsedTap[3])
                    )
            );
        }

        return sequence;
    }

}

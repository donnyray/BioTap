package pistachio.com.biotap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AvgPassKeyActivity extends Activity {

    protected Button button;

    protected View grid;

    protected List<TapInterface> taps;

    protected List<List<TapInterface>> average;

    protected boolean listening;

    protected FileOutputStream file;

    protected long absoluteTapTime = 0;

    protected TapInterface currentTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg_pass_key);

        this.button = (Button)findViewById(R.id.start_btn);
        this.grid = findViewById(R.id.grid);
        this.taps = new ArrayList<>();
        this.average = new ArrayList<List<TapInterface>>();
        this.listening = false;
        this.button.setOnClickListener(buttonListener);
        this.grid.setOnTouchListener(gridListener);


        try {
            this.file = openFileOutput("average", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Go ahead and tap out three tap sequences, pressing start and stop between each one.")
                .setCancelable(false)
                .setPositiveButton("Ok, sounds good", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                });
        builder.create().show();
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

    protected View.OnClickListener buttonListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            if (listening) {
                Log.d("Button", "Stopped listening for taps...");
                startButton();

                if (average.size() >= 1 && average.get(0).size() != taps.size()) {
                    badTapPopUp();
                } else if (taps.size() <= 0) {
                    didNotTapPopUp();
                } else {

                    average.add(taps);

                    if (average.size() == 3) {
                        averageAndWriteToFile();

                        average = new ArrayList<List<TapInterface>>();

                        showPopUp();
                    }
                }
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

    protected void averageAndWriteToFile() {

        List<TapInterface> mean = Compare.meanSeq(this.average);
        List<TapInterface> stdDev = Compare.standDeviation(this.average);

        String data = sequenceToString(mean) + "\n" + sequenceToString(stdDev);

        try {
            file.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("You've successfully registered three tap sequences.")
                .setCancelable(false)
                .setPositiveButton("Back to menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        Intent myIntent = new Intent(((Dialog)dialog).getContext(), pistachio.com.biotap.Menu.class);
                        ((Dialog) dialog).getContext().startActivity(myIntent);
                    }
                });
        builder.create().show();
    }

    protected void badTapPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("That tap sequence wasn't even close to your first one... We'll throw it, try that one again.")
                .setCancelable(false)
                .setPositiveButton("Yeah I messed up", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    protected void didNotTapPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Did you forget to tap?")
                .setCancelable(false)
                .setPositiveButton("Yeah I did", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    protected String sequenceToString(List<TapInterface> taps) {
        String data = "";

        for (int i = 0; i < taps.size(); i++) {
            data += taps.get(i).toString();

            if (i != taps.size() - 1) {
                data += ",";
            }
        }

        return data;
    }
}





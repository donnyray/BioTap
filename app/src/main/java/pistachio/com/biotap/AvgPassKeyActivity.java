package pistachio.com.biotap;

import android.content.Context;
import android.app.Activity;
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


public class AvgPassKeyActivity extends Activity {

    protected Button button;

    protected View grid;

    protected ArrayList<Tap> taps;

    protected ArrayList<Tap> temp;

    protected boolean listening;

    protected FileOutputStream file;

    protected ProgressBar progressBar;

    private int progressBarStatus;

    private int sequenceCount = 0;

    private int tapSize = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg_pass_key);

        this.button = (Button) findViewById(R.id.start_btn);

        this.grid = findViewById(R.id.grid);

        this.taps = new ArrayList<>();

        this.temp = new ArrayList<>();

        this.listening = false;

        this.button.setOnClickListener(buttonListener);

        this.grid.setOnTouchListener(gridListener);

//        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);



        try {
            this.file = openFileOutput("avgPassKey.txt", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
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

    protected View.OnClickListener buttonListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            if (listening) {
                Log.d("Button", "Stopped listening.");
                button.setText("Start");
                button.setBackgroundColor(0xFF4CAF50);

                sequenceCount++;


                if(sequenceCount == 1 ) {
                    Iterator<Tap> tapIterator = taps.iterator();
                    tapSize = taps.size();
                    while (tapIterator.hasNext()) {
                        temp.add(tapIterator.next());
                    }
                    String avgData = taps.toString() + "\n";
                    try {
                        file.write(avgData.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // need to start over if taps.size() != tapsSize
                } else {
                    try {
                        Log.d("**SIZE**", String.valueOf(taps.size()));
                        Log.d("**SIZE**", String.valueOf(temp.size()));


/*                        for (int i = 0; i < taps.size(); i++) {
                            temp.set(i, (temp.get(i).avgUpdateTap(
                                    taps.get(i).getTime(),
                                    taps.get(i).getX(),
                                    taps.get(i).getY(),
                                    sequenceCount)));
                   } */


                        if(sequenceCount < 3) {
                            String avgData = taps.toString() + "\n";
                            try {
                                file.write(avgData.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String avgData = temp.toString() + "\n";
                            try {
                                file.write(avgData.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }

                taps = new ArrayList<>();

            } else {
                Log.d("Button", "Started listening.");
                button.setText("Stop");
                button.setBackgroundColor(0xFFF44336);

            }

            listening = !listening;

            if (sequenceCount == 3) {
                button.setText("Done");
                button.setBackgroundColor(0x9C9C9C);
            }

        }

    };


    View.OnTouchListener gridListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (listening) {
                Log.d("Touch", String.valueOf(event.getEventTime()));
                Log.d("X", String.valueOf(event.getX()));
                Log.d("Y", String.valueOf(event.getY()));

/*
                    taps.add(
                            Tap.record(event.getEventTime(), event.getX(), event.getY()));

*/
            }

            return false;
        }
    };
}





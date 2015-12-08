package pistachio.com.biotap;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    protected Button button;

    protected View grid;

    protected ArrayList<TapInterface> taps;

    protected boolean listening;

    protected FileOutputStream file;

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


        try {
            this.file = openFileOutput("tapSequence.txt", Context.MODE_APPEND);
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


    View.OnClickListener buttonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (listening) {
                Log.d("Button", "Stopped listening for taps...");
                startButton();
                String data = "";
                for (int i = 0; i < taps.size(); i++) {
                    data += taps.get(i).toString();

                    if (i != taps.size() - 1) {
                        data += ",";
                    }
                }
                data += "\n";
                try {
                    file.write(data.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                /*
                 * not sure if file for hold stats should
                 * be updated here or in some other class
                 * probably better to through it into what
                 * ever class is actually doing the judging,
                 * which im not sure what that is
                 */
                
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

}

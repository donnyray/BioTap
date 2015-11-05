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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends Activity {

    protected Button button;

    protected View grid;

    protected ArrayList<Tap> taps;

    protected boolean listening;

    protected FileOutputStream file;

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
                Log.d("Button", "Stopped listening.");
                button.setText("Start");
                button.setBackgroundColor(0xFF4CAF50);
                String data = taps.toString() + "\n";

                try {
                    file.write(data.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                taps = new ArrayList<>();

            } else {
                Log.d("Button", "Started listening.");
                button.setText("Stop");
                button.setBackgroundColor(0xFFF44336);

            }

            listening = ! listening;
        }
    };

    View.OnTouchListener gridListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (listening) {
                Log.d("Touch", String.valueOf(event.getEventTime()));
                Log.d("X", String.valueOf(event.getX()));
                Log.d("Y", String.valueOf(event.getY()));
                taps.add(
                        Tap.record(event.getEventTime(), event.getX(), event.getY())
                );
            }

            return false;
        }
    };

}

package pistachio.com.biotap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.util.Log;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Creates the List of SequenceTap's to be added to once start button is */
        /*  pressed, and saved to local storage once stop button is pressed.*/
        final ArrayList<SequenceTap> tapList = new ArrayList<SequenceTap>();

        // Creates a new instance of a timer for the start button.
        final Timer sequenceTimer = new SequenceTimer(false);
        // creates a variable of the button getting it by id
        final Button startBtn = (Button) findViewById(R.id.start_btn);

        // sets the click listener on the button, changing the status of the boolean value
        // to the button that can be used to lock out the touch area. The button also changes from
        // start to stop, and stop to start respectively
        // also have the current system time logged to the console when the button is pressed
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sequenceTimer.getTimer()) {
                    startBtn.setText("STOP");
                    sequenceTimer.setTimer(false);
                    Log.d("TIME STAMP: ", valueOf(System.currentTimeMillis()));
                } else {
                    startBtn.setText("START");
                    sequenceTimer.setTimer(true);
                    Log.d("TIME STAMP: ", valueOf(System.currentTimeMillis()));
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

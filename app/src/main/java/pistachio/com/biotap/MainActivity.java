package pistachio.com.biotap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.util.Log;
import android.widget.RelativeLayout;
import android.view.MotionEvent;

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
        /* Sets the entire layout as an area to be listened to for actions.*/
        /* May cause problems with button being in the layout.*/
        final RelativeLayout clickScreen = (RelativeLayout) findViewById(R.id.clickLayout);

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

        /* Listens for any clicks on the clickScreen Layout. */
        clickScreen.setOnTouchListener(new RelativeLayout.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent m) {
                /*A sequence tap is only created when this condition is met.*/
                if (sequenceTimer.getTimer()) {
                    /*Method creates a SequenceTap using the MotionEvent.*/
                    SequenceTap tap = (onScreenTouch(m));
                    /*Adds a sequence to the ArrayList tapList, and logs the action.*/
                    tapList.add(tap);
                    Log.d("TAP_ADDED", valueOf(System.currentTimeMillis()));
                }
                return true;
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

    /*Method called when a screen touch is heard during a tap sequence.*/
    private SequenceTap onScreenTouch(MotionEvent m) {
        /*All variables in SequenceTap defined before creating tap variable.*/
        long lT = System.currentTimeMillis();
        String sA = actionSwitch(m.getActionMasked());
        float fX = m.getX();
        float fY = m.getY();
        SequenceTap tap = new SequenceTap(lT, sA, fX, fY);
        /*Log print of sequence before returning SequenceTap.*/
        Log.d("SEQUENCE_HEARD" + tap.toString(), valueOf(System.currentTimeMillis()));
        return tap;
    }

    /*Method to determine String value of action that occured.*/
    private String actionSwitch(int action) {
        String s;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                s = "Down";
                break;
            case MotionEvent.ACTION_UP:
                s = "Up";
                break;
            /*An event that is not an up or down tap may occur on live devices*/
            /* because of how MotionEvent listens for touches.*/
            /*This error can be fixed adding addition conditions to the if-*/
            /* statement in the onTouch method to only be true when the event*/
            /* is either an ACTION_DOWN or ACTION_UP.*/
            default:
                s = "ERROR_MOTION";
        }
        return s;
    }

}

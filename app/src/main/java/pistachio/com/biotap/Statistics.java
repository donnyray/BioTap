package pistachio.com.biotap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Create back button and give it a listener. */
        Button backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent backToMenu = new Intent(getApplicationContext(), Menu.class);
                Log.d("Back Button", "Pressed");
                startActivity(backToMenu);
            }
        });

        /* stat variables to b*/
        int iAttempts = 0;
        int iAccepted = 0;
        int iDissim = 0;
        double iPercent = 0.0;

        /*
         * need to get stats from txt file here
         * putting in dummy values for testing
         * until i figure it out
         * actually txt file should have 3 #s:
         * attempts, accepted, and disim score
         */
        
        iAttempts = 12;
        iAccepted = 5;
        iDissim = 15;

        /* get percent from ints */
        if(iAttempts != 0) {
            iPercent = (double)iAccepted/(double)iAttempts;
        }

        /*
         * Create TextViews for stats.
         * 01 == attempts, 02 == accepted,
         * 03 == dissimScore, 04 == accept%
         */
        TextView statView01 = (TextView) findViewById(R.id.statText01);
        TextView statView02 = (TextView) findViewById(R.id.statText02);
        TextView statView03 = (TextView) findViewById(R.id.statText03);
        TextView statView04 = (TextView) findViewById(R.id.statText04);

        /* Set the text of each view to the stats.*/
        statView01.setText(Integer.toString(iAttempts));
        statView02.setText(Integer.toString(iAccepted));
        statView03.setText(Integer.toString(iDissim));
        statView04.setText(new DecimalFormat("###.####").format(iPercent*100) + "%");
    }
}

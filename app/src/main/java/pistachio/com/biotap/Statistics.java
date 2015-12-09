package pistachio.com.biotap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;



public class Statistics extends AppCompatActivity {

    protected FileInputStream statFile;
    protected InputStreamReader inputStreamReader;
    protected BufferedReader bufferedReader;
    protected StringBuilder sb;
    protected String data;

    public static int success = 0;
    public static int fail = 0;
    public static double score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button deleteButton = (Button) findViewById(R.id.deleteall);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                File dir = getFilesDir();
                File file = new File(dir, "average");
                boolean deleted = file.delete();
                Statistics.success = 0;
                Statistics.fail = 0;
                Statistics.score = 0;

                AlertDialog.Builder builder = new AlertDialog.Builder(Statistics.this);

                builder.setMessage("All data has been deleted and reset.")
                        .setCancelable(false)
                        .setPositiveButton("Back to home", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog d = (Dialog) dialog;
                                Intent myIntent = new Intent(((Dialog)dialog).getContext(), pistachio.com.biotap.Menu.class);
                                ((Dialog) dialog).getContext().startActivity(myIntent);
                            }
                        });
                builder.create().show();
            }
        });

        /* stat variables to b*/
        int iAttempts = Statistics.success + Statistics.fail;
        int iAccepted = Statistics.success;
        double iDissim = Math.round(Statistics.score*100.0)/100.0;
        double iPercent = 0.0;


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
        statView03.setText(Double.toString(iDissim));
        statView04.setText(new DecimalFormat("###.####").format(iPercent*100) + "%");
    }
}
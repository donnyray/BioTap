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

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button loginBtn = (Button) findViewById(R.id.login_btn);
        Button statBtn = (Button) findViewById(R.id.stat_btn);
        Button regBtn = (Button) findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loginScreen = new Intent(getApplicationContext(), MainActivity.class);

                Log.d("Login Button", "Pressed");

                startActivity(loginScreen);

            }
        });

        statBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent statScreen = new Intent(getApplicationContext(), Statistics.class);

                Log.d("Stats Button", "Pressed");

                startActivity(statScreen);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent registerScreen = new Intent(getApplicationContext(), MainActivity.class);

                Log.d("Register Button", "Pressed");

                startActivity(registerScreen);
            }
        });


    }

}

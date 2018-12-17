package com.example.jan.przetwarzanierozproszone;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView welcomeSign;
    static String nickname;
    EditText nicknameEditText;
    Button button;
    static RequestQueue requestQueue;
    boolean serviceStarted = false;

    /**
     * method initializes all views in activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        welcomeSign = findViewById(R.id.welcome_sign);
        nicknameEditText = findViewById(R.id.nickanme);
        final Intent service = new Intent(this, MyService.class);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nicknameEditText.getText().toString().matches("") && !serviceStarted) {
                    nickname = String.valueOf(nicknameEditText.getText());
                    nicknameEditText.setVisibility(View.GONE);
                    welcomeSign.setVisibility(View.VISIBLE);
                    welcomeSign.setText("WITAJ " + nickname + "\nMOZESZ ROZPOCZĄĆ AKTYWNOŚĆ");
                    startService(service);
                    button.setText("zakończ aktywność");
                    serviceStarted = true;
                } else if (serviceStarted) {
                    serviceStarted = false;
                    stopService(service);
                    button.setText("rozpocznij aktywność");

                } else {
                    nicknameEditText.setHint("ADD NICKNAME");
                }
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

}

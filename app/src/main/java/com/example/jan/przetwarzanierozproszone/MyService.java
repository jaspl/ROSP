package com.example.jan.przetwarzanierozproszone;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyService extends android.app.Service {


    Timer timer = new Timer();
    Long lastUpdate = Long.valueOf(0);
    static public int stepDetector = 0;
    int allSteps = 0;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            srnsors();

        return super.onStartCommand(intent, flags, startId);
    }

    private void srnsors() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_STEP_DETECTOR:
                        if (MainActivity.nickname!=null) {
                            stepDetector++;
                            allSteps++;
                            lastUpdate = System.currentTimeMillis();
                            timer.timerStart();
                        }
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        Sensor stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepDetector != null) {
            sensorManager.registerListener(sensorEventListener, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "StepDetector not found!", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.sendData();
        timer.timerStop();
    }
}


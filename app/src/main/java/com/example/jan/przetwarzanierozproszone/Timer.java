package com.example.jan.przetwarzanierozproszone;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timer {


    int numberOfSamples = 12;
    int time;
    boolean timerStarted = false;
    PassData passData = new PassData();
    CountDownTimer countDownTimer;
    ArrayList<Integer> steps = new ArrayList<>(12);

    public void timerExecyte() {

        timerStarted = true;
        passData.setNumberOfSamples(numberOfSamples - 1);
        countDownTimer = new CountDownTimer(300000, 1000) {


            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTick(long millisUntilFinished) {

                time++;
                if (time % 3 == 0 && time > 0) {

                    steps.add(MyService.stepDetector);
                    Log.d("info", String.valueOf(steps));
                    MyService.stepDetector = 0;
                }
                if (steps.size() == numberOfSamples) {
                    sendData();
                }
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                countDownTimer.start();
            }

        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void sendData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formated = now.format(formatter);
        Log.d("INFO", formated);
        passData.jsonCreator(steps, formated);
        steps.clear();
    }

    public void timerStart() {
        if (!timerStarted) {
            timerExecyte();
        }
    }

    public void timerStop() {
        if (timerStarted) {
            countDownTimer.cancel();
            timerStarted = false;
        }
    }


}

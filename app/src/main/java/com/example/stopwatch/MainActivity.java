package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private long offsetTime;
    private long continueTime;
    private long endTime;
    private String btnText = null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //        ストップウォッチ機能
        Chronometer chronometer = findViewById(R.id.time);

        Button stopwatch_btn = findViewById(R.id.btn_stopwatch);
        Button reset_btn = findViewById(R.id.btn_reset);

        chronometer.setText("00:00:00");

        chronometer.setFormat("00:%s");

        reset_btn.setVisibility(View.INVISIBLE);

//        開始時点の表示設定
        if(btnText != null) {
//            500は調整用
            chronometer.setBase(SystemClock.elapsedRealtime() - continueTime - (SystemClock.elapsedRealtime() - endTime) - 500);
            if(btnText.equals("START")) {
                chronometer.setBase(SystemClock.elapsedRealtime());
            } else if(btnText.equals("STOP")) {
                chronometer.start();
                stopwatch_btn.setText("STOP");
                stopwatch_btn.setBackgroundResource(R.drawable.btn_stopwatch_stop);
                stopwatch_btn.setTextColor(getResources().getColorStateList(R.drawable.btn_stopwatch_stop_text, null));
            } else if(btnText.equals("RESTART")) {
                chronometer.setBase(SystemClock.elapsedRealtime() - offsetTime);
                reset_btn.setVisibility(View.VISIBLE);
                stopwatch_btn.setText("RESTART");
                stopwatch_btn.setBackgroundResource(R.drawable.btn_stopwatch);
                stopwatch_btn.setTextColor(getResources().getColorStateList(R.drawable.btn_stopwatch_text, null));
            }
        }

//        ボタン基本動作
        stopwatch_btn.setOnClickListener(v -> {
            if(stopwatch_btn.getText().equals("START")) {
                // set the base to the current time just before calling start()
//                デバック用
//                chronometer.setBase(SystemClock.elapsedRealtime() - 3595000l);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                stopwatch_btn.setText("STOP");
                stopwatch_btn.setBackgroundResource(R.drawable.btn_stopwatch_stop);
                stopwatch_btn.setTextColor(getResources().getColorStateList(R.drawable.btn_stopwatch_stop_text, null));
            } else if(stopwatch_btn.getText().equals("STOP")) {
                offsetTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
                stopwatch_btn.setText("RESTART");
                reset_btn.setVisibility(View.VISIBLE);
                stopwatch_btn.setBackgroundResource(R.drawable.btn_stopwatch);
                stopwatch_btn.setTextColor(getResources().getColorStateList(R.drawable.btn_stopwatch_text, null));
            } else if(stopwatch_btn.getText().equals("RESTART")) {
                chronometer.setBase(SystemClock.elapsedRealtime() - offsetTime);
                chronometer.start();
                stopwatch_btn.setText("STOP");
                stopwatch_btn.setBackgroundResource(R.drawable.btn_stopwatch_stop);
                stopwatch_btn.setTextColor(getResources().getColorStateList(R.drawable.btn_stopwatch_stop_text, null));
                reset_btn.setVisibility(View.INVISIBLE);
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer c) {
                long elapsedMillis = SystemClock.elapsedRealtime() -c.getBase();
                if(elapsedMillis > 3599000L){
                    c.setFormat("0%s");
                }else{
                    c.setFormat("00:%s");
                }
            }
        });

        reset_btn.setOnClickListener(v -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            stopwatch_btn.setText("START");
            reset_btn.setVisibility(View.INVISIBLE);
        });

    }
}

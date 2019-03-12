package com.xchen218.cs541p4;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatchView extends LinearLayout {
    public StopWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private int tenMSecond = 0;
    private static final int MSG_WHAT_SHOW_TIME = 1;
    private TextView tvHour, tvMinute, tvSecond, tvMSecond;
    private Button btnStart, btnPause, btnResume, btnLap, btnReset;
    private ListView lvTimeList;
    private ArrayAdapter<String> adapter;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){

        }
    };

    private Timer timer = new Timer();
    private TimerTask timerTask = null;
    private TimerTask showTimerTask = null;
    private void startTimer(){
        if (timerTask == null){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    tenMSecond++;
                }
            };
            timer.schedule(timerTask, 10, 10);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvHour = findViewById(R.id.swHour);
        tvHour.setText("0");
        tvMinute = findViewById(R.id.swMinute);
        tvMinute.setText("0");
        tvSecond = findViewById(R.id.swSecond);
        tvSecond.setText("0");
        tvMSecond = findViewById(R.id.swMSecond);
        tvMSecond.setText("0");

        btnStart = findViewById(R.id.btnSWStart);
        btnPause = findViewById(R.id.btnSWPause);
        btnResume = findViewById(R.id.btnSWResume);
        btnLap = findViewById(R.id.btnSWLap);
        btnReset = findViewById(R.id.btnSWReset);

        btnPause.setVisibility(View.GONE);
        btnResume.setVisibility(View.GONE);
        btnLap.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lvTimeList = findViewById(R.id.lvTimeList);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvTimeList.setAdapter(adapter);

        showTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);
            }
        };
        timer.schedule(showTimerTask, 200, 200);
    }
}

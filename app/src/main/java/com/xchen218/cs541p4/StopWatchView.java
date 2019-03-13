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
            switch (msg.what){
                case MSG_WHAT_SHOW_TIME:
                    tvHour.setText(tenMSecond/360000+"");
                    tvMinute.setText(tenMSecond/6000%60+"");
                    tvSecond.setText(tenMSecond/100%60+"");
                    tvMSecond.setText(tenMSecond%100+"");
                    break;
                default:
                    break;
            }
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
    private void stopTimer(){
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
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
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnLap.setVisibility(View.VISIBLE);
            }
        });
        btnPause = findViewById(R.id.btnSWPause);
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                btnPause.setVisibility(View.GONE);
                btnResume.setVisibility(View.VISIBLE);
                btnLap.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });
        btnResume = findViewById(R.id.btnSWResume);
        btnResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                btnLap.setVisibility(View.VISIBLE);
            }
        });
        btnLap = findViewById(R.id.btnSWLap);
        btnLap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.insert(String.format("%d:%d:%d.%d", tenMSecond/360000, tenMSecond/6000%60, tenMSecond/100%60, tenMSecond%100), 0);
            }
        });
        btnReset = findViewById(R.id.btnSWReset);
        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                tenMSecond = 0;
                adapter.clear();
                btnPause.setVisibility(View.GONE);
                btnResume.setVisibility(View.GONE);
                btnLap.setVisibility(View.GONE);
                btnReset.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);

            }
        });
        btnPause.setVisibility(View.GONE);
        btnResume.setVisibility(View.GONE);
        btnLap.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);



        lvTimeList = findViewById(R.id.lvTimeList);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        lvTimeList.setAdapter(adapter);

        showTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);
            }
        };
        timer.schedule(showTimerTask, 100, 100);
    }

    public void onDestroy() {
        timer.cancel();
    }
}

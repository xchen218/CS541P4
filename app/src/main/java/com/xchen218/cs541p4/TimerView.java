package com.xchen218.cs541p4;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;

public class TimerView extends LinearLayout {
    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimerView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        });
        btnPause = findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                btnPause.setVisibility(View.GONE);
                btnResume.setVisibility(View.VISIBLE);
            }
        });
        btnResume = findViewById(R.id.btnResume);
        btnResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                etHour.setText("0");
                etMinute.setText("0");
                etSecond.setText("0");

                btnReset.setVisibility(View.GONE);
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        etHour = findViewById(R.id.etHour);
        etMinute = findViewById(R.id.etMinute);
        etSecond = findViewById(R.id.etSecond);

        etHour.setText("0");
        etHour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    int value = Integer.parseInt(s.toString());
                    if (value > 59) {
                        etHour.setText("59");
                    } else if (value < 0) {
                        etHour.setText("0");
                    }

                }
                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMinute.setText("0");
        etMinute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int value = Integer.parseInt(s.toString());
                    if (value > 59) {
                        etMinute.setText("59");
                    } else if (value < 0) {
                        etMinute.setText("0");
                    }

                }

                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSecond.setText("0");
        etSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int value = Integer.parseInt(s.toString());
                    if (value > 59) {
                        etSecond.setText("59");
                    } else if (value < 0) {
                        etSecond.setText("0");
                    }

                }

                checkToEnableBtnStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnStart.setVisibility(View.VISIBLE);
        btnStart.setEnabled(false);
        btnPause.setVisibility(View.GONE);
        btnResume.setVisibility(View.GONE);
        btnReset.setVisibility(View.GONE);
    }

    private void checkToEnableBtnStart(){

        /*if (etHour.getText().toString() == null || etMinute.getText().toString() == null || etSecond.getText().toString() == null){
            btnStart.setEnabled(false);
        }else {
            btnStart.setEnabled((Integer.parseInt(etHour.getText().toString()) > 0 ||
                    Integer.parseInt(etMinute.getText().toString()) > 0 ||
                    Integer.parseInt(etSecond.getText().toString()) > 0));
        }*/

        btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText())&&(Integer.parseInt(etHour.getText().toString())) > 0) ||
                (!TextUtils.isEmpty(etMinute.getText())&&(Integer.parseInt(etMinute.getText().toString())) > 0)||
                (!TextUtils.isEmpty(etSecond.getText())&&(Integer.parseInt(etSecond.getText().toString())) > 0));
    }

    private void startTimer(){
        if (timerTask == null){
            allTimerCount = Integer.parseInt(etHour.getText().toString()) * 3600 +
                    Integer.parseInt(etMinute.getText().toString()) * 60 +
                    Integer.parseInt(etSecond.getText().toString());
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    allTimerCount--;
                    handler.sendEmptyMessage(MSG_TIME_TICK);
                    if (allTimerCount < 0){
                        handler.sendEmptyMessage(MSG_TIME_UP);
                        stopTimer();
                    }
                }
            };
            timer.schedule(timerTask, 1000, 1000);

        }
    }
    private void stopTimer(){
        if (timerTask != null){
            timerTask.cancel();
            timerTask = null;
        }
    }

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case MSG_TIME_UP:
                    new AlertDialog.Builder(getContext()).setTitle("Time up").setMessage("Time up").setNegativeButton("Cancel", null).show();
                    btnReset.setVisibility(View.GONE);
                    btnResume.setVisibility(View.GONE);
                    btnPause.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    break;
                case MSG_TIME_TICK:
                    int hour = allTimerCount/3600;
                    int minute = (allTimerCount/60)%60;
                    int second = allTimerCount%60;
                    etHour.setText(hour+"");
                    etMinute.setText(minute+"");
                    etSecond.setText(second+"");
                    break;
                default:
                    break;
            }
        }
    };

    private static final int MSG_TIME_UP = 1;
    private static final int MSG_TIME_TICK = 2;
    private int allTimerCount = 0;
    private Timer timer = new Timer();
    private TimerTask timerTask = null;
    private Button btnStart, btnPause, btnResume, btnReset;
    private EditText etHour, etMinute, etSecond;
}

package com.xchen218.cs541p4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Handler;

import java.util.Calendar;


public class TimeView extends LinearLayout {
    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimeView(Context context) {
        super(context);
    }
    private TextView tvTime;
    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        tvTime = findViewById(R.id.tvTime);
        /*tvTime.setText("Hello");*/
        timeHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == View.VISIBLE){
            timeHandler.sendEmptyMessage(0);
        }else{
            timeHandler.removeMessages(0);
        }
    }

    private void refreshTime() {
        Calendar c = Calendar.getInstance();
        /*if (c.get(Calendar.SECOND) < 10 && c.get(Calendar.MINUTE) >= 10) {
            tvTime.setText(String.format("%d:%d:0%d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
        }else if(c.get(Calendar.SECOND) >= 10 && c.get(Calendar.MINUTE) < 10) {
            tvTime.setText(String.format("%d:0%d:%d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
        }else if(c.get(Calendar.SECOND) < 10 && c.get(Calendar.MINUTE) < 10){
            tvTime.setText(String.format("%d:0%d:0%d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
        }else*/
            tvTime.setText(String.format("%d:%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));


    }

    public Handler timeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            refreshTime();
            if(getVisibility() == View.VISIBLE){
                timeHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

}


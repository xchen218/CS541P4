package com.xchen218.cs541p4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("Time").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("Timer").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("StopWatch").setContent(R.id.tabStopWatch));
        tabHost.addTab(tabHost.newTabSpec("tabNotes").setIndicator("Notes").setContent(R.id.tabNotes));

        stopWatchView = findViewById(R.id.tabStopWatch);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopWatchView.onDestroy();
    }

    private StopWatchView stopWatchView;
    private TabHost tabHost;
}

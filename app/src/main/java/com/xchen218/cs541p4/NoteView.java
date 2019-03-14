package com.xchen218.cs541p4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteView extends LinearLayout {
    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoteView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvNote = findViewById(R.id.tvNote);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            //    tvNote.getText().toString().
            }
        });
        btnClear = findViewById(R.id.btnClear);


    }

    private TextView tvNote;
    private Button btnSave, btnClear;

}

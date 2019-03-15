package com.xchen218.cs541p4;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
                try{
                    String etName =  tvNote.getText().toString();
                    if(!etName.trim().equals("")){
                        File file =new File(Environment.getExternalStorageDirectory().getPath(), "output.txt");

                        //if file doesnt exists, then create it
                        if(!file.exists()){
                            file.createNewFile();
                        }


                        FileWriter fileWritter = new FileWriter(file.getName(),true);
                        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                        bufferWritter.write(etName);
                        bufferWritter.close();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
                tvNote.setText("");
            }
        });
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNote.setText("");
            }
        });


    }

    private TextView tvNote;
    private Button btnSave, btnClear;

}

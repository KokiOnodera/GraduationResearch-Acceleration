package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

/***
 * タッチした座標を記録するファイル作成
 * タッチしたら記録して画面遷移（加速度測定画面）する
 */

public class TouchActivity extends AppCompatActivity {
    DecimalFormat format = new DecimalFormat("#.#");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_activity);
        FileOutputStream output;
        try {
            String filename = "data_ks.csv";
            //ファイル作成
            output = openFileOutput(filename, Context.MODE_PRIVATE);
            output.write("Time[s]".getBytes());
            output.write(",".getBytes());
            output.write("Signal X[V]".getBytes());
            output.write(",".getBytes());
            output.write("Signal Y[V]".getBytes());
            output.write(",".getBytes());
            output.write("Signal Z[V]".getBytes());
            output.write("\n".getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream outputs;
        try {
            String file_name = "data_ku.csv";
            //ファイル作成
            outputs = openFileOutput(file_name, Context.MODE_PRIVATE);
            outputs.write("Time[s]".getBytes());
            outputs.write(",".getBytes());
            outputs.write("Signal X[V]".getBytes());
            outputs.write(",".getBytes());
            outputs.write("Signal Y[V]".getBytes());
            outputs.write("\n".getBytes());
            outputs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream output2;
        try {
            String file_name = "touch.csv";
            //ファイル作成
            output2 = openFileOutput(file_name, Context.MODE_PRIVATE);
            output2.write("Type".getBytes());
            output2.write(",".getBytes());
            output2.write("Pressure".getBytes());
            output2.write(",".getBytes());
            output2.write("x1:".getBytes());
            output2.write(",".getBytes());
            output2.write("y1:".getBytes());
            output2.write("\n".getBytes());
            output2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //画面タップしたら
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                String filename = "touch.csv";
                FileOutputStream output;
                try {
                    output = openFileOutput(filename, Context.MODE_APPEND);
                    output.write("ACTION_DOWN".getBytes());
                    output.write(",".getBytes());
                    output.write(format.format(motionEvent.getPressure()).getBytes());
                    output.write(",".getBytes());
                    output.write(String.valueOf(motionEvent.getX()).getBytes());
                    output.write(",".getBytes());
                    output.write(String.valueOf(motionEvent.getY()).getBytes());
                    output.write("\n".getBytes());
                    output.close();
                } catch (Exception e) {
                    Log.e("error:","書き込み失敗");
                    e.printStackTrace();
                }


                break;

            case MotionEvent.ACTION_UP:
                String filename2 = "touch.csv";
                FileOutputStream outputs;
                try {
                    outputs = openFileOutput(filename2, Context.MODE_APPEND);
                    outputs.write("ACTION_UP".getBytes());
                    outputs.write(",".getBytes());
                    outputs.write(format.format(motionEvent.getPressure()).getBytes());
                    outputs.write(",".getBytes());
                    outputs.write(String.valueOf(motionEvent.getX()).getBytes());
                    outputs.write(",".getBytes());
                    outputs.write(String.valueOf(motionEvent.getY()).getBytes());
                    outputs.write("\n".getBytes());
                    outputs.close();
                } catch (Exception e) {
                    Log.e("error:","書き込み失敗");
                    e.printStackTrace();
                }
                long eventDuration2 = motionEvent.getEventTime() - motionEvent.getDownTime();


                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }

        //画面遷移
        Intent intent = new Intent(TouchActivity.this.getApplication(), MainActivity.class);
        TouchActivity.this.startActivity(intent);

        return false;

    }
}


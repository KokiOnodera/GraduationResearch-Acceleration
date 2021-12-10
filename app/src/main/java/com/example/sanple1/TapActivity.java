package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;

/**
 * 画面タップ
 * タップしたら画面遷移
 * タップするまでの時間を測定
 * 圧力，座標
 * 記録
 */

public class TapActivity extends AppCompatActivity {
    private final StringBuffer info = new StringBuffer("Test onTouchEvent\n\n");
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tap_activity);

        // Intent を取得する
        Intent intent = getIntent();
        // キーを元にデータを取得する(データがない場合、第２引数の 0 が返る)
        count = intent.getIntExtra("Count", 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

//                info.append("ACTION_DOWN\n");
//                info.append("Pressure");
//                info.append(motionEvent.getPressure());
//                info.append("\n");
//                info.append("x1:");
//                info.append(motionEvent.getX());
//                info.append("\n");
//                info.append("y1:");
//                info.append(motionEvent.getY());
//                info.append("\n\n");

                String filename = "tap_down.csv";
                FileOutputStream output;
                Log.v("タップダウンファイル",filename);

                try {
                    output = openFileOutput(filename, Context.MODE_APPEND);
                    output.write(String.valueOf(motionEvent.getPressure()).getBytes());
                    output.write(",".getBytes());
                    output.write(String.valueOf(motionEvent.getX()).getBytes());
                    output.write(",".getBytes());
                    output.write(String.valueOf(motionEvent.getY()).getBytes());
                    output.write("\n".getBytes());
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case MotionEvent.ACTION_UP:

                String filename2 = "tap_up.csv";
                Log.v("タップアップファイル",filename2);
                FileOutputStream outputs;

                try {
                    outputs = openFileOutput(filename2, Context.MODE_APPEND);
                    outputs.write(String.valueOf(motionEvent.getEventTime() - motionEvent.getDownTime()).getBytes());
                    outputs.write(",".getBytes());
                    outputs.write(String.valueOf(motionEvent.getX()).getBytes());
                    outputs.write(",".getBytes());
                    outputs.write(String.valueOf(motionEvent.getY()).getBytes());
                    outputs.write("\n".getBytes());
                    outputs.close();
                }catch(Exception ee){
                    Log.v("エラー",String.valueOf(ee));
                }
                    count++;
                    Log.v("count", String.valueOf(count));
                    if (count > 10) {
                        Log.v("test", String.valueOf(count));
                        //画面遷移
                        Intent intent = new Intent(TapActivity.this.getApplication(), EndActivity.class);
                        TapActivity.this.startActivity(intent);
                    }else{
                        Intent intent = new Intent(TapActivity.this.getApplication(), ExplanationActivity.class);
                        //カウント渡し
                        Log.v("count", String.valueOf(count));
                        intent.putExtra("Count", count);

                        startActivity(intent);
                    }


                    break;
                    case MotionEvent.ACTION_MOVE:
                        info.append("ACTION_MOVE\n");

                        break;
                    case MotionEvent.ACTION_CANCEL:
                        info.append("ACTION_CANCEL\n");

                        break;

        }

        return false;
    }
}

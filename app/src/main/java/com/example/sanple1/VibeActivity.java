package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;

/***
 * 加速度を記録するファイルを作成
 * アプリを起動して5秒後にバイブしたら画面遷移（加速度測定画面）する
 */

public class VibeActivity extends AppCompatActivity {
    /** スレッドUI操作用ハンドラ */
    private final Handler mHandler = new Handler();
    /** テキストオブジェクト */
    private Runnable vibration;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        // Intent を取得する
        Intent intent = getIntent();
        count = intent.getIntExtra("Count",0);
        FileOutputStream output;
        try {
            String filename = "data_ks"+Integer.toString(count)+".csv";
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
            String file_name = "data_ku"+Integer.toString(count)+".csv";
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

        /**
        * 5秒後にバイブレーション
        * */
        vibration = new Runnable() {
            public void run() {
                //1000ms バイブを鳴らす
                ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
                //画面遷移
                Intent intent = new Intent(VibeActivity.this.getApplication(), MainActivity.class);
                // 渡したいデータとキーを指定する
                intent.putExtra("Count", count);
                startActivity(intent);
            }
        };
        //5秒後にバイブレーション機能を起動
        mHandler.postDelayed(vibration, 5000);
    }
}


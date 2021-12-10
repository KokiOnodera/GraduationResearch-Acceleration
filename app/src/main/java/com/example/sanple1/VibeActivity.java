package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;

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

        /**
        * 5秒後にバイブレーション
        * */
        vibration = new Runnable() {
            public void run() {
                //1000ms バイブを鳴らす
                Vibrator vibratorManager = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= 26) {
                    VibrationEffect effect = VibrationEffect.createOneShot(1000, 255);
                    vibratorManager.vibrate(effect);
                }
                //((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
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


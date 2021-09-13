package com.example.sanple1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * アプリ起動時に表示される画面
 * 開始ボタンを押すとバイブレーションのアクティビティに遷移
 */
public class ExplanationActivity extends AppCompatActivity {
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Intent を取得する
        Intent intent = getIntent();
        // キーを元にデータを取得する(データがない場合、第２引数の 0 が返る)
        count = intent.getIntExtra("Count", 1);
        setContentView(R.layout.explanation);
        Button start_button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        // テキストを設定して表示
        textView.setText("開始ボタンを押して5秒後にバイブレーションします．\n"
                + "バイブレーション後に加速度，角度を計測します．10回計測します．\n"
                +"現在"+Integer.toString(count)+"回目です");
        start_button.setWidth(500);
        start_button.setHeight(300);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //画面遷移
                Intent intent = new Intent(ExplanationActivity.this.getApplication(), VibeActivity.class);
                //カウント渡し
                intent.putExtra("Count", count);
                startActivity(intent);
            }
        });
    }
}

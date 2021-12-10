   package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

/**
 * アプリ起動時に表示される画面
 * 加速度記録用のcsvファイルを作成
 * 開始ボタンを押すとバイブレーションのアクティビティ（VibeActivity）に遷移
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

        FileOutputStream output;
        try {
            String filename = "data_ks"+Integer.toString(count)+".csv";
            //加速度ファイル作成
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
            //角度ファイル作成
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

        if(count == 1){
            FileOutputStream tap_down_output;
            try{
                String filename = "tap_down.csv";
                //加速度ファイル作成
                output = openFileOutput(filename, Context.MODE_PRIVATE);
                output.write("Pressure".getBytes());
                output.write(",".getBytes());
                output.write("X".getBytes());
                output.write(",".getBytes());
                output.write("Y".getBytes());
                output.write("\n".getBytes());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FileOutputStream tap_up_output;
            try{
                String filename = "tap_up.csv";
                //加速度ファイル作成
                output = openFileOutput(filename, Context.MODE_PRIVATE);
                output.write("Time[s]".getBytes());
                output.write(",".getBytes());
                output.write("X".getBytes());
                output.write(",".getBytes());
                output.write("Y".getBytes());
                output.write("\n".getBytes());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

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

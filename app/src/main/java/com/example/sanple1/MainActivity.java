package com.example.sanple1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener  {
    private SensorManager sensorManager;
    private TextView textView, textInfo;
    private Sensor accel;

    DecimalFormat format = new DecimalFormat("#.#");
    private double sTime;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //textInfo = findViewById(R.id.text_info);
        // Get an instance of the TextView
        //textView = findViewById(R.id.text_view);

        // Intent を取得する
        Intent intent = getIntent();

        // キーを元にデータを取得する(データがない場合、第２引数の 0 が返る)
        count = intent.getIntExtra("Count", 0);

        Timer timer = new Timer(false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (count>=10){
                    //画面遷移
                    Intent intent = new Intent(MainActivity.this.getApplication(), EndActivity.class);
                    MainActivity.this.startActivity(intent);
                }else{
                    count ++;
                    //画面遷移
                    Intent intent = new Intent(MainActivity.this.getApplication(), ExplanationActivity.class);
                    //カウント渡し
                    intent.putExtra("Count", count);
                    startActivity(intent);
                }
                timer.cancel();
            }
        };
        //5秒計測
        timer.schedule(task, 5000);

        sTime=System.currentTimeMillis();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        accel = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
    }

    // 解除するコードも入れる!
    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensorX, sensorY, sensorZ;
        double ang_acc_x, ang_acc_y;

        float[] gravity = new float[3];


        final float alpha = 0.6f;


        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(1);
        //keisann
        BigDecimal time = BigDecimal.valueOf((System.currentTimeMillis() - sTime) / 1000);

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            sensorX = gravity[0];
            sensorY = gravity[1];
            sensorZ = gravity[2];
            ang_acc_x = (((Math.atan(sensorX / (Math.sqrt(Math.pow(sensorY, 2) + Math.pow(sensorZ, 2))))) * 180) / Math.PI);
            ang_acc_y = (((Math.atan(sensorY / (Math.sqrt(Math.pow(sensorX, 2) + Math.pow(sensorZ, 2))))) * 180) / Math.PI);

            String strTmp = "加速度センサー\n"
                    + " X: " + sensorX + "\n"
                    + " Y: " + sensorY + "\n"
                    + " Z: " + sensorZ + "\n\n"
                    + "角速度センサー\n"
                    + " X: " + ang_acc_x + "\n"
                    + " Y: " + ang_acc_y;
            //textView.setText(strTmp);


            String filename = "data_ks"+Integer.toString(count)+".csv";
            FileOutputStream output;

            try {
                output = openFileOutput(filename, Context.MODE_APPEND);

                output.write(format.format(time).getBytes());
                output.write(",".getBytes());
                output.write(String.valueOf(sensorX).getBytes());
                output.write(",".getBytes());
                output.write(String.valueOf(sensorY).getBytes());
                output.write(",".getBytes());
                output.write(String.valueOf(sensorZ).getBytes());
                output.write("\n".getBytes());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String file_name = "data_ku"+Integer.toString(count)+".csv";
            FileOutputStream outputs;

            try {
                outputs = openFileOutput(file_name, Context.MODE_APPEND);
                outputs.write(format.format(time).getBytes());
                outputs.write(",".getBytes());
                outputs.write(String.valueOf(ang_acc_x).getBytes());
                outputs.write(",".getBytes());
                outputs.write(String.valueOf(ang_acc_y).getBytes());
                outputs.write("\n".getBytes());
                outputs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
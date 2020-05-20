package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

public class SensorActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensorAcelera;
    SensorEventListener sensorEventListener;
    int whip = 0;
    EditText cantidad;
    private final static float ACC = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAcelera = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (sensorAcelera == null){

            finish();
        }

        cantidad = findViewById(R.id.editCantidad);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //float x = sensorEvent.values[0];
                //float y = sensorEvent.values[1];
                float[] values = sensorEvent.values;

                //if ((Math.abs(values[0]) > ACC || Math.abs(values[1]) > ACC || Math.abs(values[2]) > ACC)){
                if (sensorEvent.values[2] > 5f || sensorEvent.values[2] < -5f){

                    cantidad.setText(String.valueOf(whip));
                    whip++;
                }

                if (whip >=1 && whip < 3){

                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                }else if ( whip >= 3 || whip <= 5){

                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }else if ( whip > 5){

                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        start();
    }

    private void start(){

        sensorManager.registerListener(sensorEventListener,sensorAcelera,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){

        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {

        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {

        start();
        super.onResume();
    }
}

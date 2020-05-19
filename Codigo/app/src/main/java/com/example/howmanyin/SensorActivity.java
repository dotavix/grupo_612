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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAcelera = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensorAcelera == null){

            finish();
        }

        cantidad = findViewById(R.id.editCantidad);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];


                if (x > 10 || x < -10 || y < -10 || y > 10){

                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    cantidad.setText(String.valueOf(whip));
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
    protected void onPostResume() {

        start();
        super.onPostResume();
    }
}

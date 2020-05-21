package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensorAcelera;
    SensorEventListener sensorEventListener;
    int whip = 0;
    EditText cantidad;
    ProgressBar colorBar ;
    Button guardarPrefers;
    Button mostrarPrefers;
    String key = "KEY";
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

        guardarPrefers = findViewById(R.id.buttonConsejo);
        mostrarPrefers = findViewById(R.id.buttonShow);

        final SharedPreferences prefers = PreferenceManager.getDefaultSharedPreferences(this);

        guardarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = prefers.edit();
                editor.putString( key ,"DATO");
                editor.apply();
                Toast.makeText(getApplicationContext(), "Se guardo el dato" , Toast.LENGTH_SHORT).show();

            }
        });

        mostrarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dato = prefers.getString(key , "No hay datos");
                Toast.makeText(getApplicationContext(), dato , Toast.LENGTH_SHORT).show();
            }
        });

        cantidad = findViewById(R.id.editCantidad);

        colorBar = findViewById(R.id.progressBar);


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //float x = sensorEvent.values[0];
                //float y = sensorEvent.values[1];
                //float[] values = sensorEvent.values;

                //if ((Math.abs(values[0]) > ACC || Math.abs(values[1]) > ACC || Math.abs(values[2]) > ACC)){
                if (sensorEvent.values[2] > 5f || sensorEvent.values[2] < -5f){

                    whip++;
                    cantidad.setText(String.valueOf(whip));

                }

                if (whip >=1 && whip < 3){

                    //getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    colorBar.setBackgroundColor(Color.GREEN);

                }else if ( whip >= 3 && whip <= 5){

                    //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    colorBar.setBackgroundColor(Color.YELLOW);

                }else if ( whip > 5){

                    //getWindow().getDecorView().setBackgroundColor(Color.RED);
                    colorBar.setBackgroundColor(Color.RED);
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

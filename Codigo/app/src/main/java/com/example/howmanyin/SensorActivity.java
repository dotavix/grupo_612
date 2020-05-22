package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    EditText mostrarSensoreInfo;
    ProgressBar colorBar ;
    Button guardarPrefers;
    Button mostrarPrefers;
    private final static float ACC = 30;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String name = "nameKey";
    public static final String usuario = "Usuario";
    public static final String cantidadGiros = "emailKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAcelera = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (sensorAcelera == null){

            finish();
        }

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(name)) {

            sharedpreferences.getString(name, "");
        }
        if (sharedpreferences.contains(cantidadGiros)) {

            sharedpreferences.getInt(cantidadGiros, 0);

        }

        guardarPrefers = findViewById(R.id.buttonMostrar);
        mostrarPrefers = findViewById(R.id.buttonShow);

        guardarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(name, usuario);
                editor.putInt(cantidadGiros, whip);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Datos Guardados" , Toast.LENGTH_SHORT).show();

            }
        });

        mostrarSensoreInfo = findViewById(R.id.editSensor);

        mostrarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                String userMostrar = "";
                int cantidadMostrar = 0;

                if (sharedpreferences.contains(name)) {

                    userMostrar = sharedpreferences.getString(name, "");
                }
                if (sharedpreferences.contains(cantidadGiros)) {

                    cantidadMostrar = sharedpreferences.getInt(cantidadGiros, 0);

                }

                mostrarSensoreInfo.setText("Usuario: " + userMostrar + "\n" + "Cantidad de giros: " + cantidadMostrar);
                Toast.makeText(getApplicationContext(), "Usuario: " + userMostrar + "\n" + "Cantidad de giros: " + cantidadMostrar , Toast.LENGTH_SHORT).show();
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

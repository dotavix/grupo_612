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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class SensorActivity extends AppCompatActivity {

    int giro = 0;
    int acelero = 0;
    SensorManager sensorManager;
    SensorManager sensorManagerAcelero;
    Sensor sensorAcelera;
    Sensor sensorGiroscopo;
    SensorEventListener sensorEventListener;
    SensorEventListener sensorEventListenerAcelero;
    EditText cantidad;
    EditText cantidadAcelero;
    TextView mostrarSensoreInfo;
    ProgressBar colorBar ;
    ProgressBar colorBarAcelero;
    Button guardarPrefers;
    Button mostrarPrefers;
    private final static float ACC = 30;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "Info";
    public static final String name = "userKey";
    public static final String cantidadGiros = "giroKey";
    public static final String cantidadAcel = "aceleroKey";
    String emailSearch;
    int incremento = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);

        guardarPrefers = findViewById(R.id.buttonMostrar);

        mostrarPrefers = findViewById(R.id.buttonShow);

        mostrarSensoreInfo = findViewById(R.id.editSensor);

        cantidad = findViewById(R.id.editCantidad);

        colorBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();

        cantidadAcelero = findViewById(R.id.editAcelero);

        colorBarAcelero = findViewById(R.id.progressAcelero);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorGiroscopo = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManagerAcelero = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAcelera = sensorManagerAcelero.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensorGiroscopo == null && sensorAcelera == null){

            finish();
        }

        emailSearch = extras.getString("mail");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

/*       if (sharedpreferences.contains(name)) {

            sharedpreferences.getString(name, "");
        }
        if (sharedpreferences.contains(cantidadGiros)) {

            sharedpreferences.getInt(cantidadGiros, 0);

        }

        if (sharedpreferences.contains(cantidadAcel)) {

            sharedpreferences.getInt(cantidadAcel, 0);

        }*/

        guardarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(name + incremento, emailSearch + giro + acelero);
                //editor.putInt(cantidadGiros, giro);
                //editor.putInt(cantidadAcel, acelero);

                editor.apply();

                Toast.makeText(getApplicationContext(), "Datos Guardados" , Toast.LENGTH_SHORT).show();
                incremento++;
            }
        });

        final String[] userMostrar = new String[1];
        final int[] cantidadMostrar = {0};
        final int[] cantidadMostrarAcelero = {0};

        mostrarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                if (sharedpreferences.contains(name)) {

                    userMostrar[0] = sharedpreferences.getString(name, "");

                    Log.d("Usuario key: ", userMostrar[0]);

                    if (userMostrar[0].equals(emailSearch)){

                        if (sharedpreferences.contains(cantidadGiros)) {

                            cantidadMostrar[0] = sharedpreferences.getInt(cantidadGiros, 0);

                        }

                        if (sharedpreferences.contains(cantidadAcel)) {

                            cantidadMostrarAcelero[0] = sharedpreferences.getInt(cantidadAcel, 0);

                        }
                        mostrarSensoreInfo.setText("Usuario: " + userMostrar[0] + "\n" + "Cantidad de giros: " + cantidadMostrar[0] + "\n" + "Cantidad de movimientos: " + cantidadMostrarAcelero[0]);
                        Toast.makeText(getApplicationContext(), "Usuario: " + userMostrar[0] + "\n" + "Cantidad de giros: " + cantidadMostrar[0] + "\n" + "Cantidad de movimientos: " + cantidadMostrarAcelero[0], Toast.LENGTH_SHORT).show();
                    }

                }*/
                Map<String,?> keys = sharedpreferences.getAll();

                String mostrar= "";

                for(Map.Entry<String,?> entry : keys.entrySet()){

                    if (entry.getValue().toString().contains(emailSearch)) {

                        Log.d("Clave email", entry.getKey() + "Cantidad" + entry.getValue().toString());
                        mostrar = mostrar + "Usuario: " + entry.getKey() + "\n" + "Cantidad de giros: " + entry.getValue().toString() + "\n";
                    }
                }
                mostrarSensoreInfo.setText(mostrar);
                mostrarSensoreInfo.setMovementMethod(new ScrollingMovementMethod());

            }
        });


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //float x = sensorEvent.values[0];
                //float y = sensorEvent.values[1];
                //float[] values = sensorEvent.values;

                //if ((Math.abs(values[0]) > ACC || Math.abs(values[1]) > ACC || Math.abs(values[2]) > ACC)){
                if (sensorEvent.values[2] > 5f || sensorEvent.values[2] < -5f){

                    giro++;
                    cantidad.setText(String.valueOf(giro));

                }

                if (giro >=1 && giro < 3){

                    //getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    colorBar.setBackgroundColor(Color.GREEN);

                }else if ( giro >= 3 && giro <= 5){

                    //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    colorBar.setBackgroundColor(Color.YELLOW);

                }else if ( giro > 5){

                    //getWindow().getDecorView().setBackgroundColor(Color.RED);
                    colorBar.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorEventListenerAcelero = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                //float x = sensorEvent.values[0];
                //float y = sensorEvent.values[1];
                //float[] values = sensorEvent.values;

                //if ((Math.abs(values[0]) > ACC || Math.abs(values[1]) > ACC || Math.abs(values[2]) > ACC)){
                if ( -sensorEvent.values[0] > ACC && -sensorEvent.values[1] > ACC){

                    acelero++;
                    cantidadAcelero.setText(String.valueOf(acelero));

                }

                if (acelero >=1 && acelero < 3){

                    colorBarAcelero.setBackgroundColor(Color.GREEN);

                }else if ( acelero >= 3 && acelero <= 5){

                    colorBarAcelero.setBackgroundColor(Color.YELLOW);

                }else if ( acelero > 5){

                    colorBarAcelero.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        start();
    }

    private void start(){

        sensorManager.registerListener(sensorEventListener,sensorGiroscopo,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerAcelero,sensorAcelera,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){

        sensorManager.unregisterListener(sensorEventListener);
        sensorManagerAcelero.unregisterListener(sensorEventListenerAcelero);

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

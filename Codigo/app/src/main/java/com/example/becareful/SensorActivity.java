package com.example.becareful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
    ProgressBar colorBarPromedio;
    Button guardarPrefers;
    Button mostrarPrefers;
    Button verConsejos;
    Button verPromedio;
    private final static float ACC = 30;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "Infor";
    public static final String name = "userKey";
    String emailSearch;
    int incremento = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor);

        guardarPrefers = findViewById(R.id.buttonMostrar);

        mostrarPrefers = findViewById(R.id.buttonShow);

        verConsejos = findViewById(R.id.buttonPastInfo);

        mostrarSensoreInfo = findViewById(R.id.editSensor);

        cantidad = findViewById(R.id.editCantidad);

        colorBar = findViewById(R.id.progressBar);

        colorBarPromedio = findViewById(R.id.progressBarProm);

        verPromedio = findViewById(R.id.buttonPromedio);

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


        guardarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(name + incremento, emailSearch + " " + giro + " " + acelero);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Datos Guardados" , Toast.LENGTH_SHORT).show();
                incremento++;
            }
        });


        mostrarPrefers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,?> keys = sharedpreferences.getAll();

                String mostrar= "";

                for(Map.Entry<String,?> entry : keys.entrySet()){

                    if (entry.getValue().toString().contains(emailSearch)) {

                        String[] nombre =entry.getValue().toString().split(" ");
                        Log.d("Clave email", entry.getKey() + "Cantidad" + entry.getValue().toString());
                        mostrar = mostrar + "Usuario: " + nombre[0] + "\n" + "Cantidad de giros: " + nombre[1] + "\n" + "Cantidad aceleradas: " + nombre[2] + "\n";
                    }
                }
                mostrarSensoreInfo.setText(mostrar);
                mostrarSensoreInfo.setMovementMethod(new ScrollingMovementMethod());

            }
        });

        verConsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityInfo();
            }
        });


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.values[2] > 5f || sensorEvent.values[2] < -5f){

                    giro++;
                    cantidad.setText(String.valueOf(giro));

                }

                if (giro >=1 && giro < 3){

                    colorBar.setBackgroundColor(Color.GREEN);

                }else if ( giro >= 3 && giro <= 5){

                    colorBar.setBackgroundColor(Color.YELLOW);

                }else if ( giro > 5){

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

                if ( -sensorEvent.values[1] > ACC && -sensorEvent.values[2] > ACC){

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

        verPromedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((acelero >=1 && acelero < 3) || (giro >=1 && giro < 3)){

                    colorBarPromedio.setBackgroundColor(Color.GREEN);

                }else if ( (acelero >= 3 && acelero <= 5) || (giro >=3 && giro < 5)){

                    colorBarPromedio.setBackgroundColor(Color.YELLOW);

                }else if ( acelero > 5 || giro > 5 ){

                    colorBarPromedio.setBackgroundColor(Color.RED);
                }
            }
        });

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

    public void openActivityInfo(){

        Intent intentInfo = new Intent(this , InfoActivity.class);
        startActivity(intentInfo);
        //finish();
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

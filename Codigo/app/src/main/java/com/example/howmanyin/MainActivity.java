package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Button botonRegistrar;

    Button botonIngresar;

    EditText textoUsuario;

    EditText textoCont;

    EditText textApellido;

    EditText textoEmail;

    EditText textoDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyReceiver = new MyReceiver();

        broadcastIntent();

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        botonRegistrar = findViewById(R.id.registrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityRegistro();

            }
        });

        botonIngresar = findViewById(R.id.ingresar);

        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textoUsuario = findViewById(R.id.editUser);
                String user = textoUsuario.getText().toString();

                textoCont = findViewById(R.id.editTextPass);
                String pass = textoCont.getText().toString();

                textApellido = findViewById(R.id.editCont);
                String apellido = textApellido.getText().toString();

                textoEmail = findViewById(R.id.editTextmail);
                String email = textoEmail.getText().toString();

                textoDNI = findViewById(R.id.editTextdni);
                String dni = textoDNI.getText().toString();

                validateInputs(user, apellido, dni, email, pass);

                //broadcastIntent();
            }
        });
    }

    private void validateInputs(String user , String apellido , String dni , String email , String pass){

        if (user.length() ==0 || pass.length() == 0 || apellido.length() == 0 || email.length() == 0 || dni.length() == 0){

            Toast.makeText(this,"No ha completado los campos",Toast.LENGTH_SHORT).show();

        }else{

            createUserFromLogin(user , apellido , dni , email , pass );

        }
    }


    private void createUserFromLogin( String user ,String apellido ,String dni ,String email ,String pass){

        Registracion reg = new Registracion();

        reg.setName(user);
        reg.setLastname(apellido);
        reg.setDni(dni);
        reg.setEmail(email);
        reg.setPassword(pass);
        reg.setCommission("6124");
        reg.setEnv("DEV");
        reg.setGroup("612");

        Log.d("Request enviado", reg.toString());

        Call<RegistroResponse> call = jsonPlaceHolderApi.createUserFromLogin(reg);

        call.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {

                if (!response.isSuccessful()){

                    Log.d("Response error", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), response.message(),Toast.LENGTH_SHORT).show();

                }
                else {

                    Log.d("Response correcto", String.valueOf(response.code()));
                    Log.d("Response body", response.body().toString());
                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();

                    loginEvent(response.body());
                    openActivityEvent();
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void broadcastIntent() {

        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void openActivityRegistro(){

        Intent intentRegistro = new Intent(this , LoginActivity.class);
        startActivity(intentRegistro);
        //finish();
    }

    public void openActivityEvent(){

        Intent intentEvent = new Intent(this , SensorActivity.class);
        startActivity(intentEvent);
        finish();
    }

    public void loginEvent(RegistroResponse response){

        EventRequest eventoLogin = new EventRequest();
        eventoLogin.setState("ACTIVO");
        eventoLogin.setEnv("DEV");
        eventoLogin.setType_events("Login");
        eventoLogin.setDescription("Evento del login de un usuario");

        Call<EventResponse> call = jsonPlaceHolderApi.createEvent(response.getToken() ,eventoLogin);

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                if (!response.isSuccessful()){

                    Log.d("Response error", String.valueOf(response.code()));
                    return ;
                }
                Log.d("Evento registrado", String.valueOf(response.code()));
                Log.d("Evento response", response.body().toString());
                Toast.makeText(getApplicationContext(), response.body().toString(),Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    @Override
    protected void onDestroy(){

        unregisterReceiver(MyReceiver);
        super.onDestroy();
    }

}
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private BroadcastReceiver MyReceiver = null;

    EditText textoUsuario;

    EditText textoCont;

    EditText textApellido;

    EditText textoEmail;

    EditText textoDNI;

    Button cancelar;

    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyReceiver = new MyReceiver();

        broadcastIntent();

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


         confirmar = findViewById(R.id.confirmar);
         confirmar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.confirmar:

                textoUsuario = findViewById(R.id.editTextNombre);
                String userReg = textoUsuario.getText().toString();

                textoCont = findViewById(R.id.editTextPass);
                String passReg = textoCont.getText().toString();

                textApellido = findViewById(R.id.editApellido);
                String apellidoReg = textApellido.getText().toString();

                textoEmail = findViewById(R.id.editTextEmail);
                String emailReg = textoEmail.getText().toString();

                textoDNI = findViewById(R.id.editTextDoc);
                String dniReg = textoDNI.getText().toString();

                validateInputs( userReg ,  apellidoReg ,  dniReg ,  emailReg ,  passReg);

                break;

        }
    }

    public void broadcastIntent() {

        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void validateInputs(String user , String apellido , String dni , String email , String pass){

        if (user.length() ==0 || pass.length() == 0 || apellido.length() == 0 || email.length() == 0 || dni.length() == 0){

            Toast.makeText(this,"No ha completado los campos",Toast.LENGTH_SHORT).show();

        } else{

            if (!isValidEmail(email)) {

                Log.i("Validar email", email);
                Toast.makeText(getApplicationContext(),"Email inválido", Toast.LENGTH_SHORT).show();

            }else if (!isValidPassword(pass)){

                Log.i("Validar pass", pass);
                Toast.makeText(getApplicationContext(),"Password inválido", Toast.LENGTH_SHORT).show();

            } else
            {
                createUserFromRegister(user, apellido, dni, email, pass);

            }
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 8) {
            return true;
        }
        return false;
    }

    private void createUserFromRegister (String user, String apellido, String dni, String email, String pass){

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

            Call<RegistroResponse> call = jsonPlaceHolderApi.createUserFromRegister(reg);

            call.enqueue(new Callback<RegistroResponse>() {
                @Override
                public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {

                    if (!response.isSuccessful()) {

                        Log.d("Response error", String.valueOf(response.code()));
                        if (response.message().equals("Bad Request")) {

                            Toast.makeText(getApplicationContext(), "Email o password no registrados", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{

                        Log.d("Response correcto", String.valueOf(response.code()));
                        Log.d("Response body", response.body().getToken());
                        Toast.makeText(getApplicationContext(), response.body().getToken(), Toast.LENGTH_SHORT).show();

                        loginEvent(response.body());
                        openActivityEvent();
                    }
                }

                @Override
                public void onFailure(Call<RegistroResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    public void openActivityRegistro(){

        Intent intentRegistro = new Intent(this , MainActivity.class);
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
package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

/*        Button botonCancelar = findViewById(R.id.cancelar);

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_main);


            }
        });*/

        Button cancelar = findViewById(R.id.cancelar);
        Button confirmar = findViewById(R.id.confirmar);

        cancelar.setOnClickListener(this);
        confirmar.setOnClickListener(this);


        /*Button botonConfirmar = findViewById(R.id.confirmar);

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textoUsuario = findViewById(R.id.editTextNombre);
                String userReg = textoUsuario.getText().toString();

                EditText textoCont = findViewById(R.id.editTextPass);
                String passReg = textoCont.getText().toString();

                EditText textApellido = findViewById(R.id.editApellido);
                String apellidoReg = textApellido.getText().toString();

                EditText textoEmail = findViewById(R.id.editTextEmail);
                String emailReg = textoEmail.getText().toString();

                EditText textoDNI = findViewById(R.id.editTextDoc);
                String dniReg = textoDNI.getText().toString();


                if (userReg.length() ==0 || passReg.length() == 0 || apellidoReg.length() == 0 || emailReg.length() == 0 || dniReg.length() == 0){

                    showMsg(view);

                }else{

                    createUserFromRegister(userReg , passReg , apellidoReg , emailReg , dniReg );
                    setContentView(R.layout.activity_sensor);
                }
            }

            public  void  showMsg(View view){

                Toast.makeText( getApplicationContext(),"No ha completado los campos",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.confirmar:

                EditText textoUsuario = findViewById(R.id.editTextNombre);
                String userReg = textoUsuario.getText().toString();

                EditText textoCont = findViewById(R.id.editTextPass);
                String passReg = textoCont.getText().toString();

                EditText textApellido = findViewById(R.id.editApellido);
                String apellidoReg = textApellido.getText().toString();

                EditText textoEmail = findViewById(R.id.editTextEmail);
                String emailReg = textoEmail.getText().toString();

                EditText textoDNI = findViewById(R.id.editTextDoc);
                String dniReg = textoDNI.getText().toString();


                if (userReg.length() ==0 || passReg.length() == 0 || apellidoReg.length() == 0 || emailReg.length() == 0 || dniReg.length() == 0){

                    Toast.makeText( this,"No ha completado los campos",Toast.LENGTH_SHORT).show();

                }else{

                    createUserFromRegister(userReg , passReg , apellidoReg , emailReg , dniReg );
                    setContentView(R.layout.activity_sensor);
                }
                break;
            case R.id.cancelar:
                setContentView(R.layout.activity_main);
                break;
        }
    }

        private void createUserFromRegister (String user, String pass, String dni, String email, String apellido){

            Registracion reg = new Registracion();

            reg.setName(user);
            reg.setLastname(apellido);
            reg.setDni(dni);
            reg.setEmail(email);
            reg.setPassword(pass);
            reg.setCommission("6124");
            reg.setEnv("DEV");
            reg.setGroup("612");

            Call<RegistroResponse> call = jsonPlaceHolderApi.createUserFromRegister(reg);

            call.enqueue(new Callback<RegistroResponse>() {
                @Override
                public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {

                    if (!response.isSuccessful()) {

                        Integer resultado = response.code();

                        Log.d("Response error", String.valueOf(response.code()));
                        return;
                    }

                    Log.d("Response correcto", String.valueOf(response.code()));
                    Log.d("Response body", response.body().getToken());
                    Toast.makeText(getApplicationContext(), response.body().getToken(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<RegistroResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
}
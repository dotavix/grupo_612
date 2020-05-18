package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

                validateInputs(user, pass, apellido, email, dni);
            }
        });
    }

    private void validateInputs(String user , String apellido , String dni , String email , String pass){

        if (user.length() ==0 || pass.length() == 0 || apellido.length() == 0 || email.length() == 0 || dni.length() == 0){

            Toast.makeText(this,"No ha completado los campos",Toast.LENGTH_SHORT).show();

        }else{

            createUserFromLogin(user , pass , dni , pass , apellido );
            setContentView(R.layout.activity_sensor);
        }
    }


    public void openActivityRegistro(){

        Intent intentRegistro = new Intent(this , LoginActivity.class);
        startActivity(intentRegistro);
    }

    private void createUserFromLogin( String user ,String pass ,String dni ,String email ,String apellido){

        Registracion reg = new Registracion();

        reg.setName(user);
        reg.setLastname(apellido);
        reg.setDni(dni);
        reg.setEmail(email);
        reg.setPassword(pass);
        reg.setCommission("6124");
        reg.setEnv("TEST");
        reg.setGroup("612");

        Call<Registracion> call = jsonPlaceHolderApi.createUserFromLogin(reg);

        call.enqueue(new Callback<Registracion>() {
            @Override
            public void onResponse(Call<Registracion> call, Response<Registracion> response) {

                if (!response.isSuccessful()){

                    Integer resultado = response.code();

                    Log.d("Response error", String.valueOf(response.code()));
                    return ;
                }
                Log.d("Response correcto", String.valueOf(response.code()));
                Toast.makeText(getApplicationContext(), response.body().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Registracion> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}
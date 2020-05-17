package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //createUserFromLogin(String user ,String pass ,String dni ,String email ,String apellido);

        Button botonRegistrar = findViewById(R.id.registrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_login);

            }
        });

        Button botonIngrear = findViewById(R.id.ingresar);

        botonIngrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText textoUsuario = findViewById(R.id.editUser);
                String user =textoUsuario.getText().toString();

                EditText textoCont = findViewById(R.id.editTextPass);
                String pass =textoCont.getText().toString();

                EditText textApellido = findViewById(R.id.editCont);
                String apellido =textApellido.getText().toString();

                EditText textoEmail = findViewById(R.id.editTextmail);
                String email =textoEmail.getText().toString();

                EditText textoDNI = findViewById(R.id.editTextdni);
                String dni =textoDNI.getText().toString();


                if (user.length() ==0 || pass.length() == 0 || apellido.length() == 0 || email.length() == 0 || dni.length() == 0){

                    showMsg(view);

                }else{

                    createUserFromLogin(user , pass , dni , pass , apellido );
                    setContentView(R.layout.activity_sensor);
                }
            }

            public  void  showMsg(View view){

                Toast.makeText(getApplicationContext(),"No ha completado los campos",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserFromLogin( String user ,String pass ,String dni ,String email ,String apellido){

        Registracion reg = new Registracion();

        reg.setName(user);
        reg.setLastname(apellido);
        reg.setDni(dni);
        reg.setEmail(email);
        reg.setPassword(pass);
        reg.setCommission("");
        reg.setEnv("TEST");
        reg.setGroup("612");

        Call<Registracion> call = jsonPlaceHolderApi.createUserFromLogin(reg);

        call.enqueue(new Callback<Registracion>() {
            @Override
            public void onResponse(Call<Registracion> call, Response<Registracion> response) {

                if (!response.isSuccessful()){

                    Integer resultado = response.code();

                    return ;
                }

                Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Registracion> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
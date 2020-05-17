package com.example.howmanyin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botonRegistrar = findViewById(R.id.registrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.activity_login);

            }
        });

        EditText textoUsuario = findViewById(R.id.editUser);
        String user =textoUsuario.getText().toString();

        EditText textoCont = findViewById(R.id.editCont);
        String pass =textoCont.getText().toString();

        if (user.length()==0){
            //EditText is empty
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }else{
            //EditText is not empty
            Toast.makeText(this, "Not Empty", Toast.LENGTH_SHORT).show();
        }
    }
}
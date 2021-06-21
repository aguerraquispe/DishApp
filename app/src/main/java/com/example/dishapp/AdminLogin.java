package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdminLogin extends AppCompatActivity {

    TextInputEditText usuario, password;
    MaterialButton btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btnIngresar = (MaterialButton) findViewById(R.id.btnIngresarAdmin);

        btnIngresar.setOnClickListener(view -> {

            usuario = (TextInputEditText) findViewById(R.id.txtUsuario);
            password = (TextInputEditText) findViewById(R.id.txtPassword);

            String string_usuario = usuario.getText().toString();
            String string_password = password.getText().toString();

            if (string_usuario.isEmpty() || string_password.isEmpty()) {
                Toast.makeText(this, "Ingresar los campos requeridos", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, MainAdmin.class);
                intent.putExtra("usuario", string_usuario);
                startActivity(intent);
                finish();
            }
        });


    }
}
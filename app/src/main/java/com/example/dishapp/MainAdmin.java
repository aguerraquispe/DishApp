package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainAdmin extends AppCompatActivity {

    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        usuario = getIntent().getStringExtra("usuario");

        Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
    }
}
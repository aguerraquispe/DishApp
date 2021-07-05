package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class User_lista_platos extends AppCompatActivity {
    String categoria;
    private TextView lblCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lista_platos);

        //asignando valores
        lblCategoria =(TextView) findViewById(R.id.lblCategoria);

        //seteando de activity anterior
        categoria = getIntent().getStringExtra("categoria");
        lblCategoria.setText(categoria);

    }

    public void RegresarAMain(View view){
        Toast.makeText(this,"Regresaste a Main",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
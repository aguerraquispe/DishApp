package com.example.dishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    public void IrAPedidos(View view){
        Toast.makeText(this,"Ingresa tus datos",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User_Datos_Pedido.class);
        startActivity(intent);
    }
}
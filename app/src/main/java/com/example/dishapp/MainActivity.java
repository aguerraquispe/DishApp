package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAdmin;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdmin = (ImageButton) findViewById(R.id.buttonAdmin);


        btnAdmin.setOnClickListener(view -> {

            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
        });
    }

    public void VerPlatosALaCarta(View view){
        categoria = "Platos a la carta";
        Toast.makeText(this,"Ingresaste a Platos a la carta",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        startActivity(intent);
        finish();
    }

    public void VerBebidas(View view){
        categoria = "Bebidas";
        Toast.makeText(this,"Ingresaste a Bebidas",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        startActivity(intent);
        finish();
    }
    public void VerRapido(View view){
        categoria = "Fast Food";
        Toast.makeText(this,"Ingresaste a Fast Food",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        startActivity(intent);
        finish();
    }
    public void VerPostres(View view){
        categoria = "Postres";
        Toast.makeText(this,"Ingresaste a Postres",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        startActivity(intent);
        finish();
    }

}
package com.example.dishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class User_Pedido_Confirmado extends AppCompatActivity {

    private MaterialButton btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pedido_confirmado);

        btnFinalizar = (MaterialButton) findViewById(R.id.btnFinalizar);

        btnFinalizar.setOnClickListener(view -> {
            Toast.makeText(this, "Muchas gracias por su preferencia", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        });
    }

    //metodo para cuando se toca el boton de "atras", usar override y llamar metodo ya establecido
    @Override
    public void onBackPressed(){

    }
}
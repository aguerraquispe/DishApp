package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainAdmin extends AppCompatActivity {

    String usuario;
    MaterialButton btnNuevoPlato, btnListaPedidos, btnVerPlatos;
    TextView txtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        usuario = getIntent().getStringExtra("usuario");

        txtUsuario = (TextView) findViewById(R.id.nameAdmin);

        btnVerPlatos = (MaterialButton) findViewById(R.id.btnVerPlatos);
        btnNuevoPlato = (MaterialButton) findViewById(R.id.btnNuevoPlato);
        btnListaPedidos = (MaterialButton) findViewById(R.id.btnListaPedidos);

        txtUsuario.setText("Bienvenido " + usuario);

        btnVerPlatos.setOnClickListener(mClicked);
        btnNuevoPlato.setOnClickListener(mClicked);
        btnListaPedidos.setOnClickListener(mClicked);
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.btnVerPlatos:
                Intent verPlatos = new Intent(this, AdminListaPlatos.class);
                startActivity(verPlatos);
                break;

            case R.id.btnNuevoPlato:
                Intent nuevoPlato = new Intent(this,AdminNuevoPlato.class);
                startActivity(nuevoPlato);
                break;

            case R.id.btnListaPedidos:
                Toast.makeText(this, "Lista pedidos", Toast.LENGTH_SHORT).show();
                break;
        }
    };
}
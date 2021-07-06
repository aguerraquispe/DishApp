package com.example.dishapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class User_Datos_Pedido extends AppCompatActivity {

    private TextInputEditText txtNombreUsuario, txtDireccion, txtNumero, txtPrecioTotal;
    private MaterialButton btnPedir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_datos_pedido);

        txtNombreUsuario=(TextInputEditText) findViewById(R.id.txtNombreUser);
        txtDireccion=(TextInputEditText) findViewById(R.id.txtDireccionUser);
        txtNumero=(TextInputEditText) findViewById(R.id.txtNumeroUser);
        txtPrecioTotal=(TextInputEditText) findViewById(R.id.txtPrecioTotalUser);

        btnPedir = (MaterialButton) findViewById(R.id.btnPedir);

        btnPedir.setOnClickListener(view -> {

            String string_usuario = txtNombreUsuario.getText().toString();
            String string_direccion = txtDireccion.getText().toString();
            String string_numero = txtNumero.getText().toString();

            if (string_usuario.isEmpty() || string_direccion.isEmpty() || string_numero.isEmpty()) {
                Toast.makeText(this, "Ingresar los datos de contacto", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, User_Pedido_Confirmado.class);
                intent.putExtra("usuario", string_usuario);
                startActivity(intent);
                finish();
            }
        });

    }
}
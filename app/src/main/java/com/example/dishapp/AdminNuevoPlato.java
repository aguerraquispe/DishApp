package com.example.dishapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AdminNuevoPlato extends AppCompatActivity {

    TextInputEditText txtNombrePlato, txtDescripcionPlato, txtPrecioPlato;
    AutoCompleteTextView dropdownCategorias;
    MaterialButton btnRegistrarPlato;

    AppCompatImageView imgPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_plato);

        txtNombrePlato = (TextInputEditText) findViewById(R.id.txtNombrePlato);
        txtDescripcionPlato = (TextInputEditText) findViewById(R.id.txtDescripcionPlato);
        txtPrecioPlato = (TextInputEditText) findViewById(R.id.txtPrecioPlato);
        dropdownCategorias = (AutoCompleteTextView) findViewById(R.id.dropdownCategoria);

        imgPlato = (AppCompatImageView) findViewById(R.id.imgPlato);

        btnRegistrarPlato = (MaterialButton) findViewById(R.id.btnRegistrarPlato);

        imgPlato.setOnClickListener(mClicked);
        btnRegistrarPlato.setOnClickListener(mClicked);

        String[] categoria = {"Platos a la Carta", "Bebidas", "Comida Rápida", "Postres"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.btnRegistrarPlato:
                Toast.makeText(this, "Registrar Plato", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgPlato:
                Toast.makeText(this, "Subir foto plato", Toast.LENGTH_SHORT).show();
                break;
        }
    };
}
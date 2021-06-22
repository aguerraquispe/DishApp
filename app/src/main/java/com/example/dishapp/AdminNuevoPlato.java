package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class AdminNuevoPlato extends AppCompatActivity {

    AutoCompleteTextView dropdownCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_plato);

        dropdownCategorias = (AutoCompleteTextView) findViewById(R.id.dropdownCategoria);

        String[] categoria = {"Categoria1", "Categoria2", "Categoria3", "Categoria4"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);
    }
}
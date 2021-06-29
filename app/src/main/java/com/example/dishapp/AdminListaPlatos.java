package com.example.dishapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dishapp.Adapter.Adapter_Plato;
import com.example.dishapp.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class AdminListaPlatos extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Plato> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_platos);

        elements = new ArrayList<>();
        elements.add(new Plato());

        Adapter_Plato adapter_plato = new Adapter_Plato(elements, this);

        recyclerView = (RecyclerView) findViewById(R.id.listaPlatos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter_plato);

        //Toast.makeText(this, "Ver platos", Toast.LENGTH_SHORT).show();
    }

}
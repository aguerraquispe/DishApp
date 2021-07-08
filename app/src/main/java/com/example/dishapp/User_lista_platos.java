package com.example.dishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.Adapter.Adapter_Usuario_Plato;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class User_lista_platos extends AppCompatActivity {
    String categoria;
    private TextView lblCategoria;

    RecyclerView rv_platos;
    Adapter_Usuario_Plato adapter_usuario_plato;

    //Llamar a Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lista_platos);

        //asignando valores
        lblCategoria = (TextView) findViewById(R.id.lblCategoria);

        //seteando de activity anterior
        categoria = getIntent().getStringExtra("categoria");
        lblCategoria.setText(categoria);

        iniciarFirebase();
        firebaseDatabase = FirebaseDatabase.getInstance();

        rv_platos = (RecyclerView) findViewById(R.id.userListaPlatos);
        rv_platos.setHasFixedSize(true);
        rv_platos.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Plato> options =
                new FirebaseRecyclerOptions.Builder<Plato>()
                        .setQuery(firebaseDatabase.getReference().child("Plato").orderByChild("categoria").equalTo(categoria), Plato.class)
                        .build();

        adapter_usuario_plato = new Adapter_Usuario_Plato(options);
        rv_platos.setAdapter(adapter_usuario_plato);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_usuario_plato.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_usuario_plato.stopListening();
    }

    private void iniciarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plato");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void IrAPedidos(View view) {
        Toast.makeText(this, "Ingresa tus datos", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, User_Datos_Pedido.class);
        startActivity(intent);
    }
}
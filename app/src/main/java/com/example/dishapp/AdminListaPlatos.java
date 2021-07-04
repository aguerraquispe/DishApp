package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dishapp.Adapter.Adapter_Plato;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminListaPlatos extends AppCompatActivity {

    RecyclerView rv_platos;
    Adapter_Plato adapter_plato;
    ArrayList<Plato> listPlato;

    //Llamar a Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_platos);

        iniciarFirebase();

        rv_platos = (RecyclerView) findViewById(R.id.listaPlatos);
        rv_platos.setHasFixedSize(true);
        rv_platos.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Plato> options =
                new FirebaseRecyclerOptions.Builder<Plato>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plato"), Plato.class)
                        .build();

        //listPlato = new ArrayList<>();
        adapter_plato = new Adapter_Plato(options);
        rv_platos.setAdapter(adapter_plato);
        //listarDatos();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_plato.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_plato.stopListening();
    }
/*
    private void listarDatos() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Plato plato = dataSnapshot.getValue(Plato.class);
                    listPlato.add(plato);
                }
                adapter_plato.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
*/
    private void iniciarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plato");
        storageReference = FirebaseStorage.getInstance().getReference();
    }
}
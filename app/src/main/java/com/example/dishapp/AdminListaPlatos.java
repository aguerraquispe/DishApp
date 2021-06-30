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

    ImageButton updatePlato;
    ImageButton deletePlato;

    //Llamar a Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_platos);

        updatePlato = (ImageButton) findViewById(R.id.updatePlato);
        deletePlato = (ImageButton) findViewById(R.id.deletePlato);

        rv_platos = (RecyclerView) findViewById(R.id.listaPlatos);
        iniciarFirebase();

        rv_platos.setHasFixedSize(true);
        rv_platos.setLayoutManager(new LinearLayoutManager(this));

        listPlato = new ArrayList<>();
        adapter_plato = new Adapter_Plato(this, listPlato);
        rv_platos.setAdapter(adapter_plato);
        listarDatos();

        updatePlato.setOnClickListener(mClicked);
        deletePlato.setOnClickListener(mClicked);
    }

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

            ;
        });
    }

    private void iniciarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plato");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.updatePlato:
                Toast.makeText(this, "Actualizar plato", Toast.LENGTH_SHORT).show();
                break;

            case R.id.deletePlato:
                Toast.makeText(this, "Eliminar plato", Toast.LENGTH_SHORT).show();
                break;
        }
    };
}
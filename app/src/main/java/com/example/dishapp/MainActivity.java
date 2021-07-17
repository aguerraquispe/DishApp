package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dishapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mibase;
    DatabaseReference mireference;
    Usuario usuario;

    ImageButton btnAdmin;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_DishApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdmin = (ImageButton) findViewById(R.id.buttonAdmin);


        btnAdmin.setOnClickListener(view -> {

            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
        });

        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference("clientes");

        mireference.child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //creamos el id del cliente y lo almacenamos en la tabla clientes
                usuario = new Usuario();
                usuario.setIdCliente(UUID.randomUUID().toString());

                mireference.child(usuario.getIdCliente()).child("informacion").setValue(usuario);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //Eliminar id del usuario en caso se salga de la aplicacion
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mireference.child(usuario.getIdCliente()).removeValue();
    }

    public void VerPlatosALaCarta(View view){
        categoria = "Platos a la Carta";
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        intent.putExtra("usuario",usuario.getIdCliente());
        startActivity(intent);
    }
    public void VerBebidas(View view){
        categoria = "Bebidas";
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        intent.putExtra("usuario",usuario.getIdCliente());
        startActivity(intent);
    }
    public void VerRapido(View view){
        categoria = "Comida Rápida";
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        intent.putExtra("usuario",usuario.getIdCliente());
        startActivity(intent);
    }
    public void VerPostres(View view){
        categoria = "Postres";
        Intent intent = new Intent(this,User_lista_platos.class);
        intent.putExtra("categoria",categoria);
        intent.putExtra("usuario",usuario.getIdCliente());
        startActivity(intent);
    }

}
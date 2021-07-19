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

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //Esta java se usa en la app

    FirebaseDatabase mibase;
    DatabaseReference mireference;
    String idUsuario;
    //Usuario usuario;

    ImageButton buttonAdmin, categ_platocarta, categ_bebida, categ_rapido, categ_postre;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_DishApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdmin = (ImageButton) findViewById(R.id.buttonAdmin);
        categ_platocarta = (ImageButton) findViewById(R.id.categ_platocarta);
        categ_bebida = (ImageButton) findViewById(R.id.categ_bebida);
        categ_rapido = (ImageButton) findViewById(R.id.categ_rapido);
        categ_postre = (ImageButton) findViewById(R.id.categ_postre);

        buttonAdmin.setOnClickListener(mClicked);
        categ_platocarta.setOnClickListener(mClicked);
        categ_bebida.setOnClickListener(mClicked);
        categ_rapido.setOnClickListener(mClicked);
        categ_postre.setOnClickListener(mClicked);

        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference("clientes");

        //usuario = new Usuario();
        idUsuario = UUID.randomUUID().toString();
        HashMap<String, Object> miusuario = new HashMap<>();
        miusuario.put("idCliente", idUsuario);

        mireference.child(idUsuario).child("informacion").setValue(miusuario);

        /*mireference.child("clientes").addValueEventListener(new ValueEventListener() {
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
        });*/
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.buttonAdmin:
                Intent intent = new Intent(this, AdminLogin.class);
                startActivity(intent);
                break;
            case R.id.categ_platocarta:
                categoria = "Platos a la Carta";
                verListaDisponible(categoria);
                break;
            case R.id.categ_bebida:
                categoria = "Bebidas";
                verListaDisponible(categoria);
                break;
            case R.id.categ_rapido:
                categoria = "Comida Rápida";
                verListaDisponible(categoria);
                break;
            case R.id.categ_postre:
                categoria = "Postres";
                verListaDisponible(categoria);
                break;
        }
    };

    private void verListaDisponible(String categoria) {
        Intent intent = new Intent(this, User_lista_platos.class);
        intent.putExtra("categoria", categoria);
        intent.putExtra("usuario", idUsuario);
        startActivity(intent);
    }

    //Eliminar id del usuario en caso se salga de la aplicacion
    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        mireference.child(usuario.getIdCliente()).removeValue();
    }*/
}
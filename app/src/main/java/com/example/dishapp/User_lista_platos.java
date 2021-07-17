package com.example.dishapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.Adapter.Adapter_Usuario_Plato;
import com.example.dishapp.model.Pedido;
import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Plato;
import com.example.dishapp.model.Usuario;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class User_lista_platos extends AppCompatActivity {
    String categoria;
    public String idUsuario;

    private TextView lblCategoria;
    private MaterialButton btnOrdenar;
    private LinearLayout layoutOrdenar;

    Usuario miusuario;
    double string_preciofinal = 0;

    RecyclerView rv_platos;
    Adapter_Usuario_Plato adapter_usuario_plato;

    ArrayList<Pedido_cliente> arrayprecio;

    //Llamar a Firebase
    FirebaseDatabase mibase;
    DatabaseReference mireference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lista_platos);

        arrayprecio = new ArrayList<>();

        //asignando valores
        lblCategoria = (TextView) findViewById(R.id.lblCategoria);
        layoutOrdenar = (LinearLayout) findViewById(R.id.layoutOrdenar);
        btnOrdenar = findViewById(R.id.btnOrdenar);

        //seteando de activity anterior
        idUsuario = getIntent().getStringExtra("usuario");
        categoria = getIntent().getStringExtra("categoria");
        lblCategoria.setText(categoria);

        //conexión y listado de platos
        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        rv_platos = (RecyclerView) findViewById(R.id.userListaPlatos);
        rv_platos.setHasFixedSize(true);
        rv_platos.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Plato> options =
                new FirebaseRecyclerOptions.Builder<Plato>()
                        .setQuery(mibase.getReference().child("Plato").orderByChild("categoria").equalTo(categoria), Plato.class)
                        .build();

        adapter_usuario_plato = new Adapter_Usuario_Plato(options, idUsuario);
        rv_platos.setAdapter(adapter_usuario_plato);


        mireference.child("clientes").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                miusuario = snapshot.getValue(Usuario.class);

                if (miusuario.getCarrito() != null) {
                    //El carrito ya existe
                    mireference.child("clientes").child(idUsuario).child("carrito").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            arrayprecio.clear();
                            if (snapshot.hasChildren()) {
                                for (DataSnapshot item : snapshot.getChildren()) {
                                    String idPlato = item.getKey();

                                    mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            Pedido_cliente mipedido = snapshot.getValue(Pedido_cliente.class);
                                            arrayprecio.add(mipedido);
                                            //hacer un loop por cada plato que este en el carrito
                                            definirTotal();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                } else {
                    //El carrito aun no existe
                    layoutOrdenar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void definirTotal() {
        double precioFinal = 0;
        for (Pedido_cliente pedido : arrayprecio) {
            precioFinal += pedido.getPrecioTotal();
        }
        string_preciofinal = precioFinal;

        layoutOrdenar.setVisibility(View.VISIBLE);
        btnOrdenar.setText("Ordenar (S/." + precioFinal + ")");
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

    public void IrAPedidos(View view) {
        Intent intent = new Intent(this, User_Datos_Pedido.class);
        intent.putExtra("precioFinal",string_preciofinal);
        intent.putExtra("idUsuario",idUsuario);
        startActivity(intent);
    }

}
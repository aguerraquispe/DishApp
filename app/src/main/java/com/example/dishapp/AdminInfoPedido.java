package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Plato;
import com.example.dishapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminInfoPedido extends AppCompatActivity {

    private String idCliente;
    private Usuario miusuario;

    private TextView tvNombreUser, tvDireccionUser, tvNumeroUser, tvPrecioTotal;
    private ListView lvCarrito;

    //Llamar a Firebase
    FirebaseDatabase mibase;
    DatabaseReference mireference;

    //Exclusivo para obtener
    ArrayList<Pedido_cliente> arrayprecio;

    //
    ArrayList<Pedido_cliente> listaCarrito;
    ArrayAdapter<Pedido_cliente> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info_pedido2);

        idCliente = getIntent().getStringExtra("idCliente");

        tvNombreUser = (TextView) findViewById(R.id.tvNombreUser);
        tvDireccionUser = (TextView) findViewById(R.id.tvDireccionUser);
        tvNumeroUser = (TextView) findViewById(R.id.tvNumeroUser);
        tvPrecioTotal = (TextView) findViewById(R.id.tvPrecioTotal);
        lvCarrito = (ListView) findViewById(R.id.lvCarrito);

        arrayprecio = new ArrayList<>();


        listaCarrito = new ArrayList<>();
        adapter = new ArrayAdapter<Pedido_cliente>(AdminInfoPedido.this, android.R.layout.simple_list_item_1, listaCarrito);
        lvCarrito.setAdapter(adapter);

        //conexión y listado de platos
        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        mireference.child("clientes").child(idCliente).child("informacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                miusuario = snapshot.getValue(Usuario.class);

                tvNombreUser.setText(miusuario.getNombre());
                tvDireccionUser.setText(miusuario.getDireccion());
                tvNumeroUser.setText(miusuario.getNumero());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //llenar la lista de pedidos hechos por el usuario
        mireference.child("clientes").child(idCliente).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listaCarrito.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String idPlato = item.getKey();

                        mireference.child("clientes").child(idCliente).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                Pedido_cliente mipedido = snapshot.getValue(Pedido_cliente.class);
                                listaCarrito.add(mipedido);
                                adapter.notifyDataSetChanged();
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

        //obtener el total a pagar por los productos
        mireference.child("clientes").child(idCliente).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayprecio.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String idPlato = item.getKey();

                        mireference.child("clientes").child(idCliente).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
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
    }

    private void definirTotal() {
        double precioFinal = 0;
        for (Pedido_cliente pedido : arrayprecio) {
            precioFinal += pedido.getPrecioTotal();
        }

        tvPrecioTotal.setText(Double.toString(precioFinal));
    }
}
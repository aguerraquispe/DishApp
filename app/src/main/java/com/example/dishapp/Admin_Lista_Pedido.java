package com.example.dishapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.Adapter.Adapter_Plato;
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
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Admin_Lista_Pedido extends AppCompatActivity {
    //Esta java se usa en la app

    private ListView lvPedidos;
    private MaterialButton verEnProceso, verFinalizado;

    ArrayList<Usuario> listaPedidos;
    ArrayAdapter<Usuario> adapter;

    //Llamar a Firebase
    FirebaseDatabase mibase;
    DatabaseReference mireference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_pedido);

        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        lvPedidos = (ListView) findViewById(R.id.lvPedidos);
        verEnProceso = (MaterialButton) findViewById(R.id.verEnProceso);
        verFinalizado = (MaterialButton) findViewById(R.id.verFinalizado);


        listaPedidos = new ArrayList<>();
        adapter = new ArrayAdapter<Usuario>(Admin_Lista_Pedido.this, android.R.layout.simple_list_item_1, listaPedidos);
        lvPedidos.setAdapter(adapter);

        lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //abrir nuevo activity con la informacion del cliente
                Intent intent = new Intent(getBaseContext(), AdminInfoPedido.class);
                intent.putExtra("idCliente", listaPedidos.get(position).getIdCliente());
                startActivity(intent);
            }
        });

        mireference.child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listaPedidos.clear();
                //analizamos si "clientes" tiene contenido dentro con hasChildren()
                if (snapshot.hasChildren()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        //Obtenemos el id de los clientes uno a uno en bucle
                        String idCliente = item.getKey();

                        mireference.child("clientes").child(idCliente).child("carrito").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot2) {

                                //Comprobamos si hay un carrito en el cliente, esto para evitar conflicto con el cliente
                                // que se crea por defecto al inicar la aplicacion

                                if (snapshot2.hasChildren()) {
                                    //El carrito si tiene objetos
                                    mireference.child("clientes").child(idCliente).child("informacion").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot3) {
                                            Usuario miusuario = snapshot3.getValue(Usuario.class);
                                            listaPedidos.add(miusuario);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }
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

        verEnProceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin_Lista_Pedido.this, "En proceso", Toast.LENGTH_SHORT).show();
            }
        });

        verFinalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin_Lista_Pedido.this, "Finalizado", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
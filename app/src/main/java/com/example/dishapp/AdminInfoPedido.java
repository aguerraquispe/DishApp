package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Plato;
import com.example.dishapp.model.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminInfoPedido extends AppCompatActivity {
    //Esta java se usa en la app

    private String idCliente;
    private Usuario miusuario;
    private String string_number;
    private String estado;

    private TextView tvNombreUser, tvDireccionUser, tvNumeroUser, tvPrecioTotal;
    private ListView lvCarrito;
    private ImageButton callPedido;
    private MaterialButton btnFinalizarPedido;
    private LinearLayout linearCallPedido;

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

        linearCallPedido = (LinearLayout) findViewById(R.id.linearCallPedido);
        callPedido = (ImageButton) findViewById(R.id.callPedido);
        btnFinalizarPedido = (MaterialButton) findViewById(R.id.btnFinalizarPedido);

        arrayprecio = new ArrayList<>();


        listaCarrito = new ArrayList<>();
        adapter = new ArrayAdapter<Pedido_cliente>(AdminInfoPedido.this, android.R.layout.simple_list_item_1, listaCarrito);
        lvCarrito.setAdapter(adapter);

        //llamar al cliente
        callPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num_enlace = "tel: +51" + string_number;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(num_enlace));
                startActivity(intent);
            }
        });

        //conexión a bd
        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        //colocar informacion , todavia no se llena la lista de pedidos
        mireference.child("clientes").child(idCliente).child("informacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                miusuario = snapshot.getValue(Usuario.class);

                tvNombreUser.setText(miusuario.getNombre());
                tvDireccionUser.setText(miusuario.getDireccion());
                tvNumeroUser.setText(miusuario.getNumero());

                string_number = miusuario.getNumero();
                estado = miusuario.getEstado();

                if(estado.equalsIgnoreCase("finalizado")){
                    linearCallPedido.setVisibility(View.GONE);
                    btnFinalizarPedido.setVisibility(View.GONE);
                    //Toast.makeText(AdminInfoPedido.this, "Finalizado", Toast.LENGTH_SHORT).show();
                }
                if(estado.equalsIgnoreCase("en proceso")){
                    //Toast.makeText(AdminInfoPedido.this, "En proceso", Toast.LENGTH_SHORT).show();
                }
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

        //finalizar el pedido
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear alerta preguntando si se desea eliminar el plato
                AlertDialog.Builder builder = new AlertDialog.Builder(btnFinalizarPedido.getContext());
                builder.setTitle("Finalizar Pedido");
                builder.setMessage("Una vez seleccione finalizar el pedido no se puede editar");

                //Se eligio eliminar plato
                builder.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Eliminar desde la base de datos obteniendo el ID del cliente (getRef(position).getKey())
                        HashMap<String,Object> mipedido = new HashMap<>();
                        mipedido.put("estado","Finalizado");

                        mireference.child("clientes").child(idCliente).child("informacion").updateChildren(mipedido);
                        finish();

                        /*final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mireference.child("clientes").child(idCliente).removeValue();
                                Toast.makeText(AdminInfoPedido.this, "Pedido Finalizado", Toast.LENGTH_SHORT).show();
                            }
                        }, 1000);
*/

                    }
                });

                //Se eligio No eliminar plato
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //Mostar la alerta
                builder.show();
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
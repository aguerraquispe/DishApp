package com.example.dishapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dishapp.model.Pedido;
import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class User_Datos_Pedido extends AppCompatActivity {
    //Esta java se usa en la app

    private String idUsuario;
    private double precioFinal;

    private TextInputEditText txtNombreUsuario, txtDireccion, txtNumero, txtPrecioTotal;
    private TextView tvPrecioTotal;
    private ListView lvCarrito;
    private MaterialButton btnPedir;

    //Llamar a Firebase
    FirebaseDatabase mibase;
    DatabaseReference mireference;

    ArrayList<Pedido_cliente> listaCarrito;
    ArrayAdapter<Pedido_cliente> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_datos_pedido);

        idUsuario = getIntent().getExtras().getString("idUsuario");
        precioFinal = getIntent().getExtras().getDouble("precioFinal");

        txtNombreUsuario = (TextInputEditText) findViewById(R.id.txtNombreUser);
        txtDireccion = (TextInputEditText) findViewById(R.id.txtDireccionUser);
        txtNumero = (TextInputEditText) findViewById(R.id.txtNumeroUser);
        tvPrecioTotal = (TextView) findViewById(R.id.tvPrecioTotal);
        tvPrecioTotal.setText(Double.toString(precioFinal));
        lvCarrito = (ListView) findViewById(R.id.lvCarrito);

        btnPedir = (MaterialButton) findViewById(R.id.btnPedir);

        listaCarrito = new ArrayList<>();
        adapter = new ArrayAdapter<Pedido_cliente>(User_Datos_Pedido.this, android.R.layout.simple_list_item_1, listaCarrito);
        lvCarrito.setAdapter(adapter);

        //conexión y listado de platos
        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        btnPedir.setOnClickListener(view -> {

            String string_usuario = txtNombreUsuario.getText().toString();
            String string_direccion = txtDireccion.getText().toString();
            String string_numero = txtNumero.getText().toString();

            if (string_usuario.isEmpty() || string_direccion.isEmpty() || string_numero.isEmpty()) {
                Toast.makeText(this, "Ingresar todos los datos de contacto", Toast.LENGTH_SHORT).show();
            } else {
                //Crear alerta preguntando si se desea hacer el pedido
                AlertDialog.Builder builder = new AlertDialog.Builder(btnPedir.getContext());
                builder.setTitle("Realizar pedido");
                builder.setMessage("Una vez registrado su pedido se le llamara para hacer la validación final y envio del pedido respectivo");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //agregamos los datos junto al id del cliente
                        HashMap<String, Object> miusuario = new HashMap<>();
                        miusuario.put("direccion", string_direccion);
                        miusuario.put("nombre", string_usuario);
                        miusuario.put("numero", string_numero);
                        miusuario.put("estado","En proceso");

                        mireference.child("clientes").child(idUsuario).child("informacion").updateChildren(miusuario);

                        Intent intent = new Intent(User_Datos_Pedido.this, User_Pedido_Confirmado.class);
                        startActivity(intent);
                        finish();
                    }
                });

                //Se eligio No eliminar plato
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //Mostar la alerta
                builder.show();
            }
        });

        //listar platos pedidos por el usuario
        mireference.child("clientes").child(idUsuario).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listaCarrito.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String idPlato = item.getKey();

                        mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
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
    }
}
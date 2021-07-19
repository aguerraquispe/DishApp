package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishapp.model.Pedido;
import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Plato;
import com.example.dishapp.model.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UserDetallePlato extends AppCompatActivity {

    TextView tvNombrePlato, tvPrecioPlato, tvCantidad, tvPreciototal;
    ImageView imgPlato;
    ImageButton addPlato, restPlato;
    MaterialButton btnAgregar;

    FirebaseDatabase mibase;
    DatabaseReference mireference;

    Plato miplato;
    String idPlato;
    String idUsuario;

    boolean ordenado;

    int cantidad = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detalle_plato);

        idPlato = getIntent().getExtras().getString("idPlato");
        idUsuario = getIntent().getExtras().getString("idUsuario");

        tvNombrePlato = findViewById(R.id.tvNombrePlato);
        tvPrecioPlato = findViewById(R.id.tvPrecioPlato);
        tvCantidad = findViewById(R.id.tvCantidad);
        tvPreciototal = findViewById(R.id.tvPrecioTotal);
        imgPlato = findViewById(R.id.imgPlato);
        addPlato = findViewById(R.id.addPlato);
        restPlato = findViewById(R.id.restPlato);
        btnAgregar = findViewById(R.id.btnAgregar);

        tvCantidad.setText(Integer.toString(cantidad));

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon_logo);

        addPlato.setOnClickListener(mClicked);
        restPlato.setOnClickListener(mClicked);
        btnAgregar.setOnClickListener(mClicked);

        mibase = FirebaseDatabase.getInstance();
        mireference = mibase.getReference();

        mireference.child("Plato").child(idPlato).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                miplato = snapshot.getValue(Plato.class);

                tvNombrePlato.setText(miplato.getNombrePlato());
                tvPrecioPlato.setText(Double.toString(miplato.getPrecio()));
                tvPreciototal.setText(Double.toString(miplato.getPrecio()));

                //Uso de la libreria picasso para colocar la imagen almacenada en la bd en la tarjeta
                Picasso.with(imgPlato.getContext())
                        .load(miplato.getImageURL())
                        .fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(imgPlato);

                setTitle(miplato.getNombrePlato());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //comprobar si el plato elegido ya se escogio antes
        mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {
                    ordenado = true;
                    //Toast.makeText(UserDetallePlato.this, "ya lo agregaste al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    ordenado = false;
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.addPlato:
                cambiarCantidad(0);
                break;

            case R.id.restPlato:
                cambiarCantidad(1);
                break;
            case R.id.btnAgregar:
                String nombrePlato = tvNombrePlato.getText().toString();
                int cantidad = Integer.parseInt(tvCantidad.getText().toString());
                double precioTotal = Double.parseDouble(tvPreciototal.getText().toString());

                if (!ordenado) {
                    //1ra vez que se agrega al carrito el plato
                    Map<String, Object> pedido = new HashMap<>();

                    pedido.put("idPlato", idPlato);
                    pedido.put("nombrePlato", nombrePlato);
                    pedido.put("precioTotal", precioTotal);
                    pedido.put("cantidad", cantidad);

                    mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).setValue(pedido);
                    Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //el plato ya fue pedido antes
                    Map<String, Object> pedido = new HashMap<>();

                    pedido.put("precioTotal", precioTotal);
                    pedido.put("cantidad", cantidad);

                    mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).updateChildren(pedido);
                    Toast.makeText(this, "Plato Actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    };

    private void cambiarCantidad(int opcion) {
        if (opcion == 0) {
            //agregar plato
            if (cantidad < 5) {
                cantidad++;
                tvCantidad.setText(Integer.toString(cantidad));

            } else {
                Toast.makeText(this, "Maximo 5 platos", Toast.LENGTH_SHORT).show();
            }
        }
        if (opcion == 1) {
            //restar plato
            if (cantidad > 1) {
                cantidad--;
                tvCantidad.setText(Integer.toString(cantidad));

            }
        }
        obtenerPrecioTotal();
    }

    private void obtenerPrecioTotal() {
        Double precioPlato = Double.parseDouble(tvPrecioPlato.getText().toString());
        Double calculoPrecio = precioPlato * cantidad;
        tvPreciototal.setText(Double.toString(calculoPrecio));
    }
}
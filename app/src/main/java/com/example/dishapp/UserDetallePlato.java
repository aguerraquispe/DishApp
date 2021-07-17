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

public class UserDetallePlato extends AppCompatActivity {

    FirebaseDatabase mibase;
    DatabaseReference mireference;

    TextView tvNombrePlato, tvPrecioPlato, tvCantidad, tvPreciototal;
    ImageView imgPlato;
    ImageButton addPlato, restPlato;

    MaterialButton btnAgregar;

    Plato miplato;
    String idPlato;
    String idUsuario;

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
                        .centerInside()
                        .into(imgPlato);

                setTitle("Detalle " + miplato.getNombrePlato());
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
                mireference.child("clientes").child(idUsuario).child("carrito").child(idPlato).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Pedido_cliente mipedido = new Pedido_cliente();
                        mipedido.setIdPlato(idPlato);
                        mipedido.setNombrePlato(tvNombrePlato.getText().toString());
                        mipedido.setCantidad(Integer.parseInt(tvCantidad.getText().toString()));
                        mipedido.setPrecioTotal(Double.parseDouble(tvPreciototal.getText().toString()));

                        mibase.getReference("clientes").child(idUsuario).child("carrito").child(idPlato).setValue(mipedido);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                finish();

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
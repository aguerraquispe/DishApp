package com.example.dishapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Plato extends FirebaseRecyclerAdapter<Plato, Adapter_Plato.ViewHolder> {

    public Adapter_Plato(@NonNull FirebaseRecyclerOptions<Plato> options) {
        super(options);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_plato_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull Adapter_Plato.ViewHolder holder, int position, @NonNull Plato plato) {
        holder.tvCategoriaPlato.setText(plato.getCategoria());
        holder.tvDescripciónPlato.setText(plato.getDescripción());
        holder.tvNombrePlato.setText(plato.getNombrePlato());
        holder.tvPrecioPlato.setText(plato.toString());

        Picasso.with(holder.imgPlato.getContext())
                .load(plato.getImageURL())
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.imgPlato);

        holder.updatePlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_position = Integer.toString(position);
                Toast.makeText(v.getContext(), "Actualizar " + str_position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.deletePlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.imgPlato.getContext());
                builder.setTitle("Eliminar Plato");
                builder.setMessage("Desea eliminar el plato?");

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Plato")
                                .child(getRef(position).getKey()).removeValue();

                        Toast.makeText(v.getContext(), "Plato Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato;
        ImageView imgPlato;

        ImageButton updatePlato, deletePlato;

        public ViewHolder(View view) {
            super(view);
            imgPlato = view.findViewById(R.id.imgPlato);
            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvCategoriaPlato = view.findViewById(R.id.tvCategoriaPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);

            updatePlato = view.findViewById(R.id.updatePlato);
            deletePlato = view.findViewById(R.id.deletePlato);
        }
    }

}


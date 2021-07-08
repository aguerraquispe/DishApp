package com.example.dishapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Adapter_Usuario_Plato extends FirebaseRecyclerAdapter<Plato, Adapter_Usuario_Plato.ViewHolder> {

    public Adapter_Usuario_Plato(@NonNull FirebaseRecyclerOptions<Plato> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Adapter_Usuario_Plato.ViewHolder holder, int position, @NonNull Plato plato) {
        //Colocar informacion de la base de datos a la tarjeta
        holder.tvDescripciónPlato.setText(plato.getDescripción());
        holder.tvNombrePlato.setText(plato.getNombrePlato());
        holder.tvPrecioPlato.setText(Double.toString(plato.getPrecio()));

        //Uso de la libreria picasso para colocar la imagen almacenada en la bd en la tarjeta
        Picasso.with(holder.imgPlato.getContext())
                .load(plato.getImageURL())
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .centerInside()
                .into(holder.imgPlato);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_info_plato, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Declarar varialbes para los campos
        TextView tvNombrePlato, tvDescripciónPlato, tvPrecioPlato;
        ImageView imgPlato;

        public ViewHolder(View view) {
            super(view);
            imgPlato = view.findViewById(R.id.imgPlato);

            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);
        }
    }
}

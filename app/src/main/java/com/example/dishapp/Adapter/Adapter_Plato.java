package com.example.dishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Plato;

import java.util.ArrayList;

public class Adapter_Plato extends RecyclerView.Adapter<Adapter_Plato.ViewHolder> {

    private Context context;
    private ArrayList<Plato> list_plato;

    public Adapter_Plato(final Context context, final ArrayList<Plato> list_plato) {
        this.context = context;
        this.list_plato = list_plato;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_plato_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Plato.ViewHolder holder, int position) {
        Plato plato = list_plato.get(position);
        holder.tvNombrePlato.setText(plato.getNombrePlato());
        holder.tvDescripciónPlato.setText(plato.getDescripción());
        holder.tvCategoriaPlato.setText(plato.getCategoria());
        holder.tvPrecioPlato.setText((int) plato.getPrecio());
    }

    @Override
    public int getItemCount() {
        return list_plato.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato;
        ImageView imgPlato;

        public ViewHolder(View view) {
            super(view);
            //imgPlato = view.findViewById(R.id.imgPlato);
            /*tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvCategoriaPlato = view.findViewById(R.id.tvCategoriaPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);*/
        }
    }
}


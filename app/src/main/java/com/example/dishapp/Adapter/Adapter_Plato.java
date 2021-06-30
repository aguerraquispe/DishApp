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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plato plato = list_plato.get(position);
        holder.tvCategoriaPlato.setText(plato.getCategoria());
        holder.tvDescripciónPlato.setText(plato.getDescripción());
        holder.tvNombrePlato.setText(plato.getNombrePlato());
        holder.tvPrecioPlato.setText(plato.toString());
        //holder.tvIDPlato.setText(plato.getUid());
        holder.tvImageURLPlato.setText(plato.getImageURL());
        //holder.imgPlato.getDrawable(R.drawable.burger);
    }

    @Override
    public int getItemCount() {
        return list_plato.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato, tvImageURLPlato, tvIDPlato;
        ImageView imgPlato;

        private StorageReference storageReference;

        public ViewHolder(View view) {
            super(view);
            imgPlato = view.findViewById(R.id.imgPlato);
            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvCategoriaPlato = view.findViewById(R.id.tvCategoriaPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);

            tvImageURLPlato = view.findViewById(R.id.tvImageURLPlato);
            storageReference = FirebaseStorage.getInstance().getReference("Plato/" + tvImageURLPlato);
        }
    }
}


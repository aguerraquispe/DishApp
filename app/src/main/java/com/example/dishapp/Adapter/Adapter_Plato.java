package com.example.dishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Plato;

import java.util.List;

public class Adapter_Plato extends RecyclerView.Adapter<Adapter_Plato.ViewHolder> {
    private List<Plato> mData;
    private LayoutInflater mInflater;
    private Context context;

    public Adapter_Plato(List<Plato> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Adapter_Plato.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.lista_platos, null);
        return new Adapter_Plato.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter_Plato.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato;
        ImageView imgPlato;

        ViewHolder(View view) {
            super(view);
            imgPlato = view.findViewById(R.id.imgPlato);
            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvCategoriaPlato = view.findViewById(R.id.tvCategoriaPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);
        }

        void bindData(final Plato item) {
            tvNombrePlato.setText(item.getNombrePlato());
            tvDescripciónPlato.setText(item.getNombrePlato());
            tvCategoriaPlato.setText(item.getNombrePlato());
            tvPrecioPlato.setText(item.getNombrePlato());
        }
    }
}


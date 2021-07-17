package com.example.dishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Pedido;
import com.example.dishapp.model.Pedido_cliente;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_Pedido extends FirebaseRecyclerAdapter<Pedido_cliente, Adapter_Pedido.ViewHolder> {

    public Adapter_Pedido(@NonNull FirebaseRecyclerOptions<Pedido_cliente> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull Adapter_Pedido.ViewHolder holder, int position, @NonNull @NotNull Pedido_cliente model) {

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_plato_admin, parent, false);
        return new Adapter_Pedido.ViewHolder(view);
    }

    //obtener los id de los campos de la tarjeta
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Declarar varialbes para los campos
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato;
        ImageView imgPlato;

        ImageButton updatePlato, deletePlato;

        //Enlacar los campos con el respectivo ID
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


/*
    Context contex;
    List<Pedido> lista_pedidos;

    public Adapter_Pedido(final Context contex,  List<Pedido> lista_pedidos) {
        this.contex = contex;
        this.lista_pedidos = lista_pedidos;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contex).inflate(R.layout.items_reporte_pedidos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Adapter_Pedido.ViewHolder holder, int position) {
        if (lista_pedidos!=null && lista_pedidos.size()>0){
            Pedido model = lista_pedidos.get(position);
            for (int i=0;i>model.getPlato().size();i++){
                holder.trPlato.setText(model.getPlato().get(i).getNombrePlato());
                holder.trCantidad.setText(model.getPlato().get(i).getCantidad());
                holder.trPrecio.setText(Double.toString(model.getPlato().get(i).getPrecioSubtotal()));
                holder.trFecha.setText(model.getFecha());
                holder.trEstado.setText(model.getEstado());
            }




        }else {

        }


    }

    @Override
    public int getItemCount() {
        return lista_pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView trPlato,trCantidad,trPrecio, trFecha, trEstado;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            trPlato=itemView.findViewById(R.id.trPlato);
            trCantidad=itemView.findViewById(R.id.trCantidad);
            trPrecio=itemView.findViewById(R.id.trPrecio);
            trFecha=itemView.findViewById(R.id.trFecha);
            trEstado=itemView.findViewById(R.id.trEstado);

        }
    }

 */

}

package com.example.dishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Pedido;

import java.util.List;

public class Adapter_Pedido extends RecyclerView.Adapter<Adapter_Pedido.ViewHolder> {

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
}

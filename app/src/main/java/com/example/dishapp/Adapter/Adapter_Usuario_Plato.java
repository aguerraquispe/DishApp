package com.example.dishapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.UserDetallePlato;
import com.example.dishapp.User_lista_platos;
import com.example.dishapp.model.Plato;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class Adapter_Usuario_Plato extends FirebaseRecyclerAdapter<Plato, Adapter_Usuario_Plato.ViewHolder> {

    private OnItemClickListener listener;
    private String idUsuario;

    /*public Adapter_Usuario_Plato(@NonNull FirebaseRecyclerOptions<Plato> options) {
        super(options);
    }*/

    public Adapter_Usuario_Plato(@NonNull FirebaseRecyclerOptions<Plato> options, final String idUsuario) {
        super(options);
        this.idUsuario = idUsuario;
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

        holder.tarjetaPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent intent = new Intent(v.getContext(), UserDetallePlato.class);
                intent.putExtra("idPlato", plato.getUid());
                intent.putExtra("idUsuario", idUsuario);

                activity.startActivity(intent);
                //Toast.makeText(v.getContext(), "Plato: " + plato.getUid(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_info_plato, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Declarar varialbes para los campos
        CardView tarjetaPlato;
        TextView tvNombrePlato, tvDescripciónPlato, tvPrecioPlato;
        ImageView imgPlato;

        public ViewHolder(View view) {
            super(view);
            tarjetaPlato = view.findViewById(R.id.tarjetaPlato);
            imgPlato = view.findViewById(R.id.imgPlato);

            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);

            /**********************************/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    /**********************************************************/
    public interface OnItemClickListener {
        void onItemClick(DataSnapshot dataSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

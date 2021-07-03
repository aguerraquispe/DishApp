package com.example.dishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.R;
import com.example.dishapp.model.Plato;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Plato extends RecyclerView.Adapter<Adapter_Plato.ViewHolder> {

    private Context context;
    private ArrayList<Plato> list_plato;

    //Llamar a Firebase
    //private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private OnItemClickListener listener;

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

        Picasso.with(context)
                .load(plato.getImageURL())
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.imgPlato);
    }

    @Override
    public int getItemCount() {
        return list_plato.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlato, tvDescripciónPlato, tvCategoriaPlato, tvPrecioPlato;
        ImageView imgPlato;

        Plato platoSelected;

        ImageButton updatePlato, deletePlato;

        //private StorageReference storageReference;

        public ViewHolder(View view) {
            super(view);
            imgPlato = view.findViewById(R.id.imgPlato);
            tvNombrePlato = view.findViewById(R.id.tvNombrePlato);
            tvDescripciónPlato = view.findViewById(R.id.tvDescripciónPlato);
            tvCategoriaPlato = view.findViewById(R.id.tvCategoriaPlato);
            tvPrecioPlato = view.findViewById(R.id.tvPrecioPlato);

            updatePlato = view.findViewById(R.id.updatePlato);
            deletePlato = view.findViewById(R.id.deletePlato);

            updatePlato.setOnClickListener(v -> {
                int position = getAdapterPosition();
                String str_position = Integer.toString(position);
                Toast.makeText(updatePlato.getContext(), "Actualizar " + str_position, Toast.LENGTH_SHORT).show();
            });
            deletePlato.setOnClickListener(v -> {
                int position = getAdapterPosition();
                String str_position = Integer.toString(position);
                String nombre = tvNombrePlato.getText().toString();

                Toast.makeText(deletePlato.getContext(), "Eliminar " + str_position + "- " + nombre, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void iniciarFirebase() {
        //FirebaseApp.initializeApp(this);
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference("Plato");
        storageReference = FirebaseStorage.getInstance().getReference("Plato");
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mlistener) {
        mlistener = listener;
    }
}


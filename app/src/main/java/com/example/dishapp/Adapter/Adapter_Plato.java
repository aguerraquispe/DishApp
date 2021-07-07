package com.example.dishapp.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Adapter_Plato extends FirebaseRecyclerAdapter<Plato, Adapter_Plato.ViewHolder> {

    public Adapter_Plato(@NonNull FirebaseRecyclerOptions<Plato> options) {
        super(options);
    }

    //retorna la tarjeta con su respectiva informacion
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_plato_admin, parent, false);
        return new ViewHolder(view);
    }

    //pasar la informacion de la bd a la tarjeta
    @Override
    protected void onBindViewHolder(@NonNull Adapter_Plato.ViewHolder holder, int position, @NonNull Plato plato) {
        //Colocar informacion de la base de datos a la tarjeta
        holder.tvCategoriaPlato.setText(plato.getCategoria());
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

        //Actulizar plato
        holder.updatePlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear cuadro de dialogo de editar plato
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imgPlato.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.activity_admin_edit_lista))
                        .setExpanded(true, 1100)
                        .create();

                //Obtener campos del cuadro de dialogo
                View view = dialogPlus.getHolderView();
                final EditText nombrePlato = view.findViewById(R.id.txtNombrePlato);
                final EditText descripciónPlato = view.findViewById(R.id.txtDescripcionPlato);
                final EditText precioPlato = view.findViewById(R.id.txtPrecioPlato);
                MaterialButton btnActualizar = view.findViewById(R.id.btnEditarPlato);

                //Pasar la informacion de la tarjeta al cuadro de dialogo del plato elegido
                nombrePlato.setText(plato.getNombrePlato());
                descripciónPlato.setText(plato.getDescripción());
                precioPlato.setText(Double.toString(plato.getPrecio()));

                //mostrar cuadro de dialogo
                dialogPlus.show();

                //se hace click en el boton de editar
                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre_string = nombrePlato.getText().toString().trim().toUpperCase();
                        String descripcion_string = upperCaseFirst(descripciónPlato.getText().toString().trim());
                        String precio_string = precioPlato.getText().toString();

                        //Datos correctos- Actualizar la base de datos
                        Map<String, Object> map = new HashMap<>();
                        map.put("nombrePlato", nombre_string);
                        map.put("descripción", descripcion_string);
                        map.put("precio", Double.parseDouble(precio_string));

                        FirebaseDatabase.getInstance().getReference().child("Plato")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(v.getContext(), "Plato actualizado", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(v.getContext(), "Fallo al actualar: " + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });

        //Eliminar plato
        holder.deletePlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear alerta preguntando si se desea eliminar el plato
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.imgPlato.getContext());
                builder.setTitle("Eliminar Plato");
                builder.setMessage("Desea eliminar el plato?");

                //Se eligio eliminar plato
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Eliminar desde la base de datos obteniendo el ID del plato (getRef(position).getKey())
                        FirebaseDatabase.getInstance().getReference().child("Plato")
                                .child(getRef(position).getKey()).removeValue();

                        Toast.makeText(v.getContext(), "Plato Eliminado", Toast.LENGTH_SHORT).show();
                    }
                });

                //Se eligio No eliminar plato
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //Mostar la alerta
                builder.show();
            }
        });
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

    //convertir a mayuscula 1ra letra
    public static String upperCaseFirst(String val){
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }
}


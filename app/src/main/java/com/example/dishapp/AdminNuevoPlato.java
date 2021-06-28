package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dishapp.model.Plato;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AdminNuevoPlato extends AppCompatActivity {

    private TextInputEditText txtNombrePlato, txtDescripcionPlato, txtPrecioPlato;
    private AutoCompleteTextView dropdownCategorias;
    private MaterialButton btnRegistrarPlato;

    private ImageView imgPlato;
    public Uri path;
    private static final int File = 1;

    //Llamar a Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_plato);

        txtNombrePlato = (TextInputEditText) findViewById(R.id.txtNombrePlato);
        txtDescripcionPlato = (TextInputEditText) findViewById(R.id.txtDescripcionPlato);
        txtPrecioPlato = (TextInputEditText) findViewById(R.id.txtPrecioPlato);
        dropdownCategorias = (AutoCompleteTextView) findViewById(R.id.dropdownCategoria);

        imgPlato = (ImageView) findViewById(R.id.imgPlato);

        btnRegistrarPlato = (MaterialButton) findViewById(R.id.btnRegistrarPlato);

        imgPlato.setOnClickListener(mClicked);
        btnRegistrarPlato.setOnClickListener(mClicked);

        //Definir categorias
        String[] categoria = {"Platos a la Carta", "Bebidas", "Comida Rápida", "Postres"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);

        iniciarFirebase();
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.btnRegistrarPlato:
                String nombre_string = txtNombrePlato.getText().toString();
                String descripcion_string = txtDescripcionPlato.getText().toString();
                String precio_string = txtPrecioPlato.getText().toString();
                String categoria_string = dropdownCategorias.getText().toString();
                //String imaegn = imgPlato.getDrawable().toString();
                //Toast.makeText(this, imaegn, Toast.LENGTH_SHORT).show();

                if (nombre_string.isEmpty() || descripcion_string.isEmpty() || precio_string.isEmpty() || categoria_string.isEmpty()) {
                    validacion();
                } else {
                    //Datos correctos- Agregar a la base de datos
                    Plato plato = new Plato();
                    plato.setUid(UUID.randomUUID().toString());
                    plato.setNombrePlato(nombre_string);
                    plato.setDescripción(descripcion_string);
                    plato.setPrecio(Double.parseDouble(precio_string));
                    plato.setCategoria(categoria_string);

                    databaseReference.child("Plato").child(plato.getUid()).setValue(plato);
                    subirImagen();
                    limpiarCajas();
                    Toast.makeText(this, "Plato Registrado", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.imgPlato:
                //Toast.makeText(this, "Subir foto plato", Toast.LENGTH_SHORT).show();
                cargarImagenPlato();
                break;
        }
    };

    private void limpiarCajas() {
        txtNombrePlato.setText("");
        txtDescripcionPlato.setText("");
        txtPrecioPlato.setText("");

        String[] categoria = {"Platos a la Carta", "Bebidas", "Comida Rápida", "Postres"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);
        imgPlato.setImageDrawable(getDrawable(R.drawable.ic_image));
    }

    private void validacion() {
        String nombre_string = txtNombrePlato.getText().toString();
        String descripcion_string = txtDescripcionPlato.getText().toString();
        String precio_string = txtPrecioPlato.getText().toString();
        String categoria_string = dropdownCategorias.getText().toString();

        if (nombre_string.isEmpty()) {
            txtNombrePlato.setError("Nombre de Plato requerido");
        } else if (descripcion_string.isEmpty()) {
            txtDescripcionPlato.setError("Descripción requerida");
        } else if (precio_string.isEmpty()) {
            txtPrecioPlato.setError("Precio requerido");
        } else if (categoria_string.isEmpty()) {
            dropdownCategorias.setError("Categoria requerida");
        }
    }

    private void cargarImagenPlato() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"), File);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            path = data.getData();
            imgPlato.setImageURI(path);
        }
    }

    private void subirImagen() {
        final String randomkey = UUID.randomUUID().toString();
        StorageReference Folder = storageReference.child("Plato" + randomkey);

        final StorageReference file_name = Folder.child("file"+path.getLastPathSegment());

        file_name.putFile(path)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //databaseReference.child("Plato").child(plato.getUid()).setValue(plato);

                        Toast.makeText(AdminNuevoPlato.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                    }
                });

        /*final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Subiendo....");
        progressDialog.show();

        final String randomkey = UUID.randomUUID().toString();
        //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageReference.child("images/" + randomkey);

        riversRef.putFile(path)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(R.id.content), "Imagen subida", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        progressDialog.dismiss();
                        Toast.makeText(AdminNuevoPlato.this, "Error al subir imagen", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Porcentaje: " + (int) progressPercent + "%");
                    }
                });*/
    }
}
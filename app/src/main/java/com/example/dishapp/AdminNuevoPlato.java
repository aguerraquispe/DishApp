package com.example.dishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dishapp.model.Plato;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AdminNuevoPlato extends AppCompatActivity {

    private TextInputEditText txtNombrePlato, txtDescripcionPlato, txtPrecioPlato;
    private AutoCompleteTextView dropdownCategorias;
    private MaterialButton btnRegistrarPlato;

    private ImageView imgPlato;
    public Uri path = null;
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
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_admin, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);

        iniciarFirebase();
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plato");
        storageReference = FirebaseStorage.getInstance().getReference("Plato");
    }

    private View.OnClickListener mClicked = view -> {
        switch (view.getId()) {
            case R.id.btnRegistrarPlato:
                subirPlato();
                break;

            case R.id.imgPlato:
                cargarImagenPlato();
                break;
        }
    };

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void subirPlato() {
        String nombre_string = txtNombrePlato.getText().toString().trim();
        String descripcion_string = txtDescripcionPlato.getText().toString().trim();
        String precio_string = txtPrecioPlato.getText().toString();
        String categoria_string = dropdownCategorias.getText().toString();

        if (nombre_string.isEmpty() || descripcion_string.isEmpty() || precio_string.isEmpty() || categoria_string.isEmpty() || path == null) {
            validacion();
        } else {
            //Todos los campos estan correctamente llenados, podemos subirlos al firebase
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(path));
            fileReference.putFile(path)
                    //obtenemos la direccion URL de la imagen almacenada en firebase
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return fileReference.getDownloadUrl();
                        }
                    })
                    //Ahora que la imagen se guardo, almacenamos los demas datos
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                //Datos correctos- Agregar a la base de datos
                                Plato plato = new Plato();
                                plato.setUid(UUID.randomUUID().toString());
                                plato.setNombrePlato(nombre_string);
                                plato.setDescripción(descripcion_string);
                                plato.setPrecio(Double.parseDouble(precio_string));
                                plato.setCategoria(categoria_string);
                                plato.setImageURL(downloadUri.toString());

                                databaseReference.child(plato.getUid()).setValue(plato);
                                limpiarCajas();
                                Toast.makeText(AdminNuevoPlato.this, "Plato Registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AdminNuevoPlato.this, "Fallo al registrar plato: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            /*
            fileReference.putFile(path)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //la imagen se almaceno asi que se pueden subir el resto de datos

                            //obtener codigo simplificado de la imagen
                            // String image_url;
                            //Uri imagen = taskSnapshot.getMetadata().getReference().getDownloadUrl();


                            //Datos correctos- Agregar a la base de datos
                            Plato plato = new Plato();
                            plato.setUid(UUID.randomUUID().toString());
                            plato.setNombrePlato(nombre_string);
                            plato.setDescripción(descripcion_string);
                            plato.setPrecio(Double.parseDouble(precio_string));
                            plato.setCategoria(categoria_string);
                            //plato.setImageURL(image_url);
                            //databaseReference.child("Plato").child(plato.getUid()).setValue(plato);
                            databaseReference.child(plato.getUid()).setValue(plato);
                            //subirImagen();
                            limpiarCajas();
                            Toast.makeText(AdminNuevoPlato.this, "Plato Registrado", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(this, "Plato Registrado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminNuevoPlato.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*/
        }
    }

    private void limpiarCajas() {
        txtNombrePlato.setText("");
        txtDescripcionPlato.setText("");
        txtPrecioPlato.setText("");

        String[] categoria = {"Platos a la Carta", "Bebidas", "Comida Rápida", "Postres"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_admin, categoria);
        dropdownCategorias.setText(adapter.getItem(0).toString(), false);
        dropdownCategorias.setAdapter(adapter);
        //cambiar imagen por defecto y limpiar codigo de imagen
        imgPlato.setImageDrawable(getDrawable(R.drawable.ic_image));
        path = null;
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
        } else if (path == null) {
            Toast.makeText(this, "Debe subir una imagen del plato", Toast.LENGTH_SHORT).show();
        }
    }

    //Pedir imagen que este en el almacenamiento del celular, NO URL
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
            //obtener codigo de imagen
            path = data.getData();
            //cambiar imagen
            imgPlato.setImageURI(path);
        }
    }
    ////////////////////////////////////Fin cargarImagenPlato()

    private void subirImagen() {
        //final String randomkey = UUID.randomUUID().toString();
        //StorageReference Folder = storageReference.child("Plato" + randomkey);

        StorageReference Folder = storageReference.child("Plato");

        final StorageReference file_name = Folder.child("file" + path.getLastPathSegment());

        file_name.putFile(path)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //databaseReference.child("Plato").child(plato.getUid()).setValue(plato);
                        Toast.makeText(AdminNuevoPlato.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AdminNuevoPlato.this, "Error al subir imagen", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
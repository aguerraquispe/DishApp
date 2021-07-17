package com.example.dishapp;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishapp.Adapter.Adapter_Pedido;
import com.example.dishapp.model.Pedido;
import com.example.dishapp.model.Plato;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminReportePedidos extends AppCompatActivity {

    private TableLayout tbPedidos;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    RecyclerView recyclerView;
    Adapter_Pedido adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reporte_pedidos);

        tbPedidos= (TableLayout) findViewById(R.id.tbPedidos);

        recyclerView = findViewById(R.id.recyclerTabla);

        //setRecyclerView();

    }

    /*private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter_Pedido(this,getList());
        recyclerView.setAdapter(adapter);
    }*/

    /*private List<Pedido> getList(){
        List<Pedido> lista_pedido = new ArrayList<>();
        lista_pedido.add(new Pedido("Abel","jiron","9554","todo",195.5,"12/5/21","enviado", (List<Plato>) new Plato("marisco",15.5,3,30.0)));
        return  lista_pedido;
    }*/

    void llenarTabla(){
        //conexion
        iniciarFirebase();
        //consulta

    }

    private void iniciarFirebase() {
        //FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Plato");
    }
}
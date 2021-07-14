package com.example.dishapp.model;

public class Plato_pedido {

    private Plato plato;
    private int cantidad;

    public Plato_pedido() {
    }

    public Plato getPlato() {
        return this.plato;
    }

    public void setPlato(final Plato plato) {
        this.plato = plato;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }


}

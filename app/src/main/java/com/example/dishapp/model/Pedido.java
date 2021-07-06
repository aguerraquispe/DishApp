package com.example.dishapp.model;

public class Pedido {
    private Plato idplato;
    private int cantidad;

    public Pedido() {
    }

    public Plato getIdplato() {
        return this.idplato;
    }

    public void setIdplato(final Plato idplato) {
        this.idplato = idplato;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }
}

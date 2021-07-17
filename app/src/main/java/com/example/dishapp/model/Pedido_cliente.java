package com.example.dishapp.model;

public class Pedido_cliente {
    private String idPedido;
    private String idPlato;
    private String nombrePlato;
    private int cantidad;
    private double precioTotal;

    public Pedido_cliente() {
    }

    public String getNombrePlato() {
        return this.nombrePlato;
    }

    public void setNombrePlato(final String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(final String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdPlato() {
        return this.idPlato;
    }

    public void setIdPlato(final String idPlato) {
        this.idPlato = idPlato;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return this.precioTotal;
    }

    public void setPrecioTotal(final double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return this.cantidad + " " + this.nombrePlato + " -  S/" + this.precioTotal;
    }
}

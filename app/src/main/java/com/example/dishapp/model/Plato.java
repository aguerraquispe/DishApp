package com.example.dishapp.model;

public class Plato {
    private String uid;
    private String NombrePlato;
    private String Descripción;
    private double Precio;
    private String Categoria;
    private String imageURL;
    private int Cantidad;
    private double PrecioSubtotal;

    public Plato() {
    }

    public Plato(String nombrePlato, double precio, int cantidad, double precioSubtotal) {
        this.NombrePlato = nombrePlato;
        this.Precio = precio;
        this.Cantidad = cantidad;
        this.PrecioSubtotal = precioSubtotal;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public String getNombrePlato() {
        return this.NombrePlato;
    }

    public void setNombrePlato(final String nombrePlato) {
        this.NombrePlato = nombrePlato;
    }

    public String getDescripción() {
        return this.Descripción;
    }

    public void setDescripción(final String descripción) {
        this.Descripción = descripción;
    }

    public double getPrecio() {
        return this.Precio;
    }

    public void setPrecio(final double precio) {
        this.Precio = precio;
    }

    public String getCategoria() {
        return this.Categoria;
    }

    public void setCategoria(final String categoria) {
        this.Categoria = categoria;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(final String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCantidad() {
        return this.Cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.Cantidad = cantidad;
    }

    public double getPrecioSubtotal() {
        return this.PrecioSubtotal;
    }

    public void setPrecioSubtotal(final double precioSubtotal) {
        this.PrecioSubtotal = precioSubtotal;
    }

    @Override
    public String toString() {
        return "" + Precio + "";
    }
}

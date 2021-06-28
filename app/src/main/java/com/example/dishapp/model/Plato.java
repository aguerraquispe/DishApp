package com.example.dishapp.model;

public class Plato {
    private String uid;
    private String NombrePlato;
    private String Descripción;
    private double Precio;
    private String Categoria;
    private String imageURL;

    public Plato() {
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

    @Override
    public String toString() {
        return NombrePlato;
    }
}

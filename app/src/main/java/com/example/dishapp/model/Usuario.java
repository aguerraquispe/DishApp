package com.example.dishapp.model;

public class Usuario {
    private String nombre;
    private String direccion;
    private String numero;
    private String resumen;
    private double preciototal;

    public Usuario() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getResumen() {
        return this.resumen;
    }

    public void setResumen(final String resumen) {
        this.resumen = resumen;
    }

    public double getPreciototal() {
        return this.preciototal;
    }

    public void setPreciototal(final double preciototal) {
        this.preciototal = preciototal;
    }


}

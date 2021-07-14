package com.example.dishapp.model;

import java.util.List;

public class Pedido {
    private String nombre;
    private String direccion;
    private String numero;
    private String resumen;
    private double preciototal;
    private String fecha;
    private String estado;
    private List<Plato> plato;

    public Pedido() {
    }

    public Pedido(String nombre, String direccion, String numero, String resumen, double preciototal, String fecha, String estado, List<Plato> plato) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.numero = numero;
        this.resumen = resumen;
        this.preciototal = preciototal;
        this.fecha = fecha;
        this.estado = estado;
        this.plato = plato;
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

    public double getPreciototal() {
        return this.preciototal;
    }

    public void setPreciototal(final double preciototal) {
        this.preciototal = preciototal;
    }

    public String getResumen() {
        return this.resumen;
    }

    public void setResumen(final String resumen) {
        this.resumen = resumen;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    public List<Plato> getPlato() {
        return this.plato;
    }

    public void setPlato(final List<Plato> plato) {
        this.plato = plato;
    }
}

package com.example.dishapp.model;

import java.util.HashMap;

public class Usuario {
    private String idCliente;
    private String nombre;
    private String direccion;
    private String numero;
    private String resumen; //creo que no va
    //private double preciototal; // creo que no va
    private HashMap<String, Pedido_cliente> carrito;

    public Usuario() {
    }

    public Usuario(final String idCliente, final String nombre, final String direccion, final String numero) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numero = numero;
        this.carrito = new HashMap<>();
    }

    public HashMap<String, Pedido_cliente> getCarrito() {
        return this.carrito;
    }

    public void setCarrito(final HashMap<String, Pedido_cliente> carrito) {
        this.carrito = carrito;
    }

    public String getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(final String idCliente) {
        this.idCliente = idCliente;
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

    @Override
    public String toString() {
        return nombre + " - " + numero;
        //return idCliente;
    }


    /*public double getPreciototal() {
        return this.preciototal;
    }

    public void setPreciototal(final double preciototal) {
        this.preciototal = preciototal;
    }*/


}

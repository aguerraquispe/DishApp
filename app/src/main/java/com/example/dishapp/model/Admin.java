package com.example.dishapp.model;

public class Admin {
    private String nombre;
    private int pedpendientes;
    private String user;
    private String pass;

    public Admin() {
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public int getPedpendientes() {
        return this.pedpendientes;
    }

    public void setPedpendientes(final int pedpendientes) {
        this.pedpendientes = pedpendientes;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }
}

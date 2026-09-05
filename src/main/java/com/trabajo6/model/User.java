package com.trabajo6.model;

public class User {
    private int id;
    private String usuario;
    private String clave;
    private boolean administrador;
    public User(int id, String usuario, String clave, boolean administrador) { this.id = id; this.usuario = usuario; this.clave = clave; this.administrador = administrador; }
    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
    public boolean isAdministrador() { return administrador; }
}

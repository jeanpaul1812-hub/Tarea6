package com.trabajo6.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String usuario;
    private String clave;
    @Column(name = "administrador")
    private boolean administrador;

    protected UserEntity() { }

    public Integer getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
    public boolean isAdministrador() { return administrador; }
}

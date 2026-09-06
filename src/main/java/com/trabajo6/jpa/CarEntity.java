package com.trabajo6.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diecast")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String marca;
    private String modelo;
    private String codigo;
    private Integer anio;
    private Integer numero;
    private String color;
    private String serie;
    private String otro;
    @Column(name = "thunt")
    private String thunt;

    protected CarEntity() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public String getOtro() { return otro; }
    public void setOtro(String otro) { this.otro = otro; }
    public String getThunt() { return thunt; }
    public void setThunt(String thunt) { this.thunt = thunt; }
}

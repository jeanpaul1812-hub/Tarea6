package com.trabajo6.model;

public class Car {
    private int id;
    private String marca;
    private String modelo;
    private String codigo;
    private int anio;
    private int numero;
    private String color;
    private String serie;
    private String otro;
    private String thunt;

    public Car() { }
    public Car(int id, String marca, String modelo, String codigo, int anio, int numero, String color, String serie, String otro, String thunt) {
        this.id = id; this.marca = marca; this.modelo = modelo; this.codigo = codigo; this.anio = anio; this.numero = numero; this.color = color; this.serie = serie; this.otro = otro; this.thunt = thunt;
    }
    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public String getMarca() { return marca; } public void setMarca(String v) { marca = v; }
    public String getModelo() { return modelo; } public void setModelo(String v) { modelo = v; }
    public String getCodigo() { return codigo; } public void setCodigo(String v) { codigo = v; }
    public int getAnio() { return anio; } public void setAnio(int v) { anio = v; }
    public int getNumero() { return numero; } public void setNumero(int v) { numero = v; }
    public String getColor() { return color; } public void setColor(String v) { color = v; }
    public String getSerie() { return serie; } public void setSerie(String v) { serie = v; }
    public String getOtro() { return otro; } public void setOtro(String v) { otro = v; }
    public String getThunt() { return thunt; } public void setThunt(String v) { thunt = v; }
}

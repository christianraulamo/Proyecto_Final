package com.example.chris.crao_final.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurante implements Serializable {

    private String IdRestaurante;

    @Expose
    @SerializedName("Nombre")
    private String Nombre;

    @Expose
    @SerializedName("Tipo")
    private String Tipo;

    @Expose
    @SerializedName("descripcion")
    private String descripcion;

    @Expose
    @SerializedName("foto")
    private String foto;

    public Restaurante() {
    }

    public Restaurante(String nombre, String foto) {
        Nombre = nombre;
        this.foto = foto;
    }

    public Restaurante(String nombre, String tipo, String descripcion, String foto) {
        Nombre = nombre;
        Tipo = tipo;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "Nombre='" + Nombre + '\'' +
                ", Tipo='" + Tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public String getIdRestaurante() {
        return IdRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        IdRestaurante = idRestaurante;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
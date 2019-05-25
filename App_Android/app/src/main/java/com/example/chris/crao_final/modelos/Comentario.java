package com.example.chris.crao_final.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comentario implements Serializable {

    @Expose
    @SerializedName("IdComentarios")
    private String IdComentarios;

    @Expose
    @SerializedName("Restaurante")
    private String Restaurante;

    @Expose
    @SerializedName("Usuario")
    private String Usuario;

    @Expose
    @SerializedName("Puntua")
    private String Puntua;

    @Expose
    @SerializedName("Comenta")
    private String Comenta;

    @Expose
    @SerializedName("PrecioMedio")
    private String PrecioMedio;

    public Comentario() {
    }

    public Comentario(String restaurante, String usuario, String puntua, String comenta, String precioMedio) {
        Restaurante = restaurante;
        Usuario = usuario;
        Puntua = puntua;
        Comenta = comenta;
        PrecioMedio = precioMedio;
    }

    public String getIdComentarios() {
        return IdComentarios;
    }

    public void setIdComentarios(String idComentarios) {
        IdComentarios = idComentarios;
    }

    public String getRestaurante() {
        return Restaurante;
    }

    public void setRestaurante(String restaurante) {
        Restaurante = restaurante;
    }

    public String getIdComentario() {
        return IdComentarios;
    }

    public void setIdComentario(String idComentarios) {
        IdComentarios = idComentarios;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPuntua() {
        return Puntua;
    }

    public void setPuntua(String puntua) {
        Puntua = puntua;
    }

    public String getComenta() {
        return Comenta;
    }

    public void setComenta(String comenta) {
        Comenta = comenta;
    }

    public String getPrecioMedio() {
        return PrecioMedio;
    }

    public void setPrecioMedio(String precioMedio) {
        PrecioMedio = precioMedio;
    }
}

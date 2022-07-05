package com.camila.ortiz.ortiz_t4;

import java.io.Serializable;

public class pelicualaGET implements Serializable {

    String nombre,fecha_de_estreno,tienda_1,tienda_2,tienda_3,imagen_url;
    Integer vistas;

    public pelicualaGET(String nombre, String fecha_de_estreno, String tienda_1, String tienda_2, String tienda_3, String imagen_url, Integer vistas) {
        this.nombre = nombre;
        this.fecha_de_estreno = fecha_de_estreno;
        this.tienda_1 = tienda_1;
        this.tienda_2 = tienda_2;
        this.tienda_3 = tienda_3;
        this.imagen_url = imagen_url;
        this.vistas = vistas;
    }

    public pelicualaGET() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_de_estreno() {
        return fecha_de_estreno;
    }

    public void setFecha_de_estreno(String fecha_de_estreno) {
        this.fecha_de_estreno = fecha_de_estreno;
    }

    public String getTienda_1() {
        return tienda_1;
    }

    public void setTienda_1(String tienda_1) {
        this.tienda_1 = tienda_1;
    }

    public String getTienda_2() {
        return tienda_2;
    }

    public void setTienda_2(String tienda_2) {
        this.tienda_2 = tienda_2;
    }

    public String getTienda_3() {
        return tienda_3;
    }

    public void setTienda_3(String tienda_3) {
        this.tienda_3 = tienda_3;
    }


    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public Integer getVistas() {
        return vistas;
    }

    public void setVistas(Integer vistas) {
        this.vistas = vistas;
    }
}

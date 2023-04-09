package es.miguel.polideportivo_v2.dominio;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

public class Actividad {

    private int id_actividad;
    private String descripcion;
    private int imagen;

    public Actividad() {
    }

    public Actividad(int id_actividad, String descripcion, int imagen) {
        this.id_actividad = id_actividad;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Actividad(String descripcion, int imagen) {
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(int id_actividad) {
        this.id_actividad = id_actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}

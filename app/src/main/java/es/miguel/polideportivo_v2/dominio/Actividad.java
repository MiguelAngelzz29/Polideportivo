package es.miguel.polideportivo_v2.dominio;

import java.util.ArrayList;

public class Actividad {

    private int id_actividad;
    private String descripcion;
    private int capacidad;
    private int numero_reservas;
    private int imagen;
    private boolean reservar;
    private int tipo_actividad;
    private ArrayList<ReservaActividad> listaReservasActividades;




    public Actividad() {
    }

    public Actividad(int id_actividad, String descripcion, int capacidad, int numero_reservas, int imagen, boolean reservar, int tipo_actividad, ArrayList<ReservaActividad> listaReservasActividades) {
        this.id_actividad = id_actividad;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.numero_reservas = numero_reservas;
        this.imagen = imagen;
        this.reservar = reservar;
        this.tipo_actividad = tipo_actividad;
        this.listaReservasActividades = listaReservasActividades;
    }

    public Actividad(String descripcion, int capacidad, int imagen) {
        this.descripcion = descripcion;
        this.capacidad = capacidad;
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

     public boolean isReservar() {
        return reservar;
    }

    public void setReservar(boolean reservar) {
        this.reservar = reservar;
    }

    public int getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(int tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public ArrayList<ReservaActividad> getListaReservasActividades() {
        return listaReservasActividades;
    }

    public void setListaReservasActividades(ArrayList<ReservaActividad> listaReservasActividades) {
        this.listaReservasActividades = listaReservasActividades;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getNumero_reservas() {
        return numero_reservas;
    }

    public void setNumero_reservas(int numero_reservas) {
        this.numero_reservas = numero_reservas;
    }
}

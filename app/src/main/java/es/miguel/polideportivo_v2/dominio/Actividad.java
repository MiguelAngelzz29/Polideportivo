package es.miguel.polideportivo_v2.dominio;

import java.util.ArrayList;

public class Actividad {

    private int id_actividad;
    private String descripcion;
    private int capacidad;
    private int imagen;
    private String horario;
    private boolean reservar;
    private int tipo_actividad;
    private ArrayList<ReservaActividad> listaReservasActividades;




    public Actividad() {
    }

    public Actividad(int id_actividad, String descripcion, int capacidad, int imagen, String horario, boolean reservar, int tipo_actividad, ArrayList<ReservaActividad> listaReservasActividades) {
        this.id_actividad = id_actividad;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.imagen = imagen;
        this.horario = horario;
        this.reservar = reservar;
        this.tipo_actividad = tipo_actividad;
        this.listaReservasActividades = listaReservasActividades;
    }

    public Actividad(String descripcion, int capacidad, int imagen, String horario) {
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.imagen = imagen;
        this.horario = horario;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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
}

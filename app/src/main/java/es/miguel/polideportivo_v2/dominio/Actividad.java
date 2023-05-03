package es.miguel.polideportivo_v2.dominio;

import java.util.ArrayList;

public class Actividad {

    private int id_actividad;
    private String nombre;
    private String descripcion;
    private int capacidad;
    private int numero_reservas;
    private String imagen;
    private String ubicacion;
    private int tipo_actividad;

    private ArrayList<ReservaActividad> listaReservasActividades;




    public Actividad() {
    }


    public Actividad(int id_actividad, String nombre, String descripcion, int capacidad, int numero_reservas, String imagen, String ubicacion, int tipo_actividad) {
        this.id_actividad = id_actividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.numero_reservas = numero_reservas;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
        this.tipo_actividad = tipo_actividad;
    }

    public Actividad(int id_actividad, String nombre, int capacidad, int numero_reservas, String imagen, String ubicacion, int tipo_actividad) {
        this.id_actividad = id_actividad;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.numero_reservas = numero_reservas;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
        this.tipo_actividad = tipo_actividad;
    }

    public Actividad(int id_actividad, String nombre, int capacidad, String imagen, String ubicacion, int tipo_actividad) {
        this.id_actividad = id_actividad;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
        this.tipo_actividad = tipo_actividad;
    }

    public Actividad(String descripcion, String imagen) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public int getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(int tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id_actividad='" + id_actividad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", capacidad=" + capacidad +
                ", numero_reservas=" + numero_reservas +
                ", imagen='" + imagen + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", tipo_actividad=" + tipo_actividad +
                ", listaReservasActividades=" + listaReservasActividades +
                '}';
    }
}

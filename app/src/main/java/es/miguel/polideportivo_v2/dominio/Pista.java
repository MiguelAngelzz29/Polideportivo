package es.miguel.polideportivo_v2.dominio;

import java.util.ArrayList;

public class Pista {

    private int id_pista;
    private String tipo_deporte;
    private String ubicacion;
    private boolean disponible;
    private String imagen;
    private double precio_sin_iluminacion;
    private double precio_con_iluminacion;

    private ArrayList<ReservaPista> listaReservas;

    public Pista(int i, String pádel, String s, boolean b, int pista_padel) {
    }

    public Pista(int id_pista, String tipo_deporte, String ubicacion, boolean disponible, String imagen, double precio_sin_iluminacion, double precion_con_iluminacion, ArrayList<ReservaPista> listaReservas) {
        this.id_pista = id_pista;
        this.tipo_deporte = tipo_deporte;
        this.ubicacion = ubicacion;
        this.disponible = disponible;
        this.imagen = imagen;
        this.precio_sin_iluminacion = precio_sin_iluminacion;
        this.precio_con_iluminacion = precion_con_iluminacion;
        this.listaReservas = listaReservas;
    }

    public Pista(String tipo_deporte, String ubicacion, boolean disponible, String imagen, double precio_sin_iluminacion, double precion_con_iluminacion, ArrayList<ReservaPista> listaReservas) {
        this.tipo_deporte = tipo_deporte;
        this.ubicacion = ubicacion;
        this.disponible = disponible;
        this.imagen = imagen;
        this.precio_sin_iluminacion = precio_sin_iluminacion;
        this.precio_con_iluminacion = precion_con_iluminacion;
        this.listaReservas = listaReservas;
    }

    public Pista(int id_pista, String tipo_deporte, String ubicacion, String imagen) {
        this.id_pista = id_pista;
        this.tipo_deporte = tipo_deporte;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public int getId_pista() {
        return id_pista;
    }

    public void setId_pista(int id_pista) {
        this.id_pista = id_pista;
    }

    public String getTipo_deporte() {
        return tipo_deporte;
    }

    public void setTipo_deporte(String tipo_deporte) {
        this.tipo_deporte = tipo_deporte;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getPrecio_tarifa() {
        return precio_sin_iluminacion;
    }

    public void setPrecio_tarifa(double precio_tarifa) {
        this.precio_sin_iluminacion = precio_tarifa;
    }

    public double getPrecio_con_iluminacion() {
        return precio_con_iluminacion;
    }

    public void setPrecio_con_iluminacion(double precio_con_iluminacion) {
        this.precio_con_iluminacion = precio_con_iluminacion;
    }


    public double getPrecio_sin_iluminacion() {
        return precio_sin_iluminacion;
    }

    public void setPrecio_sin_iluminacion(double precio_sin_iluminacion) {
        this.precio_sin_iluminacion = precio_sin_iluminacion;
    }

    public ArrayList<ReservaPista> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<ReservaPista> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "id_pista=" + id_pista +
                ", tipo_deporte='" + tipo_deporte + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", disponible=" + disponible +
                ", precio_tarifa=" + precio_sin_iluminacion +
                ", precio_iluminacion=" + precio_con_iluminacion +
                ", imagen=" + imagen +
                '}';
    }
}

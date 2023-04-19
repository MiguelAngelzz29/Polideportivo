package es.miguel.polideportivo_v2.dominio;

public class Instalacion {

    private int id_instalacion;
    private int tipo_instalacion;
    private double tarifa;

    public Instalacion() {
    }

    public Instalacion(int id_instalacion, int tipo_instalacion, double tarifa) {
        this.id_instalacion = id_instalacion;
        this.tipo_instalacion = tipo_instalacion;
        this.tarifa = tarifa;
    }

    public int getId_instalacion() {
        return id_instalacion;
    }

    public void setId_instalacion(int id_instalacion) {
        this.id_instalacion = id_instalacion;
    }

    public int getTipo_instalacion() {
        return tipo_instalacion;
    }

    public void setTipo_instalacion(int tipo_instalacion) {
        this.tipo_instalacion = tipo_instalacion;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }
}

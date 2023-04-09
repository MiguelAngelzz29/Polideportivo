package es.miguel.polideportivo_v2.dominio;

public class Pista {

    private int id_pista;
    private String tipo_deporte;
    private String ubicacion;
    private boolean disponible;
    private double precio_tarifa;
    private boolean iluminacion;
    private double precio_iluminacion;

    public Pista() {
    }

    public Pista(int id_pista, String tipo_deporte, String ubicacion, boolean disponible,
                 double precio_tarifa, boolean iluminacion, double precio_iluminacion) {
        this.id_pista = id_pista;
        this.tipo_deporte = tipo_deporte;
        this.ubicacion = ubicacion;
        this.disponible = disponible;
        this.precio_tarifa = precio_tarifa;
        this.iluminacion = iluminacion;
        this.precio_iluminacion = precio_iluminacion;
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
        return precio_tarifa;
    }

    public void setPrecio_tarifa(double precio_tarifa) {
        this.precio_tarifa = precio_tarifa;
    }

    public boolean isIluminacion() {
        return iluminacion;
    }

    public void setIluminacion(boolean iluminacion) {
        this.iluminacion = iluminacion;
    }

    public double getPrecio_iluminacion() {
        return precio_iluminacion;
    }

    public void setPrecio_iluminacion(double precio_iluminacion) {
        this.precio_iluminacion = precio_iluminacion;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "id_pista=" + id_pista +
                ", tipo_deporte='" + tipo_deporte + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", disponible=" + disponible +
                ", precio_tarifa=" + precio_tarifa +
                ", iluminacion=" + iluminacion +
                ", precio_iluminacion=" + precio_iluminacion +
                '}';
    }
}

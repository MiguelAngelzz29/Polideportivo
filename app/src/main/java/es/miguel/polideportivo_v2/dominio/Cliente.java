package es.miguel.polideportivo_v2.dominio;

import java.util.ArrayList;

public class Cliente {

    private int id_cliente;
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    private String password;
    private int tipo_abono;

    private ArrayList<ContrataInstalacion> listaContrataciones;
    private ArrayList<ReservaPista> listaReservaPistas;
    private ArrayList<ReservaActividad> listaReservaActividades;

    public Cliente() {
    }

    public Cliente(int id_cliente, String nombre, String primer_apellido, String segundo_apellido,
                   String dni, String direccion, String email, String telefono, String password,
                   int tipo_abono) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.tipo_abono = tipo_abono;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo_abono() {
        return tipo_abono;
    }

    public void setTipo_abono(int tipo_abono) {
        this.tipo_abono = tipo_abono;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nombre='" + nombre + '\'' +
                ", primer_apellido='" + primer_apellido + '\'' +
                ", segundo_apellido='" + segundo_apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", password='" + password + '\'' +
                ", tipo_abono=" + tipo_abono +
                '}';
    }
}

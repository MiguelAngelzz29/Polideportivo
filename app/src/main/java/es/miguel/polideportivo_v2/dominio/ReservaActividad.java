package es.miguel.polideportivo_v2.dominio;

import java.time.LocalDate;

public class ReservaActividad implements Comparable<ReservaActividad>{


        private String id_reserva_actividad;
        private LocalDate fecha_reserva;
        private String horario_reservado;
        private Actividad actividad;
        private Cliente cliente;

    public ReservaActividad() {
    }

    public ReservaActividad(String id_reserva_actividad, LocalDate fecha_reserva, String horario_reservado, Actividad actividad, Cliente cliente) {
        this.id_reserva_actividad = id_reserva_actividad;
        this.fecha_reserva = fecha_reserva;
        this.horario_reservado = horario_reservado;
        this.actividad = actividad;
        this.cliente = cliente;
    }

    public String getId_reserva_actividad() {
        return id_reserva_actividad;
    }

    public void setId_reserva_actividad(String id_reserva_actividad) {
        this.id_reserva_actividad = id_reserva_actividad;
    }

    public LocalDate getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(LocalDate fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getHorario_reservado() {
        return horario_reservado;
    }

    public void setHorario_reservado(String horario_reservado) {
        this.horario_reservado = horario_reservado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int compareTo(ReservaActividad otraReserva) {
        return this.horario_reservado.compareTo(otraReserva.getHorario_reservado());
    }
}

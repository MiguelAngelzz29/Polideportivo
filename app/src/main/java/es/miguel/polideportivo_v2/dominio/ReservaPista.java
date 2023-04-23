package es.miguel.polideportivo_v2.dominio;

import java.time.LocalDateTime;


public class ReservaPista implements Comparable<ReservaPista>{

    private String id_reserva_pista;
    private LocalDateTime fecha_reserva;
    private String horario_reservado;
    private double precio_pagado;
    private Pista pista;
    private Cliente cliente;



    public ReservaPista() {
    }

    public ReservaPista(String id_reserva_pista, LocalDateTime fecha_reserva, String horario_reservado, double precio_pagado, Pista pista, Cliente cliente) {
        this.id_reserva_pista = id_reserva_pista;
        this.fecha_reserva = fecha_reserva;
        this.horario_reservado = horario_reservado;
        this.precio_pagado = precio_pagado;
        this.pista = pista;
        this.cliente = cliente;
    }

    public ReservaPista(LocalDateTime  fecha_reserva, String horario_reservado, double precio_pagado, Pista pista, Cliente cliente) {
        this.fecha_reserva = fecha_reserva;
        this.horario_reservado = horario_reservado;
        this.precio_pagado = precio_pagado;
        this.pista = pista;
        this.cliente = cliente;
    }

    public ReservaPista(LocalDateTime fecha_reserva, String horario_reservado, double precio_pagado, Pista pista) {
        this.fecha_reserva = fecha_reserva;
        this.horario_reservado = horario_reservado;
        this.precio_pagado = precio_pagado;
        this.pista = pista;
    }

    public ReservaPista(String horario_reservado, Pista pista) {
        this.horario_reservado = horario_reservado;
        this.pista = pista;
    }



    public String getId_reserva_pista() {
        return id_reserva_pista;
    }

    public void setId_reserva_pista(String id_reserva_pista) {
        this.id_reserva_pista = id_reserva_pista;
    }

    public LocalDateTime  getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(LocalDateTime fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getHorario_reservado() {
        return horario_reservado;
    }

    public void setHorario_reservado(String horario_reservado) {
        this.horario_reservado = horario_reservado;
    }

    public double getPrecio_pagado() {
        return precio_pagado;
    }

    public void setPrecio_pagado(double precio_pagado) {
        this.precio_pagado = precio_pagado;
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int compareTo(ReservaPista otraReserva) {
        return this.horario_reservado.compareTo(otraReserva.getHorario_reservado());
    }

    @Override
    public String toString() {
        return "ReservaPista{" +
                "id_reserva_pista='" + id_reserva_pista + '\'' +
                ", fecha_reserva=" + fecha_reserva +
                ", horario_reservado='" + horario_reservado + '\'' +
                ", precio_pagado=" + precio_pagado +
                ", pista=" + pista +
                ", cliente=" + cliente +
                '}';
    }
}

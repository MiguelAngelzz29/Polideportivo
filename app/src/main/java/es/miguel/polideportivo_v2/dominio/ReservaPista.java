package es.miguel.polideportivo_v2.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservaPista {

    private int id_reserva_pista;
    private LocalDate fecha_reserva;
    private LocalDateTime horario_reserva;
    private double precio_pagado;
    Pista pista;
    Cliente cliente;

}

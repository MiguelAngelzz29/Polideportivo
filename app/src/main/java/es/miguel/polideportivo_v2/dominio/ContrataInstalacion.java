package es.miguel.polideportivo_v2.dominio;

import java.time.LocalDate;

public class ContrataInstalacion {

    private String id_contratacion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private double precio_pagado;
    private Instalacion instalacion;
    private Cliente cliente;

    public ContrataInstalacion() {
    }

    public ContrataInstalacion(String id_contratacion, LocalDate fecha_inicio, LocalDate fecha_fin, double precio_pagado, Instalacion instalacion, Cliente cliente) {
        this.id_contratacion = id_contratacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.precio_pagado = precio_pagado;
        this.instalacion = instalacion;
        this.cliente = cliente;
    }

    public String getId_contratacion() {
        return id_contratacion;
    }

    public void setId_contratacion(String id_contratacion) {
        this.id_contratacion = id_contratacion;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public double getPrecio_pagado() {
        return precio_pagado;
    }

    public void setPrecio_pagado(double precio_pagado) {
        this.precio_pagado = precio_pagado;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

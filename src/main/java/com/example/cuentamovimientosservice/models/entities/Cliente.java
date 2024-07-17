package com.example.cuentamovimientosservice.models.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class Cliente implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    private Long clienteId;
    private String nombre;
    private String identificacion;

    // Constructores
    public Cliente() {
    }

    public Cliente(Long clienteId, String nombre, String identificacion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    // Getters y setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

}
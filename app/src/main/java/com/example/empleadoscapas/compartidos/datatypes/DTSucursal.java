package com.example.empleadoscapas.compartidos.datatypes;

import java.io.Serializable;
import java.util.Objects;

public class DTSucursal implements Serializable {

    private long id;
    private String nombre;
    private String direccion;
    private Integer superficie;
    private boolean estacionamiento;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public boolean getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(boolean estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public DTSucursal() {
        this(1, null, null, null, false);
    }

    public DTSucursal(String nombre, String direccion, Integer superficie, boolean estacionamiento) {
        this(1, nombre, direccion, superficie, estacionamiento);
    }

    public DTSucursal(long id, String nombre, String direccion, Integer superficie, boolean estacionamiento) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.superficie = superficie;
        this.estacionamiento = estacionamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTSucursal sucursal = (DTSucursal) o;
        return id == sucursal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getNombre();
    }

}

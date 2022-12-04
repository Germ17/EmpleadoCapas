package com.example.empleadoscapas.compartidos.datatypes;

import java.io.Serializable;
import java.util.Date;

public class DTEmpleado implements Serializable {

    private long id;
    private int cedula;
    private String nombre;
    private Date fechaIngreso;
    private double sueldo;
    private DTSucursal sucursal;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public DTSucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(DTSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public DTEmpleado() {
        this(1, 1, "N/D", new Date(), 0, new DTSucursal());
    }

    public DTEmpleado(int cedula, String nombre, Date fechaIngreso, double sueldo, DTSucursal sucursal) {
        this(1, cedula, nombre, fechaIngreso, sueldo, sucursal);
    }

    public DTEmpleado(long id, int cedula, String nombre, Date fechaIngreso, double sueldo, DTSucursal sucursal) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.sueldo = sueldo;
        this.sucursal = sucursal;
    }

}

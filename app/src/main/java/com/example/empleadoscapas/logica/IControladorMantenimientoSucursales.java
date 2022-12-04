package com.example.empleadoscapas.logica;

import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.util.List;

public interface IControladorMantenimientoSucursales {

    List<DTSucursal> listarSucursales() throws ExcepcionEmpleados;
    void agregarSucursal(DTSucursal sucursal) throws ExcepcionEmpleados;
    void modificarSucursal(DTSucursal sucursal) throws ExcepcionEmpleados;
    void eliminarSucursal(long id) throws ExcepcionEmpleados;

}

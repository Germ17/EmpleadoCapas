package com.example.empleadoscapas.logica;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.util.List;

public interface IControladorMantenimientoEmpleados {

    List<DTSucursal> listarSucursales() throws ExcepcionEmpleados;
    List<DTEmpleado> listarEmpleados() throws ExcepcionEmpleados;
    void agregarEmpleado(DTEmpleado empleado) throws ExcepcionEmpleados;
    void modificarEmpleado(DTEmpleado empleado) throws ExcepcionEmpleados;
    void eliminarEmpleado(int cedula) throws ExcepcionEmpleados;

}

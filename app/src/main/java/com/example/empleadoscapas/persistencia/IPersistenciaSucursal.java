package com.example.empleadoscapas.persistencia;

import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.util.List;

public interface IPersistenciaSucursal {

    List<DTSucursal> listar() throws ExcepcionEmpleados;
    DTSucursal obtener(long id) throws ExcepcionEmpleados;
    void agregar(DTSucursal sucursal) throws ExcepcionEmpleados;
    void modificar(DTSucursal sucursal) throws ExcepcionEmpleados;
    void eliminar(long id) throws ExcepcionEmpleados;

}

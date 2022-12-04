package com.example.empleadoscapas.persistencia;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.util.List;

public interface IPersistenciaEmpleado {

    List<DTEmpleado> listar() throws ExcepcionEmpleados;
    DTEmpleado obtener(int cedula) throws ExcepcionEmpleados;
    void agregar(DTEmpleado empleado) throws ExcepcionEmpleados;
    void modificar(DTEmpleado empleado) throws ExcepcionEmpleados;
    void eliminar(int cedula) throws ExcepcionEmpleados;

}

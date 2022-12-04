package com.example.empleadoscapas.logica;

import android.content.Context;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;
import com.example.empleadoscapas.persistencia.FabricaPersistencia;

import java.util.List;

class ControladorMantenimientoEmpleados implements IControladorMantenimientoEmpleados {

    private static ControladorMantenimientoEmpleados instancia;


    public static ControladorMantenimientoEmpleados getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new ControladorMantenimientoEmpleados(contexto);
        }

        return instancia;
    }


    private Context contexto;


    private ControladorMantenimientoEmpleados(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }


    public List<DTSucursal> listarSucursales()
            throws ExcepcionEmpleados {
        return FabricaPersistencia.getPersistenciaSucursal(contexto).listar();
    }

    @Override
    public List<DTEmpleado> listarEmpleados()
            throws ExcepcionEmpleados {
        return FabricaPersistencia.getPersistenciaEmpleado(contexto).listar();
    }

    @Override
    public void agregarEmpleado(DTEmpleado empleado)
            throws ExcepcionEmpleados {
        LogicaEmpleado.getInstancia().validar(empleado);

        FabricaPersistencia.getPersistenciaEmpleado(contexto).agregar(empleado);
    }

    @Override
    public void modificarEmpleado(DTEmpleado empleado)
            throws ExcepcionEmpleados {
        LogicaEmpleado.getInstancia().validar(empleado);

        FabricaPersistencia.getPersistenciaEmpleado(contexto).modificar(empleado);
    }

    @Override
    public void eliminarEmpleado(int cedula)
            throws ExcepcionEmpleados {
        FabricaPersistencia.getPersistenciaEmpleado(contexto).eliminar(cedula);
    }

}

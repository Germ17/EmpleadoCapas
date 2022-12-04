package com.example.empleadoscapas.logica;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionLogica;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

class LogicaEmpleado {

    private static LogicaEmpleado instancia;


    public static LogicaEmpleado getInstancia() {
        if (instancia == null) {
            instancia = new LogicaEmpleado();
        }

        return instancia;
    }


    private LogicaEmpleado() {

    }


    public void validar(DTEmpleado empleado)
            throws ExcepcionEmpleados {
        validar(empleado, false);
    }

    public void validar(DTEmpleado empleado, boolean validarOtros)
            throws ExcepcionEmpleados {
        if (empleado == null) {
            throw new ExcepcionLogica("El empleado es nulo.");
        }

        if (empleado.getCedula() < 1) {
            throw new ExcepcionLogica("La cédula debe ser mayor o igual a 1.");
        }

        if (empleado.getNombre() != null) {
            empleado.setNombre(empleado.getNombre().trim());
        }

        if (empleado.getNombre() == null || empleado.getNombre().isEmpty()) {
            throw new ExcepcionLogica("El nombre no puede quedar vacío.");
        }

        if (empleado.getNombre().length() > 25) {
            throw new ExcepcionLogica("El nombre no puede tener más de 25 caracteres.");
        }

        if (empleado.getFechaIngreso() == null) {
            throw new ExcepcionLogica("La fecha de ingreso no puede quedar vacía.");
        }

        if (empleado.getSueldo() < 0) {
            throw new ExcepcionLogica("El sueldo debe ser mayor o igual a 0.");
        }

        if (empleado.getSucursal() == null) {
            throw new ExcepcionLogica("La sucursal no puede quedar vacía.");
        }

        if (validarOtros) {
            LogicaSucursal.getInstancia().validar(empleado.getSucursal());
        }
    }

}

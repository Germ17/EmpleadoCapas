package com.example.empleadoscapas.logica;

import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionLogica;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

class LogicaSucursal {

    private static LogicaSucursal instancia;


    public static LogicaSucursal getInstancia() {
        if (instancia == null) {
            instancia = new LogicaSucursal();
        }

        return instancia;
    }


    private LogicaSucursal() {

    }


    public void validar(DTSucursal sucursal)
            throws ExcepcionEmpleados {
        if (sucursal == null) {
            throw new ExcepcionLogica("La sucursal es nula.");
        }

        if (sucursal.getNombre() == null || sucursal.getNombre().isEmpty()) {
            throw new ExcepcionLogica("El nombre no puede quedar vacío.");
        }

        sucursal.setNombre(sucursal.getNombre().trim());

        if (sucursal.getNombre().length() > 25) {
            throw new ExcepcionLogica("El nombre no puede tener más de 25 caracteres.");
        }

        if (sucursal.getDireccion() != null) {
            sucursal.setDireccion(sucursal.getDireccion().trim());
        }

        if (sucursal.getDireccion() != null && sucursal.getDireccion().isEmpty()) {
            sucursal.setDireccion(null);
        }

        if (sucursal.getDireccion() != null && sucursal.getDireccion().length() > 50) {
            throw new ExcepcionLogica("La dirección no puede tener más de 50 caracteres.");
        }

        if (sucursal.getSuperficie() != null && sucursal.getSuperficie() < 1) {
            throw new ExcepcionLogica("La superficie debe ser mayor o igual a 1.");
        }
    }

}

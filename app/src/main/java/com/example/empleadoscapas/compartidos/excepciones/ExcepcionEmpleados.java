package com.example.empleadoscapas.compartidos.excepciones;

public class ExcepcionEmpleados extends Exception {

    public ExcepcionEmpleados() {

    }

    public ExcepcionEmpleados(String mensaje) {
        super(mensaje);
    }

    public ExcepcionEmpleados(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

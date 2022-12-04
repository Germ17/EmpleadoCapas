package com.example.empleadoscapas.compartidos.excepciones;

public class ExcepcionPresentacion extends ExcepcionEmpleados {

    public ExcepcionPresentacion() {

    }

    public ExcepcionPresentacion(String mensaje) {
        super(mensaje);
    }

    public ExcepcionPresentacion(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

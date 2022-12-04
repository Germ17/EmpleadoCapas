package com.example.empleadoscapas.compartidos.excepciones;

public class ExcepcionLogica extends ExcepcionEmpleados {

    public ExcepcionLogica() {

    }

    public ExcepcionLogica(String mensaje) {
        super(mensaje);
    }

    public ExcepcionLogica(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

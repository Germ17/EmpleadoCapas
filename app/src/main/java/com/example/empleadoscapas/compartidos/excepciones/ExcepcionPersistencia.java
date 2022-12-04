package com.example.empleadoscapas.compartidos.excepciones;

public class ExcepcionPersistencia extends ExcepcionEmpleados {

    public ExcepcionPersistencia() {

    }

    public ExcepcionPersistencia(String mensaje) {
        super(mensaje);
    }

    public ExcepcionPersistencia(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

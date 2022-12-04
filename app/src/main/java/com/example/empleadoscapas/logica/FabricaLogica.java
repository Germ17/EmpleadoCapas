package com.example.empleadoscapas.logica;

import android.content.Context;

public class FabricaLogica {

    public static IControladorMantenimientoSucursales getControladorMantenimientoSucursales(Context contexto) {
        return ControladorMantenimientoSucursales.getInstancia(contexto);
    }

    public static IControladorMantenimientoEmpleados getControladorMantenimientoEmpleados(Context contexto) {
        return ControladorMantenimientoEmpleados.getInstancia(contexto);
    }

}

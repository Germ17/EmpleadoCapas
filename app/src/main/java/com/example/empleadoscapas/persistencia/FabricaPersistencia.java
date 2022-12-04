package com.example.empleadoscapas.persistencia;

import android.content.Context;

public class FabricaPersistencia {

    public static IPersistenciaSucursal getPersistenciaSucursal(Context contexto) {
        return PersistenciaSucursal.getInstancia(contexto);
    }

    public static IPersistenciaEmpleado getPersistenciaEmpleado(Context contexto) {
        return PersistenciaEmpleado.getInstancia(contexto);
    }

}

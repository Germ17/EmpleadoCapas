package com.example.empleadoscapas.persistencia;

import android.provider.BaseColumns;

final class BDContract {

    public static final String NOMBRE_BASE_DATOS = "EmpleadosCapas.sqlite3";
    public static final int VERSION_BASE_DATOS = 1;

    public static final String SUCURSALES = "Sucursales";
    public static final String EMPLEADOS = "Empleados";

    public static final String VISTA_SUCURSALES = "VistaSucursales";
    public static final String VISTA_EMPLEADOS = "VistaEmpleados";


    private BDContract() {

    }


    public static abstract class Sucursales implements BaseColumns {

        public static final String NOMBRE = "Nombre";
        public static final String DIRECCION = "Direccion";
        public static final String SUPERFICIE = "Superficie";
        public static final String ESTACIONAMIENTO = "Estacionamiento";

        public static final String[] COLUMNAS = { _ID, NOMBRE, DIRECCION, SUPERFICIE, ESTACIONAMIENTO };

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ").append(SUCURSALES).append(" (")
                .append(_ID).append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ")
                .append(NOMBRE).append(" TEXT NOT NULL UNIQUE, ")
                .append(DIRECCION).append(" TEXT, ")
                .append(SUPERFICIE).append(" INTEGER, ")
                .append(ESTACIONAMIENTO).append(" INTEGER NOT NULL);")
                .toString();

    }

    public static abstract class Empleados implements BaseColumns {

        public static final String CEDULA = "Cedula";
        public static final String NOMBRE = "Nombre";
        public static final String FECHA_INGRESO = "FechaIngreso";
        public static final String SUELDO = "Sueldo";
        public static final String SUCURSAL = "Sucursal";

        public static final String[] COLUMNAS = { _ID, CEDULA, NOMBRE, FECHA_INGRESO, SUELDO, SUCURSAL };

        public static final String SQL_CREAR_TABLA = new StringBuilder("CREATE TABLE ").append(EMPLEADOS).append(" (")
                .append(_ID).append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ")
                .append(CEDULA).append(" INTEGER NOT NULL UNIQUE, ")
                .append(NOMBRE).append(" TEXT NOT NULL, ")
                .append(FECHA_INGRESO).append(" TEXT NOT NULL, ")
                .append(SUELDO).append(" REAL NOT NULL, ")
                .append(SUCURSAL).append(" INTEGER NOT NULL, ")
                .append("FOREIGN KEY (").append(SUCURSAL).append(") REFERENCES ").append(SUCURSALES).append("(").append(Sucursales._ID).append("));")
                .toString();

    }

    public static abstract class VistaSucursales {

        public static final String SUCURSALES_ID = "Sucursales_Id";
        public static final String SUCURSALES_NOMBRE = "Sucursales_Nombre";
        public static final String SUCURSALES_DIRECCION = "Sucursales_Direccion";
        public static final String SUCURSALES_SUPERFICIE = "Sucursales_Superficie";
        public static final String SUCURSALES_ESTACIONAMIENTO = "Sucursales_Estacionamiento";

        public static final String[] COLUMNAS = { SUCURSALES_ID, SUCURSALES_NOMBRE, SUCURSALES_DIRECCION, SUCURSALES_SUPERFICIE, SUCURSALES_ESTACIONAMIENTO };

        public static final String SQL_CREAR_VISTA = new StringBuilder("CREATE VIEW ").append(VISTA_SUCURSALES).append(" AS SELECT ")
                .append(Sucursales._ID).append(" AS ").append(SUCURSALES_ID).append(", ")
                .append(Sucursales.NOMBRE).append(" AS ").append(SUCURSALES_NOMBRE).append(", ")
                .append(Sucursales.DIRECCION).append(" AS ").append(SUCURSALES_DIRECCION).append(", ")
                .append(Sucursales.SUPERFICIE).append(" AS ").append(SUCURSALES_SUPERFICIE).append(", ")
                .append(Sucursales.ESTACIONAMIENTO).append(" AS ").append(SUCURSALES_ESTACIONAMIENTO)
                .append(" FROM ").append(SUCURSALES).append(";")
                .toString();

    }

    public static abstract class VistaEmpleados {

        public static final String EMPLEADOS_ID = "Empleados_Id";
        public static final String EMPLEADOS_CEDULA = "Empleados_Cedula";
        public static final String EMPLEADOS_NOMBRE = "Empleados_Nombre";
        public static final String EMPLEADOS_FECHA_INGRESO = "Empleados_FechaIngreso";
        public static final String EMPLEADOS_SUELDO = "Empleados_Sueldo";

        public static final String[] COLUMNAS = { EMPLEADOS_ID, EMPLEADOS_CEDULA, EMPLEADOS_NOMBRE, EMPLEADOS_FECHA_INGRESO, EMPLEADOS_SUELDO, VistaSucursales.SUCURSALES_ID, VistaSucursales.SUCURSALES_NOMBRE, VistaSucursales.SUCURSALES_DIRECCION, VistaSucursales.SUCURSALES_SUPERFICIE, VistaSucursales.SUCURSALES_ESTACIONAMIENTO };

        public static final String SQL_CREAR_VISTA = new StringBuilder("CREATE VIEW ").append(VISTA_EMPLEADOS).append(" AS SELECT ")
                .append(Empleados._ID).append(" AS ").append(EMPLEADOS_ID).append(", ")
                .append(Empleados.CEDULA).append(" AS ").append(EMPLEADOS_CEDULA).append(", ")
                .append(Empleados.NOMBRE).append(" AS ").append(EMPLEADOS_NOMBRE).append(", ")
                .append(Empleados.FECHA_INGRESO).append(" AS ").append(EMPLEADOS_FECHA_INGRESO).append(", ")
                .append(Empleados.SUELDO).append(" AS ").append(EMPLEADOS_SUELDO).append(", ")
                .append(VISTA_SUCURSALES).append(".* ")
                .append(" FROM ").append(EMPLEADOS).append(" INNER JOIN ").append(VISTA_SUCURSALES)
                .append(" ON ").append(EMPLEADOS).append(".").append(Empleados.SUCURSAL).append(" = ").append(VISTA_SUCURSALES).append(".").append(VistaSucursales.SUCURSALES_ID).append(";")
                .toString();

    }

}

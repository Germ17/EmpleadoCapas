package com.example.empleadoscapas.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class BDHelper extends SQLiteOpenHelper {

    private Context contexto;


    public BDHelper(Context contexto) {
        super(contexto, BDContract.NOMBRE_BASE_DATOS, null, BDContract.VERSION_BASE_DATOS);

        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BDContract.Sucursales.SQL_CREAR_TABLA);
        db.execSQL(BDContract.Empleados.SQL_CREAR_TABLA);

        db.execSQL(BDContract.VistaSucursales.SQL_CREAR_VISTA);
        db.execSQL(BDContract.VistaEmpleados.SQL_CREAR_VISTA);

        db.execSQL(new StringBuilder("INSERT INTO ").append(BDContract.SUCURSALES).append(" VALUES (NULL, 'Centro', '18 de Julio 1234', 1000, 1);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BDContract.SUCURSALES).append(" VALUES (NULL, 'Ciudad Vieja', 'Rincón 4321', NULL, 0);").toString());

        db.execSQL(new StringBuilder("INSERT INTO ").append(BDContract.EMPLEADOS).append(" VALUES (NULL, 1, 'José Pérez', '1991-07-10', 1000, 1);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BDContract.EMPLEADOS).append(" VALUES (NULL, 2, 'Ana Fernández', '2002-03-28', 2000, 1);").toString());
        db.execSQL(new StringBuilder("INSERT INTO ").append(BDContract.EMPLEADOS).append(" VALUES (NULL, 3, 'Pedro Rodríguez', '2014-12-05', 2500, 2);").toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

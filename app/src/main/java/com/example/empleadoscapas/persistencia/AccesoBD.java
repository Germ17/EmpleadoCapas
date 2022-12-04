package com.example.empleadoscapas.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class AccesoBD {

    private static AccesoBD instancia = null;


    public static AccesoBD getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new AccesoBD(contexto);
        }

        return instancia;
    }


    private Context contexto;
    private BDHelper bdHelper;
    private SQLiteDatabase baseDatos;


    public SQLiteDatabase getBaseDatos() {
        return baseDatos;
    }


    private AccesoBD(Context contexto) {
        this.contexto = contexto;

        bdHelper = new BDHelper(contexto);
        baseDatos = bdHelper.getWritableDatabase();
    }


    @Override
    protected void finalize() throws Throwable {
        baseDatos.close();
        bdHelper.close();

        super.finalize();
    }

}

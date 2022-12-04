package com.example.empleadoscapas.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionPersistencia;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.util.ArrayList;
import java.util.List;

class PersistenciaSucursal implements IPersistenciaSucursal {

    private static PersistenciaSucursal instancia;


    public static PersistenciaSucursal getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaSucursal(contexto);
        }

        return instancia;
    }


    private Context contexto;


    private PersistenciaSucursal(Context contexto) {
        this.contexto = contexto.getApplicationContext();
    }

    @Override
    public List<DTSucursal> listar()
            throws ExcepcionEmpleados {
        Cursor datos = null;

        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            datos = baseDatos.query(BDContract.VISTA_SUCURSALES, BDContract.VistaSucursales.COLUMNAS, null, null, null, null, BDContract.VistaSucursales.SUCURSALES_NOMBRE);

            List<DTSucursal> sucursales = new ArrayList<DTSucursal>();

            while (datos.moveToNext()) {
                DTSucursal sucursal = instanciarSucursal(datos);
                sucursales.add(sucursal);
            }

            return sucursales;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar las sucursales.", ex);
        } finally {
            if (datos != null) datos.close();
        }
    }

    @Override
    public DTSucursal obtener(long id)
            throws ExcepcionEmpleados {
        Cursor datos = null;

        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            datos = baseDatos.query(BDContract.VISTA_SUCURSALES, BDContract.VistaSucursales.COLUMNAS, BDContract.VistaSucursales.SUCURSALES_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);

            DTSucursal sucursal = null;

            if (datos.moveToNext()) {
                sucursal = instanciarSucursal(datos);
            }

            return sucursal;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener la sucursal.", ex);
        } finally {
            if (datos != null) datos.close();
        }
    }

    @Override
    public void agregar(DTSucursal sucursal)
            throws ExcepcionEmpleados {
        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            ContentValues valores = new ContentValues();
            valores.put(BDContract.Sucursales.NOMBRE, sucursal.getNombre());
            valores.put(BDContract.Sucursales.DIRECCION, sucursal.getDireccion());
            valores.put(BDContract.Sucursales.SUPERFICIE, sucursal.getSuperficie());
            valores.put(BDContract.Sucursales.ESTACIONAMIENTO, sucursal.getEstacionamiento());

            long id = baseDatos.insertOrThrow(BDContract.SUCURSALES, null, valores);

            sucursal.setId(id);
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar la sucursal.", ex);
        }
    }

    @Override
    public void modificar(DTSucursal sucursal)
            throws ExcepcionEmpleados {
        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            ContentValues valores = new ContentValues();
            valores.put(BDContract.Sucursales.NOMBRE, sucursal.getNombre());
            valores.put(BDContract.Sucursales.DIRECCION, sucursal.getDireccion());
            valores.put(BDContract.Sucursales.SUPERFICIE, sucursal.getSuperficie());
            valores.put(BDContract.Sucursales.ESTACIONAMIENTO, sucursal.getEstacionamiento());

            int filasAfectadas = baseDatos.update(BDContract.SUCURSALES, valores, BDContract.Sucursales._ID + " = ?", new String[] { String.valueOf(sucursal.getId()) });

            if (filasAfectadas < 1) {
                throw new ExcepcionPersistencia("La sucursal no existe.");
            }
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo modificar la sucursal.", ex);
        }
    }

    @Override
    public void eliminar(long id)
            throws ExcepcionEmpleados {
        Cursor datos = null;

        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            datos = baseDatos.query(BDContract.VISTA_EMPLEADOS, BDContract.VistaEmpleados.COLUMNAS, BDContract.VistaSucursales.SUCURSALES_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);

            if (datos.moveToNext()) {
                throw new ExcepcionPersistencia("La sucursal tiene empleados.");
            }

            int filasAfectadas = baseDatos.delete(BDContract.SUCURSALES, BDContract.Sucursales._ID + " = ?", new String[] { String.valueOf(id) });

            if (filasAfectadas < 1) {
                throw new ExcepcionPersistencia("La sucursal no existe.");
            }
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo eliminar la sucursal.", ex);
        } finally {
            if (datos != null) datos.close();
        }
    }

    public DTSucursal instanciarSucursal(Cursor datos) {
        int columnaSucursalesId = datos.getColumnIndex(BDContract.VistaSucursales.SUCURSALES_ID);
        int columnaSucursalesNombre = datos.getColumnIndex(BDContract.VistaSucursales.SUCURSALES_NOMBRE);
        int columnaSucursalesDireccion = datos.getColumnIndex(BDContract.VistaSucursales.SUCURSALES_DIRECCION);
        int columnaSucursalesSuperficie = datos.getColumnIndex(BDContract.VistaSucursales.SUCURSALES_SUPERFICIE);
        int columnaSucursalesEstacionamiento = datos.getColumnIndex(BDContract.VistaSucursales.SUCURSALES_ESTACIONAMIENTO);

        Integer superficie = datos.isNull(columnaSucursalesSuperficie) ? null : datos.getInt(columnaSucursalesSuperficie);
        boolean estacionamiento = datos.getInt(columnaSucursalesEstacionamiento) == 1;

        DTSucursal sucursal = new DTSucursal(datos.getLong(columnaSucursalesId), datos.getString(columnaSucursalesNombre), datos.getString(columnaSucursalesDireccion), superficie, estacionamiento);

        return sucursal;
    }

}

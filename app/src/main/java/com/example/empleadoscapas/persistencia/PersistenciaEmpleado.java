package com.example.empleadoscapas.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionPersistencia;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PersistenciaEmpleado implements IPersistenciaEmpleado {

    private static PersistenciaEmpleado instancia;


    public static PersistenciaEmpleado getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new PersistenciaEmpleado(contexto);
        }

        return instancia;
    }


    private Context contexto;
    private SimpleDateFormat formateadorFechas;


    private PersistenciaEmpleado(Context contexto) {
        this.contexto = contexto.getApplicationContext();

        formateadorFechas = new SimpleDateFormat("yyyy-MM-dd");
    }


    @Override
    public List<DTEmpleado> listar()
            throws ExcepcionEmpleados {
        Cursor datos = null;

        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            datos = baseDatos.query(BDContract.VISTA_EMPLEADOS, BDContract.VistaEmpleados.COLUMNAS, null, null, null, null, BDContract.VistaEmpleados.EMPLEADOS_CEDULA);

            List<DTEmpleado> empleados = new ArrayList<DTEmpleado>();

            while (datos.moveToNext()) {
                DTEmpleado empleado = instanciarEmpleado(datos);
                empleados.add(empleado);
            }

            return empleados;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar los empleados.", ex);
        } finally {
            if (datos != null) datos.close();
        }
    }

    @Override
    public DTEmpleado obtener(int cedula) throws ExcepcionEmpleados {
        Cursor datos = null;

        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            datos = baseDatos.query(BDContract.VISTA_EMPLEADOS, BDContract.VistaEmpleados.COLUMNAS, BDContract.VistaEmpleados.EMPLEADOS_CEDULA + " = ?", new String[] { String.valueOf(cedula) }, null, null, null);

            DTEmpleado empleado = null;

            if (datos.moveToNext()) {
                empleado = instanciarEmpleado(datos);
            }

            return empleado;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el empleado.", ex);
        } finally {
            if (datos != null) datos.close();
        }
    }

    @Override
    public void agregar(DTEmpleado empleado)
            throws ExcepcionEmpleados {
        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            if (obtener(empleado.getCedula()) != null) {
                throw new ExcepcionPersistencia("El empleado ya existe.");
            }

            if (PersistenciaSucursal.getInstancia(contexto).obtener(empleado.getSucursal().getId()) == null) {
                throw new ExcepcionPersistencia("La sucursal no existe.");
            }

            ContentValues valores = new ContentValues();
            valores.put(BDContract.Empleados.CEDULA, empleado.getCedula());
            valores.put(BDContract.Empleados.NOMBRE, empleado.getNombre());
            valores.put(BDContract.Empleados.FECHA_INGRESO, formateadorFechas.format(empleado.getFechaIngreso()));
            valores.put(BDContract.Empleados.SUELDO, empleado.getSueldo());
            valores.put(BDContract.Empleados.SUCURSAL, empleado.getSucursal().getId());

            long id = baseDatos.insertOrThrow(BDContract.EMPLEADOS, null, valores);

            empleado.setId(id);
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el empleado.", ex);
        }
    }

    @Override
    public void modificar(DTEmpleado empleado)
            throws ExcepcionEmpleados {
        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            ContentValues valores = new ContentValues();
            valores.put(BDContract.Empleados.CEDULA, empleado.getCedula());
            valores.put(BDContract.Empleados.NOMBRE, empleado.getNombre());
            valores.put(BDContract.Empleados.FECHA_INGRESO, formateadorFechas.format(empleado.getFechaIngreso()));
            valores.put(BDContract.Empleados.SUELDO, empleado.getSueldo());
            valores.put(BDContract.Empleados.SUCURSAL, empleado.getSucursal().getId());

            int filasAfectadas = baseDatos.update(BDContract.EMPLEADOS, valores, BDContract.Empleados.CEDULA + " = ?", new String[] { String.valueOf(empleado.getCedula()) });

            if (filasAfectadas < 1) {
                throw new ExcepcionPersistencia("El empleado no existe.");
            }
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo modificar el empleado.", ex);
        }
    }

    @Override
    public void eliminar(int cedula)
            throws ExcepcionEmpleados {
        try {
            SQLiteDatabase baseDatos = AccesoBD.getInstancia(contexto).getBaseDatos();

            int filasAfectadas = baseDatos.delete(BDContract.EMPLEADOS, BDContract.Empleados.CEDULA + " = ?", new String[] { String.valueOf(cedula) });

            if (filasAfectadas < 1) {
                throw new ExcepcionPersistencia("El empleado no existe.");
            }
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo eliminar el empleado.", ex);
        }
    }

    public DTEmpleado instanciarEmpleado(Cursor datos)
            throws ParseException {
        int columnaEmpleadosId = datos.getColumnIndex(BDContract.VistaEmpleados.EMPLEADOS_ID);
        int columnaEmpleadosCedula = datos.getColumnIndex(BDContract.VistaEmpleados.EMPLEADOS_CEDULA);
        int columnaEmpleadosNombre = datos.getColumnIndex(BDContract.VistaEmpleados.EMPLEADOS_NOMBRE);
        int columnaEmpleadosFechaIngreso = datos.getColumnIndex(BDContract.VistaEmpleados.EMPLEADOS_FECHA_INGRESO);
        int columnaEmpleadosSueldo = datos.getColumnIndex(BDContract.VistaEmpleados.EMPLEADOS_SUELDO);

        Date fechaIngreso = formateadorFechas.parse(datos.getString(columnaEmpleadosFechaIngreso));

        DTSucursal sucursal = PersistenciaSucursal.getInstancia(contexto).instanciarSucursal(datos);

        DTEmpleado empleado = new DTEmpleado(datos.getLong(columnaEmpleadosId), datos.getInt(columnaEmpleadosCedula), datos.getString(columnaEmpleadosNombre), fechaIngreso, datos.getDouble(columnaEmpleadosSueldo), sucursal);

        return empleado;
    }

}

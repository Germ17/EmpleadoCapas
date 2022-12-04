package com.example.empleadoscapas.presentacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empleadoscapas.compartidos.Constantes;
import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.R;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;
import com.example.empleadoscapas.logica.FabricaLogica;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditarEmpleadoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected TextView tvTitulo;
    protected EditText etCedula;
    protected EditText etNombre;
    protected EditText etFechaIngreso;
    protected EditText etSueldo;
    protected Spinner spSucursal;

    protected DTEmpleado empleadoSeleccionado;

    protected SimpleDateFormat formateadorFechas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_empleado);

        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        etCedula = (EditText)findViewById(R.id.etCedula);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etFechaIngreso = (EditText)findViewById(R.id.etFechaIngreso);
        etSueldo = (EditText)findViewById(R.id.etSueldo);
        spSucursal = (Spinner)findViewById(R.id.spSucursal);

        List<DTSucursal> sucursales = null;
        ArrayAdapter<DTSucursal> adaptadorSucursales = null;

        try {
            sucursales = FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).listarSucursales();

            adaptadorSucursales = new ArrayAdapter<DTSucursal>(this, android.R.layout.simple_list_item_1, sucursales);
            adaptadorSucursales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSucursal.setAdapter(adaptadorSucursales);
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

            finish();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo listar las sucursales.", Toast.LENGTH_SHORT).show();

            finish();
        }

        empleadoSeleccionado = (DTEmpleado)getIntent().getSerializableExtra(Constantes.EXTRA_EMPLEADO);

        formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

        if (empleadoSeleccionado != null) {
            tvTitulo.setText("Modificar Empleado");
            etCedula.setText(String.valueOf(empleadoSeleccionado.getCedula()));
            etNombre.setText(empleadoSeleccionado.getNombre());
            etFechaIngreso.setText(formateadorFechas.format(empleadoSeleccionado.getFechaIngreso()));
            etSueldo.setText(new DecimalFormat("#.00").format(empleadoSeleccionado.getSueldo()));
            spSucursal.setSelection(adaptadorSucursales.getPosition(empleadoSeleccionado.getSucursal()));

            etCedula.setEnabled(false);

            etNombre.requestFocus();
        }

        etFechaIngreso.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return etFechaIngresoOnTouch(v, event);
            }

        });

        etFechaIngreso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etFechaIngresoAfterTextChanged(s);
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_empleado, menu);

        if(menu instanceof MenuBuilder){
            ((MenuBuilder)menu).setOptionalIconsVisible(true);
        }

        if (empleadoSeleccionado == null) {
            menu.removeItem(R.id.mniEliminar);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniInicio:
                Intent intencionInicio = new Intent(getApplicationContext(), MainActivity.class);
                intencionInicio.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intencionInicio);

                finish();

                return true;
            case R.id.mniEliminar:
                try {
                    FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).eliminarEmpleado(empleadoSeleccionado.getCedula());

                    Toast.makeText(this, "Empleado eliminado con éxito.", Toast.LENGTH_SHORT).show();

                    finish();
                } catch (ExcepcionEmpleados ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "No se pudo eliminar el empleado.", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etFechaIngreso.setText(String.format("%1$02d", dayOfMonth) + "/" + String.format("%1$02d", (month + 1)) + "/" + year);

        if (etFechaIngreso.getError() != null) {
            etFechaIngreso.setError(null);
            etFechaIngreso.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    public void btnGuardarOnClick(View view) {
        boolean errores = false;

        int cedula = 0;

        try {
            cedula = Integer.parseInt(etCedula.getText().toString());
        } catch (Exception ex) {
            etCedula.setError("La cédula no es válida.");

            errores = true;
        }

        String nombre = etNombre.getText().toString();

        Date fechaIngreso = null;

        try {
            fechaIngreso = formateadorFechas.parse(etFechaIngreso.getText().toString());
        } catch (Exception ex) {
            etFechaIngreso.setError("La fecha de ingreso no es válida.");

            errores = true;
        }

        double sueldo = 0;

        try {
            sueldo = Double.parseDouble(etSueldo.getText().toString());
        } catch (Exception ex) {
            etSueldo.setError("El sueldo no es válido.");

            errores = true;
        }

        DTSucursal sucursal = (DTSucursal)spSucursal.getSelectedItem();

        if (!errores) {
            if (empleadoSeleccionado == null) {
                agregarEmpleado(cedula, nombre, fechaIngreso, sueldo, sucursal);
            } else {
                modificarEmpleado(cedula, nombre, fechaIngreso, sueldo, sucursal);
            }
        }
    }

    public boolean etFechaIngresoOnTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (etFechaIngreso.getRight() - etFechaIngreso.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Calendar calendario = Calendar.getInstance();

                try {
                    int dia = Integer.parseInt(etFechaIngreso.getText().toString().substring(0, 2));
                    int mes = Integer.parseInt(etFechaIngreso.getText().toString().substring(3, 5)) - 1;
                    int anio = Integer.parseInt(etFechaIngreso.getText().toString().substring(6, 10));

                    calendario.set(Calendar.DAY_OF_MONTH, dia);
                    calendario.set(Calendar.MONTH, mes);
                    calendario.set(Calendar.YEAR, anio);
                } catch (Exception ex) {

                }

                SelectorFechaIngreso.newInstance(calendario).show(getSupportFragmentManager(), Constantes.TAG_SELECTOR_FECHA_INGRESO);

                return true;
            }
        }

        return false;
    }

    public void etFechaIngresoAfterTextChanged(Editable s) {
        if (etFechaIngreso.getError() != null) {
            etFechaIngreso.setError(null);
            etFechaIngreso.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_search, 0);
        }
    }

    protected void agregarEmpleado(int cedula, String nombre, Date fechaIngreso, double sueldo, DTSucursal sucursal) {
        try {
            DTEmpleado empleado = new DTEmpleado(cedula, nombre, fechaIngreso, sueldo, sucursal);

            FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).agregarEmpleado(empleado);

            Toast.makeText(this, "Empleado agregado con éxito.", Toast.LENGTH_SHORT).show();

            finish();
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo agregar el empleado.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void modificarEmpleado(int cedula, String nombre, Date fechaIngreso, double sueldo, DTSucursal sucursal) {
        try {
            DTEmpleado empleado = new DTEmpleado(cedula, nombre, fechaIngreso, sueldo, sucursal);

            FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).modificarEmpleado(empleado);

            Toast.makeText(this, "Empleado modificado con éxito.", Toast.LENGTH_SHORT).show();

            finish();
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo modificar el empleado.", Toast.LENGTH_SHORT).show();
        }
    }


    public static class SelectorFechaIngreso extends DialogFragment {

        public static SelectorFechaIngreso newInstance(Calendar calendario) {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable(Constantes.ARGUMENTO_CALENDARIO, calendario);

            SelectorFechaIngreso fragmento = new SelectorFechaIngreso();
            fragmento.setArguments(argumentos);

            return fragmento;
        }


        private Calendar calendario;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            calendario = (Calendar)getArguments().getSerializable(Constantes.ARGUMENTO_CALENDARIO);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), anio, mes, dia);
        }

    }

}
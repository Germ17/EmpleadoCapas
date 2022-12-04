package com.example.empleadoscapas.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empleadoscapas.R;
import com.example.empleadoscapas.compartidos.Constantes;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;
import com.example.empleadoscapas.logica.FabricaLogica;

public class EditarSucursalActivity extends AppCompatActivity {

    protected LinearLayout llCampos;
    protected TextView tvTitulo;
    protected TextView tvId;
    protected EditText etId;
    protected EditText etNombre;
    protected EditText etDireccion;
    protected EditText etSuperficie;
    protected CheckBox cbEstacionamiento;

    protected DTSucursal sucursalSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_sucursal);

        llCampos = (LinearLayout)findViewById(R.id.llCampos);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvId = (TextView)findViewById(R.id.tvId);
        etId = (EditText)findViewById(R.id.etId);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etDireccion = (EditText)findViewById(R.id.etDireccion);
        etSuperficie = (EditText)findViewById(R.id.etSuperficie);
        cbEstacionamiento = (CheckBox)findViewById(R.id.cbEstacionamiento);

        sucursalSeleccionada = (DTSucursal) getIntent().getSerializableExtra(Constantes.EXTRA_SUCURSAL);

        if (sucursalSeleccionada != null) {
            tvTitulo.setText("Modificar Sucursal");
            etId.setText(String.valueOf(sucursalSeleccionada.getId()));
            etNombre.setText(sucursalSeleccionada.getNombre());
            etDireccion.setText(sucursalSeleccionada.getDireccion());
            etSuperficie.setText(sucursalSeleccionada.getSuperficie() != null ? String.valueOf(sucursalSeleccionada.getSuperficie()) : "");
            cbEstacionamiento.setChecked(sucursalSeleccionada.getEstacionamiento());
        } else {
            llCampos.removeView(tvId);
            llCampos.removeView(etId);
        }

        etNombre.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_empleado, menu);

        if(menu instanceof MenuBuilder){
            ((MenuBuilder)menu).setOptionalIconsVisible(true);
        }

        if (sucursalSeleccionada == null) {
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
                    FabricaLogica.getControladorMantenimientoSucursales(getApplicationContext()).eliminarSucursal(sucursalSeleccionada.getId());

                    Toast.makeText(this, "Sucursal eliminada con éxito.", Toast.LENGTH_SHORT).show();

                    finish();
                } catch (ExcepcionEmpleados ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(this, "No se pudo eliminar la sucursal.", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    public void btnGuardarOnClick(View view) {
        boolean errores = false;

        String nombre = etNombre.getText().toString();

        String direccion = etDireccion.getText().toString();

        Integer superficie = null;

        if (!etSuperficie.getText().toString().trim().isEmpty()) {
            try {
                superficie = Integer.parseInt(etSuperficie.getText().toString());
            } catch (Exception ex) {
                etSuperficie.setError("La superficie no es válida.");

                errores = true;
            }
        }

        boolean estacionamiento = cbEstacionamiento.isChecked();

        if (!errores) {
            if (sucursalSeleccionada == null) {
                agregarSucursal(nombre, direccion, superficie, estacionamiento);
            } else {
                modificarSucursal(sucursalSeleccionada.getId(), nombre, direccion, superficie, estacionamiento);
            }
        }
    }

    protected void agregarSucursal(String nombre, String direccion, Integer superficie, boolean estacionamiento) {
        try {
            DTSucursal sucursal = new DTSucursal(nombre, direccion, superficie, estacionamiento);

            FabricaLogica.getControladorMantenimientoSucursales(getApplicationContext()).agregarSucursal(sucursal);

            Toast.makeText(this, "Sucursal agregada con éxito (Id: " + sucursal.getId() + ").", Toast.LENGTH_SHORT).show();

            finish();
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo agregar la sucursal.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void modificarSucursal(long id, String nombre, String direccion, Integer superficie, boolean estacionamiento) {
        try {
            DTSucursal sucursal = new DTSucursal(id, nombre, direccion, superficie, estacionamiento);

            FabricaLogica.getControladorMantenimientoSucursales(getApplicationContext()).modificarSucursal(sucursal);

            Toast.makeText(this, "Sucursal modificada con éxito.", Toast.LENGTH_SHORT).show();

            finish();
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo modificar la sucursal.", Toast.LENGTH_SHORT).show();
        }
    }

}
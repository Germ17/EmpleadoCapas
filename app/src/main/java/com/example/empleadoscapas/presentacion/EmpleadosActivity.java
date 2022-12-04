package com.example.empleadoscapas.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.empleadoscapas.R;
import com.example.empleadoscapas.compartidos.Constantes;
import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;
import com.example.empleadoscapas.logica.FabricaLogica;

import java.util.List;

public class EmpleadosActivity extends AppCompatActivity {

    protected ListView lvEmpleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empleados);

        setTitle(getString(R.string.app_name) + " - Empleados");

        lvEmpleados = (ListView)findViewById(R.id.lvEmpleados);

        lvEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvEmpleadosOnItemClick(parent, view, position, id);
            }

        });

        registerForContextMenu(lvEmpleados);
    }

    protected void mostrarEmpleados() {
        try {
            List<DTEmpleado> empleados = FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).listarEmpleados();

            EmpleadosAdapter adaptadorEmpleados = new EmpleadosAdapter(this, empleados);
            lvEmpleados.setAdapter(adaptadorEmpleados);
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo listar los empleados.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mostrarEmpleados();
    }

    public void fabAgregarOnClick(View view) {
        Intent intencionEditarEmpleado = new Intent(getApplicationContext(), EditarEmpleadoActivity.class);

        startActivity(intencionEditarEmpleado);
    }

    protected void lvEmpleadosOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTEmpleado empleado = (DTEmpleado)parent.getItemAtPosition(position);

        Intent intencionEditarEmpleado = new Intent(getApplicationContext(), EditarEmpleadoActivity.class);
        intencionEditarEmpleado.putExtra(Constantes.EXTRA_EMPLEADO, empleado);

        startActivity(intencionEditarEmpleado);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.lvEmpleados:
                getMenuInflater().inflate(R.menu.menu_contextual_empleado, menu);

                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (((View)info.targetView.getParent()).getId()) {
            case R.id.lvEmpleados:
                DTEmpleado empleado = (DTEmpleado)lvEmpleados.getItemAtPosition(info.position);

                switch (item.getItemId()) {
                    case R.id.mniModificar:
                        Intent intencionEditarEmpleado = new Intent(getApplicationContext(), EditarEmpleadoActivity.class);
                        intencionEditarEmpleado.putExtra(Constantes.EXTRA_EMPLEADO, empleado);

                        startActivity(intencionEditarEmpleado);

                        return true;
                    case R.id.mniEliminar:
                        try {
                            FabricaLogica.getControladorMantenimientoEmpleados(getApplicationContext()).eliminarEmpleado(empleado.getCedula());

                            Toast.makeText(this, "Empleado eliminado con éxito.", Toast.LENGTH_SHORT).show();
                        } catch (ExcepcionEmpleados ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(this, "No se pudo eliminar el empleado.", Toast.LENGTH_SHORT).show();
                        }

                        mostrarEmpleados();

                        return true;
                    default:

                        return super.onContextItemSelected(item);
                }
            default:

                return super.onContextItemSelected(item);
        }
    }

}
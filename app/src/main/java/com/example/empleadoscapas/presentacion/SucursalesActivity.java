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
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;
import com.example.empleadoscapas.compartidos.excepciones.ExcepcionEmpleados;
import com.example.empleadoscapas.logica.FabricaLogica;

import java.util.List;

public class SucursalesActivity extends AppCompatActivity {

    protected ListView lvSucursales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sucursales);

        setTitle(getString(R.string.app_name) + " - Sucursales");

        lvSucursales = (ListView)findViewById(R.id.lvSucursales);

        lvSucursales.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvSucursalesOnItemClick(parent, view, position, id);
            }

        });

        registerForContextMenu(lvSucursales);
    }

    protected void mostrarSucursales() {
        try {
            List<DTSucursal> sucursales = FabricaLogica.getControladorMantenimientoSucursales(getApplicationContext()).listarSucursales();

            SucursalesAdapter adaptadorSucursales = new SucursalesAdapter(this, sucursales);
            lvSucursales.setAdapter(adaptadorSucursales);
        } catch (ExcepcionEmpleados ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "No se pudo listar las sucursales.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mostrarSucursales();
    }

    public void fabAgregarOnClick(View view) {
        Intent intencionEditarSucursal = new Intent(getApplicationContext(), EditarSucursalActivity.class);

        startActivity(intencionEditarSucursal);
    }

    public void lvSucursalesOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        DTSucursal sucursal = (DTSucursal)parent.getItemAtPosition(position);

        Intent intencionEditarSucursal = new Intent(getApplicationContext(), EditarSucursalActivity.class);
        intencionEditarSucursal.putExtra(Constantes.EXTRA_SUCURSAL, sucursal);

        startActivity(intencionEditarSucursal);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.lvSucursales:
                getMenuInflater().inflate(R.menu.menu_contextual_sucursal, menu);

                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (((View)info.targetView.getParent()).getId()) {
            case R.id.lvSucursales:
                DTSucursal sucursal = (DTSucursal)lvSucursales.getItemAtPosition(info.position);

                switch (item.getItemId()) {
                    case R.id.mniModificar:
                        Intent intencionEditarSucursal = new Intent(getApplicationContext(), EditarSucursalActivity.class);
                        intencionEditarSucursal.putExtra(Constantes.EXTRA_SUCURSAL, sucursal);

                        startActivity(intencionEditarSucursal);

                        return true;
                    case R.id.mniEliminar:
                        try {
                            FabricaLogica.getControladorMantenimientoSucursales(getApplicationContext()).eliminarSucursal(sucursal.getId());

                            Toast.makeText(this, "Sucursal eliminada con éxito.", Toast.LENGTH_SHORT).show();
                        } catch (ExcepcionEmpleados ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(this, "No se pudo eliminar la sucursal.", Toast.LENGTH_SHORT).show();
                        }

                        mostrarSucursales();

                        return true;
                    default:

                        return super.onContextItemSelected(item);
                }
            default:

                return super.onContextItemSelected(item);
        }
    }

}
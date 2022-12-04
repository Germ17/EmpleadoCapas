package com.example.empleadoscapas.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empleadoscapas.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void btnEmpleadosOnClick(View view) {
        Intent intencionEmpleados = new Intent(getApplicationContext(), EmpleadosActivity.class);

        startActivity(intencionEmpleados);
    }

    public void btnSucursalesOnClick(View view) {
        Intent intencionSucursales = new Intent(getApplicationContext(), SucursalesActivity.class);

        startActivity(intencionSucursales);
    }

}
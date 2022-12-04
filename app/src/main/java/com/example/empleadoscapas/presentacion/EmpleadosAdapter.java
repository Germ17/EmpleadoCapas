package com.example.empleadoscapas.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.empleadoscapas.R;

import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmpleadosAdapter extends BaseAdapter {

    private Context contexto;
    private List<DTEmpleado> empleados;


    public EmpleadosAdapter(Context contexto, List<DTEmpleado> empleados) {
        this.contexto = contexto;
        this.empleados = empleados;
    }


    @Override
    public int getCount() {
        return empleados.size();
    }

    @Override
    public DTEmpleado getItem(int position) {
        return empleados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        EmpleadoViewHolder empleadoViewHolder;

        if (vista == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            vista = inflador.inflate(R.layout.listitem_empleado, null);

            empleadoViewHolder = new EmpleadoViewHolder(vista);
            vista.setTag(empleadoViewHolder);
        } else {
            empleadoViewHolder = (EmpleadoViewHolder)vista.getTag();
        }

        empleadoViewHolder.enlazarEmpleado(empleados.get(position));

        return vista;
    }


    protected class EmpleadoViewHolder {

        private TextView tvCedula;
        private TextView tvNombre;
        private TextView tvFechaIngreso;
        private TextView tvSueldo;


        public EmpleadoViewHolder(View vista) {
            tvCedula = (TextView)vista.findViewById(R.id.tvCedula);
            tvNombre = (TextView)vista.findViewById(R.id.tvNombre);
            tvFechaIngreso = (TextView)vista.findViewById(R.id.tvFechaIngreso);
            tvSueldo = (TextView)vista.findViewById(R.id.tvSueldo);
        }


        public void enlazarEmpleado(DTEmpleado empleado) {
            SimpleDateFormat formateadorFechas = new SimpleDateFormat("dd/MM/yyyy");

            tvCedula.setText(String.valueOf(empleado.getCedula()));
            tvNombre.setText(empleado.getNombre());
            tvFechaIngreso.setText(formateadorFechas.format(empleado.getFechaIngreso()));
            tvSueldo.setText("$ " + new DecimalFormat("#.00").format(empleado.getSueldo()));
        }

    }

}

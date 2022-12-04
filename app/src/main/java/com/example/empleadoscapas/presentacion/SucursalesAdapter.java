package com.example.empleadoscapas.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.empleadoscapas.R;
import com.example.empleadoscapas.compartidos.datatypes.DTEmpleado;
import com.example.empleadoscapas.compartidos.datatypes.DTSucursal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SucursalesAdapter extends BaseAdapter {

    private Context contexto;
    private List<DTSucursal> sucursales;


    public SucursalesAdapter(Context contexto, List<DTSucursal> sucursales) {
        this.contexto = contexto;
        this.sucursales = sucursales;
    }


    @Override
    public int getCount() {
        return sucursales.size();
    }

    @Override
    public DTSucursal getItem(int position) {
        return sucursales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;
        SucursalesAdapter.SucursalViewHolder sucursalViewHolder;

        if (vista == null) {
            LayoutInflater inflador = LayoutInflater.from(contexto);
            vista = inflador.inflate(R.layout.listitem_sucursal, null);

            sucursalViewHolder = new SucursalesAdapter.SucursalViewHolder(vista);
            vista.setTag(sucursalViewHolder);
        } else {
            sucursalViewHolder = (SucursalesAdapter.SucursalViewHolder)vista.getTag();
        }

        sucursalViewHolder.enlazarSucursal(sucursales.get(position));

        return vista;
    }


    protected class SucursalViewHolder {

        private TextView tvId;
        private TextView tvNombre;
        private TextView tvDireccion;


        public SucursalViewHolder(View vista) {
            tvId = (TextView)vista.findViewById(R.id.tvId);
            tvNombre = (TextView)vista.findViewById(R.id.tvNombre);
            tvDireccion = (TextView)vista.findViewById(R.id.tvDireccion);
        }


        public void enlazarSucursal(DTSucursal sucursal) {
            tvId.setText(String.valueOf(sucursal.getId()));
            tvNombre.setText(sucursal.getNombre());
            tvDireccion.setText(sucursal.getDireccion());
        }
    }

}

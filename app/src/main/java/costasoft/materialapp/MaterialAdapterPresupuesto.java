package costasoft.materialapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Benjamin Costa on 30/10/2016.
 */
public class MaterialAdapterPresupuesto extends RecyclerView.Adapter<MaterialAdapterPresupuesto.MaterialPresupuestoViewHolder> implements View.OnClickListener, View.OnLongClickListener {
        private ArrayList<MaterialPresupuesto> items;
        private Context ctx;
        private View.OnClickListener listener;
        private View.OnLongClickListener longListener;

        public static class MaterialPresupuestoViewHolder extends RecyclerView.ViewHolder{
            public TextView marca;
            public TextView descripcion;
            public TextView precio;
            public TextView cantidad;
            private ArrayList<MaterialPresupuesto> productos = new ArrayList<MaterialPresupuesto>();

            public MaterialPresupuestoViewHolder(View v, Context ctx, ArrayList<MaterialPresupuesto> prods){
                super(v);
                this.productos = prods;
                marca = (TextView)v.findViewById(R.id.tv_marca);
                descripcion = (TextView)v.findViewById(R.id.tv_descripcion);
                precio = (TextView)v.findViewById(R.id.tv_precio);
                cantidad = (TextView)v.findViewById(R.id.tv_cantidad);
            }

        }

        public MaterialAdapterPresupuesto(ArrayList<MaterialPresupuesto> items,Context ctx){
            this.items = items;
            this.ctx = ctx;
        }

        @Override
        public MaterialAdapterPresupuesto.MaterialPresupuestoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_tarjeta_presupuesto, parent, false);
            v.setOnClickListener(this);
            v.setLongClickable(true);
            v.setOnLongClickListener(this);
            return new MaterialPresupuestoViewHolder(v,ctx,items);
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener = listener;
        }



        @Override
        public void onClick(View v) {
            if(listener!=null)
                listener.onClick(v);
        }

        public void setOnLongClickListener(View.OnLongClickListener listener){
            this.longListener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            if(longListener!=null)
                longListener.onLongClick(v);
            return true;
        }


        public MaterialPresupuesto obtenerItem(int position){
            return items.get(position);
        }

        @Override
        public void onBindViewHolder(MaterialAdapterPresupuesto.MaterialPresupuestoViewHolder holder, int position) {
            holder.marca.setText(items.get(position).getMarca());
            holder.descripcion.setText(items.get(position).getDescripcion());
            holder.precio.setText(String.valueOf(items.get(position).getPrecio()));
            holder.cantidad.setText(String.valueOf(items.get(position).getCantidad()));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }


    }


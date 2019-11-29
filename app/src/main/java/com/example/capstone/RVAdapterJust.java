package com.example.capstone;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Model.Justificacion;

import java.util.List;

public class RVAdapterJust extends RecyclerView.Adapter<RVAdapterJust.JustificacionViewHolder> {

    List<Justificacion> justificacionList;
    private  View.OnClickListener listener;
    public  RVAdapterJust(List<Justificacion> justificacionList){
        this.justificacionList = justificacionList;
    }

    @NonNull
    @Override
    public JustificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_justificacion,parent,false);
        view.setOnClickListener(listener);
        JustificacionViewHolder jvh = new JustificacionViewHolder(view);
        return jvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JustificacionViewHolder holder, int position) {
        holder.tvFecha.setText(justificacionList.get(position).getFecha());
        holder.tvNombres.setText(justificacionList.get(position).getNombres());
        System.out.println("pinga"+justificacionList.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return justificacionList.size();
    }
    public  void setOnClickListener1(View.OnClickListener listener){

        this.listener=listener;

    }

    public class JustificacionViewHolder extends RecyclerView.ViewHolder {

     TextView tvNombres,tvFecha;

     public JustificacionViewHolder(@NonNull View itemView) {
         super(itemView);
         tvNombres = itemView.findViewById(R.id.tvNombres);
         tvFecha = itemView.findViewById(R.id.tvFecha);

     }

 }

}

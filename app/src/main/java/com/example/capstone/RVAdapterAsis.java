package com.example.capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Model.Asistencia;

import java.util.List;

public class RVAdapterAsis extends RecyclerView.Adapter<RVAdapterAsis.AsistenciaViewHolder> {


    List<Asistencia> asistenciaList;
    public  RVAdapterAsis(List<Asistencia> asistenciaList){
           this.asistenciaList=asistenciaList;
    }
    @NonNull
    @Override
    public AsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistencia,parent,false);
        AsistenciaViewHolder avh = new AsistenciaViewHolder(view);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciaViewHolder holder, int position) {
        holder.tvFecha.setText(asistenciaList.get(position).getFecha());
        holder.chxAsis.setChecked(asistenciaList.get(position).isAsistio());
        holder.chxJust.setChecked(asistenciaList.get(position).isJustifico());
    }

    @Override
    public int getItemCount() {
        return asistenciaList.size();
    }


    public class AsistenciaViewHolder extends RecyclerView.ViewHolder{
        TextView tvFecha;
        CheckBox chxAsis,chxJust;
       public AsistenciaViewHolder(@NonNull View itemView) {
           super(itemView);
           chxAsis=itemView.findViewById(R.id.chxAsis);
           chxJust=itemView.findViewById(R.id.chxJust);
           tvFecha=itemView.findViewById(R.id.tvFecha);
           chxAsis.setClickable(false);
           chxJust.setClickable(false);
       }
   }
}

package com.example.capstone.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capstone.Instances.instances;
import com.example.capstone.Model.Asistencia;
import com.example.capstone.R;
import com.example.capstone.RVAdapterAsis;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AsistenciasFragment extends Fragment {

    int position;
    int numDias;
    RecyclerView rvAsis;
    TextView txtPorcenA,txtPorcenF;
    ProgressBar progressBar;
    static List<Asistencia> asistenciaList;
    private DatabaseReference mDatabase;
    public AsistenciasFragment(int position,int numDias) {
        // Required empty public constructor
        this.position=position;
        this.numDias=numDias;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_asistencias, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW,R.id.spnAsistencia);
        params.setMargins(0,350,0,0);
        view.setLayoutParams(params);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        rvAsis=view.findViewById(R.id.rvAsis);
        txtPorcenA = view.findViewById(R.id.txtPorcenA);
        txtPorcenF = view.findViewById(R.id.txtPorcenF);
        progressBar=view.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAsis.setLayoutManager(linearLayoutManager);
        conseguirAsistencias();
        return view;
    }
    private  void conseguirAsistencias(){


        asistenciaList= new ArrayList<>();
        for(int i=1;i<numDias+1;i++) {
            mDatabase.child("Asistencias").child("empleado"+ position).child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d("TAG1","asfsdgwegewgqweg");
                        Object fech = dataSnapshot.child("Fecha").getValue();
                        Object asist = dataSnapshot.child("Asistio").getValue();
                        Object justifi =dataSnapshot.child("Justificó").getValue();
                        if(fech==null || asist==null || justifi==null ){
                            return;
                        }
                        String fecha = fech.toString();
                        boolean asistio = Boolean.valueOf(asist.toString());
                        boolean justifico = Boolean.valueOf(justifi.toString());

                        asistenciaList.add(new Asistencia(fecha, asistio, justifico));
                        RVAdapterAsis adapater = new RVAdapterAsis(asistenciaList);

                        rvAsis.setAdapter(adapater);
                        conseguirPorcentajes();


                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    private void conseguirPorcentajes() {
        double contA=0,contF=0;
        System.out.println(asistenciaList.get(0).getFecha());
        for(int i=0;i<asistenciaList.size();i++){
            if(asistenciaList.get(i).isAsistio() || asistenciaList.get(i).isJustifico()){
                contA++;
            }
        }
        double pa=(contA/numDias)*100;

        String Spa=String.format("%.2f", (pa))+"%";
        String Spf=String.format("%.2f", ((100-(pa))))+"%";
        txtPorcenA.setText(Spa);
        txtPorcenF.setText(Spf);
        progressBar.setProgress((int)pa);
    }
}

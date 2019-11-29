package com.example.capstone.UIEmpleadoRegular;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstone.Instances.instances;
import com.example.capstone.Model.Asistencia;
import com.example.capstone.R;
import com.example.capstone.RVAdapterAsis;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaActivity extends AppCompatActivity {

    RecyclerView rvAsis;
    ProgressBar progressBar;
    Button btnSalir,btnJustificar;
    static   List<Asistencia> asistenciaList;
    private DatabaseReference mDatabase;
    TextView txtPorcenA,txtPorcenF;
     int  numDias=instances.getInstance().getNumDias(),cont=1;


    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_asistencia);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setUpView();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvAsis.setLayoutManager(linearLayoutManager);
        conseguirAsistencias();




    }
    private  void conseguirAsistencias(){

        asistenciaList= new ArrayList<>();
        while(i<numDias+1){

                System.out.println(cont);
                mDatabase.child("Asistencias").child("empleado" + SharedPrefPreferences.getInstance(getApplicationContext()).getNumEmple()).child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && cont<numDias+1) {

                            Object fech = dataSnapshot.child("Fecha").getValue();
                            Object asist = dataSnapshot.child("Asistio").getValue();
                            Object justifi = dataSnapshot.child("Justificó").getValue();
                            if (fech == null || asist == null || justifi == null) {
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
                      cont++;
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            i++;
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



    private void setUpView() {
        rvAsis = findViewById(R.id.rvAsis);
        btnSalir =findViewById(R.id.btnSalir);
        btnJustificar = findViewById(R.id.btnJustificar);
        txtPorcenA = findViewById(R.id.txtPorcenA);
        txtPorcenF = findViewById(R.id.txtPorcenF);
        progressBar=findViewById(R.id.progressBar);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmpleadoRegularActivity.class));
            }
        });
        btnJustificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JustificarActivity.class));
            }
        });
    }


}

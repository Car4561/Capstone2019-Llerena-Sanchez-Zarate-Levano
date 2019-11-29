package com.example.capstone.UIEmpleadoSupervisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstone.Fragments.AsistenciasFragment;
import com.example.capstone.Instances.instances;
import com.example.capstone.LoginActivity;
import com.example.capstone.Model.Asistencia;
import com.example.capstone.R;
import com.example.capstone.RVAdapterAsis;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIEmpleadoRegular.JustificarActivity;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.example.capstone.UIMenu.EmpleadoSupervisorActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AsistenciasActivity extends AppCompatActivity {
    RecyclerView rvAsis;
    ProgressBar progressBar;
    Spinner spnAsistencias;
    Button btnSalir,btnJustificar;
    static List<Asistencia> asistenciaList;
    private DatabaseReference mDatabase;
    TextView txtPorcenA,txtPorcenF;
    List<String> listEmpleados;
    int i=0;
    int  numDias= instances.getInstance().getNumDias();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setUpView();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        setUpSpinner();
    }

    private void setUpSpinner() {
        listEmpleados= new ArrayList<>();
        listEmpleados.add("Seleccione Empleado");
        while (i<instances.getInstance().getCantEmple()){
            System.out.println("i: "+ i+" cant: "+LoginActivity.cantEmple);
            mDatabase.child("Empleados").child("empleado"+i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()) {
                      listEmpleados.add(dataSnapshot.child("Nombres").getValue().toString()+" "+dataSnapshot.child("Apellidos").getValue().toString());
                      System.out.println(dataSnapshot.child("Nombres").getValue().toString());
                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, listEmpleados);
                      spnAsistencias.setAdapter(adapter);

                 }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            i++;
        }

        spnAsistencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               if(position!=0) {
                   AsistenciasFragment af = new AsistenciasFragment((position), (numDias));
                   getSupportFragmentManager().beginTransaction().replace(R.id.container, af).commit();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void conseguirAsistencias(int position) {

        asistenciaList= new ArrayList<>();
        for(int i=1;i<numDias+1;i++) {
            mDatabase.child("Asistencias").child("empleado"+ position).child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
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
    private void setUpView() {
        rvAsis = findViewById(R.id.rvAsis);
        btnSalir =findViewById(R.id.btnSalir);
        btnJustificar = findViewById(R.id.btnJustificar);
        txtPorcenA = findViewById(R.id.txtPorcenA);
        txtPorcenF = findViewById(R.id.txtPorcenF);
        progressBar=findViewById(R.id.progressBar);
        spnAsistencias=findViewById(R.id.spnAsistencia);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmpleadoSupervisorActivity.class));
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

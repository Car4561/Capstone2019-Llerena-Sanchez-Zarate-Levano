package com.example.capstone.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Instances.instances;
import com.example.capstone.Model.Justificacion;
import com.example.capstone.R;
import com.example.capstone.RVAdapterJust;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JustificacionesFragment extends Fragment {
    DatabaseReference mDatabase;
   static String nombre,nomb;
    RecyclerView rvJusti;
    List<Justificacion> justificacionList;
    int j=1,i=1,k=1,l=1,cont1=1,cont2=1,cont3=1,cont4=1,contI=1;
    public  JustificacionesFragment ( List<Justificacion> justificacionList){
        this.justificacionList=justificacionList;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        justificacionList= new ArrayList<>();
        View view =inflater.inflate(R.layout.fragment_justificaciones, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW,R.id.txtTitulo);
        params.setMargins(0,60,0,0);
        view.setLayoutParams(params);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        rvJusti=view.findViewById(R.id.rvAsis);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvJusti.setLayoutManager(linearLayoutManager);
        if(instances.getInstance().getList().size()==0 ){
            NoJustiFragment nf = new NoJustiFragment();
            getFragmentManager().beginTransaction().replace(R.id.contentJust,nf).commit();
        }
        conseguirJustificaciones();

        System.out.println(instances.getInstance().getList().size()+"asgfsdgsdg");
        return view;
    }

    private void conseguirJustificaciones() {
        justificacionList= new ArrayList<>();
        while(j< instances.getInstance().getCantEmple() && cont1<instances.getInstance().getCantEmple()) {
            conseguirNombre(j);
            i=1;
            contI=1;
            while(i<instances.getInstance().getNumDias()+1) {
                System.out.println("j= "+j+" i= "+i);
                instances.getInstance().setI(i);
                instances.getInstance().setJ(j);
                mDatabase.child("Asistencias").child("empleado" + instances.getInstance().getJ()).child(String.valueOf(instances.getInstance().getI())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        Object justi = dataSnapshot.child("Justificación").getValue();
                        Boolean justBool = Boolean.valueOf(dataSnapshot.child("Justificó").getValue().toString());
                        String fecha = dataSnapshot.child("Fecha").getValue().toString();

                        if(justi!=null && !justBool) {
                            justificacionList.add(new Justificacion(fecha,instances.getInstance().getNombre(),justi.toString(),cont1,contI));
                            System.out.println("Agrego en " +"c="+cont1+" i="+contI);
                            RVAdapterJust adapterJust= new RVAdapterJust(justificacionList);
                            rvJusti.setAdapter(adapterJust);


                            adapterJust.setOnClickListener1(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                    final int position= rvJusti.getChildAdapterPosition(view);
                                    String[] arg= new String[]{justificacionList.get(position).getJustificacion()};
                                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            k=1;
                                            cont2=1;
                                            while(k< instances.getInstance().getCantEmple()) {
                                                l=1;
                                                cont3=1;
                                                while(l<instances.getInstance().getNumDias()+1) {
                                                    mDatabase.child("Asistencias").child("empleado"+k).child(String.valueOf(l)).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            Object justificacion = dataSnapshot.child("Justificación").getValue();
                                                            if(justificacion!=null) {
                                                                if (justificacion.toString().equals(justificacionList.get(position).getJustificacion()))
                                                                {
                                                                    mDatabase.child("Asistencias").child("empleado" +justificacionList.get(position).getNumEpleado()).child(String.valueOf(justificacionList.get(position).getDia())).child("Justificó").setValue("true");
                                                                    Toast.makeText(getContext(), "Justificación Aceptada", Toast.LENGTH_SHORT).show();
                                                                    if(justificacionList.size()!=1) {
                                                                        JustificacionesFragment jf = new JustificacionesFragment(justificacionList);
                                                                        getFragmentManager().beginTransaction().replace(R.id.contentJust, jf).commit();
                                                                    }else{
                                                                        NoJustiFragment nf = new NoJustiFragment();
                                                                        getFragmentManager().beginTransaction().replace(R.id.contentJust,nf).commit();

                                                                    }

                                                                }
                                                            }

                                                            cont3++;
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                    l++;
                                                }
                                                k++;
                                                cont2++;
                                            }

                                        }
                                    });
                                    dialog.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    dialog.setTitle("Revision de Justificación").setItems(arg,null);
                                    dialog.show();

                                }
                            });
                            instances.getInstance().setList(justificacionList);
                        }


                        Log.d("TAG1",String.valueOf(justificacionList.size()));

                        contI++;
                        if(contI>=instances.getInstance().getNumDias()+1){{
                            cont1++;
                            contI=1;
                        }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
               i++;


            }
            j++;
        }
      cont4=100;

    }
    private void conseguirNombre(int j) {

        mDatabase.child("Empleados").child("empleado"+j).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] nombres =  String.valueOf(dataSnapshot.child("Nombres").getValue()).split(" ");
                String[] apellidos= String.valueOf(dataSnapshot.child("Apellidos").getValue()).split(" ");
                nombre = nombres[0]+" "+apellidos[0];
                instances.getInstance().setNombre(nombre);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
/*
    private void conseguirNombre(int j) {

        mDatabase.child("Empleados").child("empleado"+j).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] nombres =  String.valueOf(dataSnapshot.child("Nombres").getValue()).split(" ");
                String[] apellidos= String.valueOf(dataSnapshot.child("Apellidos").getValue()).split(" ");
                nombre = nombres[0]+" "+apellidos[0];
                instances.getInstance().setNombre(nombre);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

 */
}

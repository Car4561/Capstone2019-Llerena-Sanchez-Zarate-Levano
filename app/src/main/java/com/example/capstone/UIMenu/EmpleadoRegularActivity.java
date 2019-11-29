package com.example.capstone.UIMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.Instances.instances;
import com.example.capstone.LoginActivity;
import com.example.capstone.Model.Empleado;
import com.example.capstone.R;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIEmpleadoRegular.AsistenciaActivity;
import com.example.capstone.UIEmpleadoRegular.JustificarActivity;
import com.example.capstone.PerfilActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmpleadoRegularActivity extends AppCompatActivity {
    private ImageView btnAsistencias,btnJustificar,btnSalir,btnPerfil;
    private TextView tvID,tvNombApe,tvRol;
    private Empleado empleado;
    private DatabaseReference mDatabase;
    public  static int numDias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empleado_regular);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        numDias();
        setUpView();
        setUpEmpleado();

        if(SharedPrefPreferences.getInstance(getApplicationContext()).getEmpleado().getId()==-1){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }

    private void setUpEmpleado() {
        empleado = SharedPrefPreferences.getInstance(getApplicationContext()).getEmpleado();
        tvID.setText(String.valueOf(empleado.getId()));
        String[] nombres = empleado.getNombres().split(" ");
        String[] apellidos=empleado.getApellidos().split(" ");
        tvNombApe.setText(nombres[0]+" "+apellidos[0]);
        tvRol.setText("Empleado "+empleado.getRol());

    }

    private void setUpView() {
        btnAsistencias=findViewById(R.id.btnAsistencias);
        btnJustificar =findViewById(R.id.btnJustificar);
        btnSalir = findViewById(R.id.btnSalir);
        btnPerfil= findViewById(R.id.btnPerfil);
        tvID=findViewById(R.id.tvID);
        tvNombApe=findViewById(R.id.tvNombApe);
        tvRol=findViewById(R.id.tvRol);


        btnAsistencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AsistenciaActivity.class));
            }
        });

        btnJustificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JustificarActivity.class));
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPrefPreferences.getInstance(getApplicationContext()).logOut();
                startActivity(intent);

            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
            }
        });

    }
    private void numDias() {
        mDatabase.child("numDias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numDias=Integer.parseInt(dataSnapshot.getValue().toString());
                instances.getInstance().setNumDias(numDias);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

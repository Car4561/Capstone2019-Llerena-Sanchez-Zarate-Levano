package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone.Model.Empleado;
import com.example.capstone.Model.Supervisor;
import com.example.capstone.R;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.example.capstone.UIMenu.EmpleadoSupervisorActivity;

public class PerfilActivity extends AppCompatActivity {
    TextView tvId,tvNombres,tvApellidos,tvEmail,tvEdad,tvRol;
    Empleado empleado;
    Button btnSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setUpView();
        setUpEmpleado();

    }


    private void setUpView() {
       tvId = findViewById(R.id.tvID);
       tvNombres = findViewById(R.id.tvNombres);
       tvApellidos = findViewById(R.id.tvApellidos);
       tvEdad = findViewById(R.id.tvEdad);
       tvEmail = findViewById(R.id.tvEmail);
       tvRol = findViewById(R.id.tvRol);
       btnSalir= findViewById(R.id.btnSalir);
       btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPrefPreferences.getInstance(getApplicationContext()).getEmpleado().getRol().equals("Supervisor")){
                    startActivity(new Intent(getApplicationContext(), EmpleadoSupervisorActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), EmpleadoRegularActivity.class));
                }

            }
        });

    }

    private void setUpEmpleado(){
        empleado = SharedPrefPreferences.getInstance(getApplicationContext()).getEmpleado();
        tvId.setText(String.valueOf(empleado.getId()));
        tvNombres.setText(empleado.getNombres());
        tvApellidos.setText(empleado.getApellidos());
        tvEdad.setText(String.valueOf(empleado.getEdad()));
        tvEmail.setText(empleado.getEmail());
        tvRol.setText("Empleado "+empleado.getRol());
    }
}

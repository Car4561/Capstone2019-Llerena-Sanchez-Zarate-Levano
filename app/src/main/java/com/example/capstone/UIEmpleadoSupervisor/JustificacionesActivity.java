package com.example.capstone.UIEmpleadoSupervisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstone.Fragments.JustificacionesFragment;
import com.example.capstone.R;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.example.capstone.UIMenu.EmpleadoSupervisorActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JustificacionesActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button btnSalir;
    RecyclerView rvJusti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificaciones);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmpleadoSupervisorActivity.class));

            }
        });
        JustificacionesFragment jf = new JustificacionesFragment(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentJust,jf).commit();

    }
}

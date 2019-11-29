package com.example.capstone.UIEmpleadoRegular;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Instances.instances;
import com.example.capstone.R;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class JustificarActivity extends AppCompatActivity {
    Button btnSalir,btnEnviar;
    TextView txtJusti;
    int j=1,positionF,cont=1,pas=0;
    Calendar calendario = Calendar.getInstance();
    Spinner spnFecha;
    private DatabaseReference mDatabase;
    List<String> listFech;
    List<Integer> listPosition;
    Long  mLastClickTime=0l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnSalir = findViewById(R.id.btnSalir);
        spnFecha= findViewById(R.id.spnFecha);
        btnEnviar=findViewById(R.id.btnEnviar);
        txtJusti = findViewById(R.id.txtJusti);
        ConseguirFechas();
        pas++;
        spnFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                positionF=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmpleadoRegularActivity.class));
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLastClickTime = SystemClock.elapsedRealtime();
                mDatabase.child("Asistencias").child("empleado"+SharedPrefPreferences.getInstance(getApplicationContext()).getNumEmple()).child(String.valueOf(listPosition.get(positionF))).child("Justificación").setValue(txtJusti.getText().toString().trim());
                System.out.println(listFech);
                Toast.makeText(getApplicationContext(), "Justificación Enviada", Toast.LENGTH_SHORT).show();
                txtJusti.setText("");
            }
        });

    }
    private void ConseguirFechas() {
        listPosition = new ArrayList<>();
        listFech= new ArrayList<>();
        while(j<= instances.getInstance().getNumDias()) {
            mDatabase.child("Asistencias").child("empleado" + SharedPrefPreferences.getInstance(getApplicationContext()).getNumEmple()).child(String.valueOf(j)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!Boolean.valueOf(dataSnapshot.child("Asistio").getValue().toString()) && !Boolean.valueOf(dataSnapshot.child("Justificó").getValue().toString()) && cont<=instances.getInstance().getNumDias()) {
                             listFech.add(dataSnapshot.child("Fecha").getValue().toString());
                             listPosition.add(cont);

                        }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, listFech);
                    spnFecha.setAdapter(adapter);
                    cont++;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            j++;
        }
        j=1;

    }





}

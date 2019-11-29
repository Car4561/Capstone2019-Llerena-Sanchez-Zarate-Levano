package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.Instances.instances;
import com.example.capstone.Model.Empleado;
import com.example.capstone.SharedPreferences.SharedPrefPreferences;
import com.example.capstone.UIMenu.EmpleadoRegularActivity;
import com.example.capstone.UIMenu.EmpleadoSupervisorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button btnIngresar;
        private FirebaseAuth mAuth;
        private EditText txtEmail,txtPassword;
        private DatabaseReference mDatabase;
        public  static  int cantEmple,numEmple,cantSupervisor,cantEmple2;
        private Empleado empleado;
        private  int i=1;
        private  int cont=1;
        int contg=1;
        int j=1;
        int cont1=1;
        int conts=1;
        boolean a;
        static String rol;
        Long  mLastClickTime=0l;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            btnIngresar=findViewById(R.id.btnLogin);
            txtEmail=findViewById(R.id.txtEmail);
            txtPassword=findViewById(R.id.txtPassword);
            mAuth=FirebaseAuth.getInstance();
            mDatabase= FirebaseDatabase.getInstance().getReference();
            Log.d("TAG1","real time :"+SystemClock.elapsedRealtime() +" last:  "+mLastClickTime);
            cantEmpleado();
            cantSupervisor();
            btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG1","real time :"+SystemClock.elapsedRealtime() +" last:  "+mLastClickTime);
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                BuscarEmpleado();
               }
            });
    }



    private void BuscarSupervisor() {
        conts=1;
        cont1=1;
        contg=1;
        cantEmple2 =cantSupervisor;
        System.out.println("CONCHATUMADRE");
        System.out.println(cantSupervisor);
        for( int i =1;i<cantSupervisor+1;i++) {
            System.out.println("SUPERVISOR CONCTM "+i);
            mDatabase.child("RRHH").child("supervisor"+i).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println("Afuera del todoooo12ooooooo ");
                    Log.d("TAG1", "contas: " + String.valueOf(conts));
                    final String usuario = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    if(usuario.isEmpty()){
                        txtEmail.setError("Error Usuario vacio");
                        return;
                    }
                    if(password.isEmpty()){
                        txtPassword.setError("Error Password vacio");
                        return;
                    }
                    System.out.println("Afuera del todo");
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(usuario).matches  () ) {
                        System.out.println("Afuera del todo2");
                        for( j=1;j<cantEmple;j++) {
                            mDatabase.child("RRHH").child("supervisor" + j).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    System.out.println("Afuera del if"+j);
                                    if (dataSnapshot.child("email").getValue().toString().equals(usuario)) {
                                        a=true;
                                        System.out.println(dataSnapshot.child("email").getValue().toString());
                                        System.out.println("Adentro del if"+cont1    );
                                        instances.getInstance().setNumEmpleado(cont1-1);

                                    } else {
                                        cont1++;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        if(instances.getInstance().getNumEmpleado()==contg){
                            loginEmail(usuario, password,cont);

                        }



                    }
                    else {
                        String email = null;
                        Object emailNull = dataSnapshot.child(usuario).getValue();
                        if (emailNull == null) {
                            conts++;
                            login(null, password, conts);
                            return;
                        }
                        email = emailNull.toString();
                        login(email, password, conts);
                        Log.d("TAG1", "cont: " + String.valueOf(conts));
                    }
                    contg++;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        Log.d("TAG1",String.valueOf(cantSupervisor));



    }

    private void BuscarEmpleado() {
        cont=1;
        cont1=1;
        contg=1;
        for( int i =1;i<cantEmple+1;i++) {

            System.out.println("Afuera del todooooooooooo "+i);
            mDatabase.child("Empleados").child("empleado"+i).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String usuario = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    if(usuario.isEmpty()){
                        txtEmail.setError("Error Usuario vacio");
                        return;
                    }
                    if(password.isEmpty()){
                        txtPassword.setError("Error Password vacio");
                        return;
                    }
                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(usuario).matches  () ) {
                        for( int j=1;j<cantEmple;j++) {
                            mDatabase.child("Empleados").child("empleado" + j).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child("email").getValue().toString().equals(usuario)) {

                                            System.out.println(dataSnapshot.child("email").getValue().toString());
                                            instances.getInstance().setNumEmpleado(cont1 - 1);

                                        } else {
                                            cont1++;
                                        }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        if(instances.getInstance().getNumEmpleado()==contg){
                            loginEmail(usuario, password,cont);

                        }



                    }
                    else {
                        String email = null;
                        Object emailNull = dataSnapshot.child(usuario).getValue();
                        if (emailNull == null) {
                            cont++;
                            login(null, password, cont);
                            return;
                        }
                        email = emailNull.toString();
                        login(email, password, cont);
                        Log.d("TAG1", "cont: " + String.valueOf(cont));
                    }
                    contg++;
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        Log.d("TAG1",String.valueOf(cantEmple));


    }

    private void buscar(final String email){


    }
    private void cantEmpleado() {
        mDatabase.child("numEmple").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cantEmple=Integer.parseInt(dataSnapshot.getValue().toString())+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void cantSupervisor() {
        mDatabase.child("numSupervisor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cantSupervisor=Integer.parseInt(dataSnapshot.getValue().toString())+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void login(final String email, String password,final int con) {

        if(email ==null ){
           if(con==cantEmple+1){
               Toast.makeText(getApplicationContext(),"Usuario  o contraseña incorrecta",Toast.LENGTH_SHORT).show();
           }
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Inicio Sesión con Éxito",Toast.LENGTH_SHORT).show();
                    instances.getInstance().setNumEmpleado(con);
                    a = true;
                    saveEmpleado();




                }else{
                    Toast.makeText(getApplicationContext(),"Usuario  o contraseña incorrecta",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void loginEmail(final String email, String password,final int con) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Inicio Sesión con Éxito",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EmpleadoRegularActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    buscar(email);
                    saveEmpleado();
                    Log.d("TAG1",String.valueOf(instances.getInstance().getNumEmpleado()));

                    SharedPrefPreferences.getInstance(getApplicationContext()).saveNumEmple(instances.getInstance().getNumEmpleado());
                    instances.getInstance().setA(true);
                    startActivity(intent);



                }else{
                    Toast.makeText(getApplicationContext(),"Usuario  o contraseña incorrecta",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    private void saveEmpleado() {
        System.out.println(instances.getInstance().getNumEmpleado());
        String child;
        String usuariochild;
        child ="Empleados";
        usuariochild="empleado";


        mDatabase.child(child).child(usuariochild+ instances.getInstance().getNumEmpleado()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empleado =new Empleado();
                empleado.setId(Long.parseLong(dataSnapshot.child("id").getValue().toString()));
                empleado.setEmail(dataSnapshot.child(dataSnapshot.child("id").getValue().toString()).getValue().toString());
                empleado.setNombres(dataSnapshot.child("Nombres").getValue().toString());
                empleado.setApellidos(dataSnapshot.child("Apellidos").getValue().toString());
                empleado.setEdad(Integer.parseInt(dataSnapshot.child("edad").getValue().toString()));
                empleado.setRol(dataSnapshot.child("rol").getValue().toString());
                System.out.println("GAAAAAAAA"+empleado.getRol());
                instances.getInstance().setRol(empleado.getRol());
                SharedPrefPreferences.getInstance(getApplicationContext()).saveEmpleado(empleado);
                logeo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void logeo() {
        Log.d("TAG1","numEplea"+String.valueOf(instances.getInstance().getNumEmpleado()));
        SharedPrefPreferences.getInstance(getApplicationContext()).saveNumEmple(instances.getInstance().getNumEmpleado());
        System.out.println("ctm"+instances.getInstance().getRol());
        if(instances.getInstance().getRol().equals("Supervisor")) {
            Intent intent = new Intent(getApplicationContext(), EmpleadoSupervisorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), EmpleadoRegularActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void saveSupervisor() {
        System.out.println(instances.getInstance().getNumEmpleado());
        mDatabase.child("Empleados").child("supervisor"+ instances.getInstance().getNumSupervisor()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empleado =new Empleado();
                empleado.setId(Long.parseLong(dataSnapshot.child("id").getValue().toString()));
                empleado.setEmail(dataSnapshot.child(dataSnapshot.child("id").getValue().toString()).getValue().toString());
                empleado.setNombres(dataSnapshot.child("Nombres").getValue().toString());
                empleado.setApellidos(dataSnapshot.child("Apellidos").getValue().toString());
                empleado.setEdad(Integer.parseInt(dataSnapshot.child("edad").getValue().toString()));
                SharedPrefPreferences.getInstance(getApplicationContext()).saveEmpleado(empleado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefPreferences.getInstance(getApplicationContext()).isLogin()){
            if(SharedPrefPreferences.getInstance(getApplicationContext()).getEmpleado().getRol().equals("Supervisor")) {
                Intent intent = new Intent(getApplicationContext(), EmpleadoSupervisorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getApplicationContext(), EmpleadoRegularActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}

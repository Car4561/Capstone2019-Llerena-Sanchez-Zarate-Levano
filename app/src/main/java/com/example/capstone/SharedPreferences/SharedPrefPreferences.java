package com.example.capstone.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.capstone.Model.Empleado;

public class SharedPrefPreferences {

    private static final String  SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String  SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";
    private static final String  SHARED_PREFERENCES_EMAIL = "SHARED_PREFERENCES_EMAIL";
    private static final String  SHARED_PREFERENCES_NOMBRES = "SHARED_PREFERENCES_NOMBRES";
    private static final String  SHARED_PREFERENCES_APELLIDOS = "SHARED_PREFERENCES_APELLIDOS";
    private static final String  SHARED_PREFERENCES_EDAD = "SHARED_PREFERENCES_EDAD";
    private static final String  SHARED_PREFERENCES_ROL = "SHARED_PREFERENCES_ROL";
    private static final String  SHARED_PREFERENCES_ASISTENCIAS = "SHARED_PREFERENCES_ASISTENCIAS";
    private static final String  SHARED_PREFERENCES_NUMEMPLEADO = "SHARED_PREFERENCES_NUMEMPLEADO";


    private static SharedPrefPreferences instance;
    private Empleado empleado;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private int numEmple;

    private SharedPrefPreferences(Context context){
         this.context=context;
         sharedPreferences= context.getSharedPreferences(SHARED_PREFERENCES,context.MODE_PRIVATE);
         editor= sharedPreferences.edit();
    }
    public static synchronized SharedPrefPreferences getInstance(Context context){
          if(instance==null){
              instance= new SharedPrefPreferences(context);
          }
          return instance;
    }

   public void saveEmpleado(Empleado empleado){
         editor.putLong(SHARED_PREFERENCES_ID,empleado.getId());
         editor.putString(SHARED_PREFERENCES_EMAIL,empleado.getEmail());
         editor.putString(SHARED_PREFERENCES_NOMBRES,empleado.getNombres());
         editor.putString(SHARED_PREFERENCES_APELLIDOS,empleado.getApellidos());
         editor.putInt(SHARED_PREFERENCES_EDAD,empleado.getEdad());
         editor.putString(SHARED_PREFERENCES_ROL,empleado.getRol());
         editor.apply();


   }
   public Empleado getEmpleado(){
        empleado = new Empleado();
        empleado.setId(sharedPreferences.getLong(SHARED_PREFERENCES_ID,-1));
        empleado.setEmail(sharedPreferences.getString(SHARED_PREFERENCES_EMAIL,null));
        empleado.setNombres(sharedPreferences.getString(SHARED_PREFERENCES_NOMBRES,null));
        empleado.setApellidos(sharedPreferences.getString(SHARED_PREFERENCES_APELLIDOS,null));
        empleado.setEdad(sharedPreferences.getInt(SHARED_PREFERENCES_EDAD,-1));
        empleado.setRol(sharedPreferences.getString(SHARED_PREFERENCES_ROL,null));
        return empleado;
   }

    public boolean isLogin(){
        if(sharedPreferences.getLong(SHARED_PREFERENCES_ID,-1)!=-1){
            return true;
        }
        return false;
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }

    public void saveNumEmple(int numEmple){
        editor.putInt(SHARED_PREFERENCES_NUMEMPLEADO,numEmple);
        editor.apply();
    }
    public int getNumEmple(){
        return sharedPreferences.getInt(SHARED_PREFERENCES_NUMEMPLEADO,0);
    }

}

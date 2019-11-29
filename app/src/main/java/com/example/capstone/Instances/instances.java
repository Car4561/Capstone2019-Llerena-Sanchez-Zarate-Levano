package com.example.capstone.Instances;

import com.example.capstone.Model.Justificacion;

import java.util.ArrayList;
import java.util.List;

public class instances {
    private  static instances instance;
    private  int numEmpleado,numSupervisor,numDias,cantEmple,j,i;
    private String nombre;
    private  boolean a;
    private String rol;
    List<Justificacion> justificacionList= new ArrayList<>();

    private instances(){

    }
    public static synchronized instances getInstance(){
        if(instance==null){
            instance=new instances();
        }
        return instance;
    }
    public int getNumEmpleado(){
        return numEmpleado;
    }
    public void setNumEmpleado(int numEmpleado){
        this.numEmpleado=numEmpleado;
    }
    public boolean isA(){
        return a;
    }
    public  void  setA(boolean a){
        this.a=a;
    }

    public int getNumSupervisor() {
         return  numSupervisor;
    }
    public void setNumSupervisor(int numSupervisor){
        this.numSupervisor=numSupervisor;
    }
    public String getRol(){
        return  rol;
    }
    public void setRol(String rol){
        this.rol=rol;

    }
    public int getNumDias(){
        return  numDias;
    }
    public void setNumDias(int numDias){
        this.numDias=numDias;

    }
    public int getCantEmple(){
        return  cantEmple;
    }
    public void setCantEmple(int cantEmple){
        this.cantEmple=cantEmple;

    }
    public String getNombre(){
        return  nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;

    }
    public int getJ(){
        return  j;
    }
    public void setJ(int j){
        this.j=j;

    }
    public int getI(){
        return  i;
    }
    public void setI(int i){
        this.i=i;

    }
    public List<Justificacion> getList(){
        return  this.justificacionList;
    }
    public void setList(List<Justificacion> justificacionList){
        this.justificacionList=justificacionList;

    }

}

package com.example.capstone.Model;

public class Justificacion {
    private String fecha;
    private String nombres;
    private String Justificacion;
    private int numEpleado;
    private int dia;

    public Justificacion(String fecha, String nombres, String justificacion, int numEpleado, int dia) {
        this.fecha = fecha;
        this.nombres = nombres;
        Justificacion = justificacion;
        this.numEpleado = numEpleado;
        this.dia = dia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getJustificacion() {
        return Justificacion;
    }

    public void setJustificacion(String justificacion) {
        Justificacion = justificacion;
    }

    public int getNumEpleado() {
        return numEpleado;
    }

    public void setNumEpleado(int numEpleado) {
        this.numEpleado = numEpleado;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}

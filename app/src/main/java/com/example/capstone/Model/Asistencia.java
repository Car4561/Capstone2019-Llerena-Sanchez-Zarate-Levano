package com.example.capstone.Model;

public class Asistencia {

      private String fecha;
      private boolean asistio;
      private boolean justifico;

    public Asistencia(String fecha, boolean asistio, boolean justifico) {
        this.fecha = fecha;
        this.asistio = asistio;
        this.justifico = justifico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isJustifico() {
        return justifico;
    }

    public void setJustifico(boolean justifico) {
        this.justifico = justifico;
    }
}

package com.example.capstone.Model;

import com.example.capstone.Model.Asistencia;

import java.util.List;

public class Empleado {
    private Long id;
    private String email;
    private String nombres;
    private String apellidos;
    private int edad;
    private String rol;


    public Empleado() {
    }

    public Empleado(Long id) {
        this.id = id;
    }

    public Empleado(Long id, String email, String nombres, String apellidos, int edad, String rol) {
        this.id = id;
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

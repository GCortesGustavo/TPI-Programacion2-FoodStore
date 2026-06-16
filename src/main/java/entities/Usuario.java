/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
import enums.EnumsRol;
/**
 *
 * @author Santiago Villalba
 */
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private EnumsRol rol;
    private boolean eliminado;

    public Usuario(int id, String nombre, String email, EnumsRol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.eliminado = false;
    }

     //=================== GETTERs Y SETTERS=======================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumsRol getRol() {
        return rol;
    }

    public void setRol(EnumsRol rol) {
        this.rol = rol;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    //===================TERMINAN LOS GETTERS Y SETTERS=======================
    

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + email + ") - " + rol;
    }
}

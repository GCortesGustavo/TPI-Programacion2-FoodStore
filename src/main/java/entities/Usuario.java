/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.EnumsRol;

/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class Usuario extends Base {

    private String nombre;
    private String email;
    private EnumsRol rol;

    //Constructor para cuando se crea un usuario nuevo
    public Usuario(String nombre, String email, EnumsRol rol) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    //Constructor para cuando se trae un usuario de la BD
    public Usuario(Long id, String nombre, String email, EnumsRol rol) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    //=================== GETTERs Y SETTERS=======================
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

    //===================TERMINAN LOS GETTERS Y SETTERS=======================
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + email + ") - " + rol;
    }
}

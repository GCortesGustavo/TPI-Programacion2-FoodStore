/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.EnumsRol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Villalba - Cortés - Lorenzo - Flores
 */
public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String contrasenia;
    private EnumsRol rol;
    private List<Pedido> pedidos = new ArrayList();
    private PerfilDetalle perfil;

    //Constructor para cuando se crea un usuario nuevo
    public Usuario(String nombre, String apellido, String email, String celular, String contrasenia, EnumsRol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.perfil = new PerfilDetalle("No asignada", "No asignada");
    }

    //Constructor para cuando se trae un usuario de la BD
    public Usuario() {
        super();
    }

    //=================== GETTERs Y SETTERS=======================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasena) {
        this.contrasenia = contrasena;
    }

    public EnumsRol getRol() {
        return rol;
    }

    public void setRol(EnumsRol rol) {
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public PerfilDetalle getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDetalle perfil) {
        this.perfil = perfil;
    }

    //===================TERMINAN LOS GETTERS Y SETTERS=======================
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + email + ") - " + rol;
    }
}

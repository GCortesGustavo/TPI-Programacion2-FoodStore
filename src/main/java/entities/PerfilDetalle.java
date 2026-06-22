/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Usuario
 */
public class PerfilDetalle extends Base {

    private String direccion;
    private String ciudad;

    public PerfilDetalle() {
        super();
    }

    public PerfilDetalle(String direccion, String ciudad) {
        super();
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}

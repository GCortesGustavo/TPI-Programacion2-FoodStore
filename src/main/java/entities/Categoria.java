/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class Categoria extends Base {
    private String nombre;
    private String descripcion;

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    
     //=================== GETTER Y SETTER=======================

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    //===================TERMINAN LOS GETTER Y SETTER========================

    @Override
    public String toString() {
        return "[" + id + "] " + nombre;
    }
}

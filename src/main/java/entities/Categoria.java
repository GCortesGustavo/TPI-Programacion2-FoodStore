/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Santiago Villalba
 */
public class Categoria {
    private int id;
    private String nombre;
    private boolean eliminado;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.eliminado = false;
    }
    
     //=================== GETTER Y SETTER=======================

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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    //===================TERMINAN LOS GETTER Y SETTER========================

    @Override
    public String toString() {
        return "[" + id + "] " + nombre;
    }
}

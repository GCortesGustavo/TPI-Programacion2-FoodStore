/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Santiago Villalba
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;
    private boolean eliminado;

    public Producto(int id, String nombre, double precio, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.eliminado = false;
    }
    
    //=================GETTERS Y SETTERS=============================
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    
    public void setEliminado(boolean eliminado) {    
        this.eliminado = eliminado;
    }

    //=================TERMINAN LOS GETTERS Y SETTERS=============================
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | $" + precio + " | Stock: " + stock + " | Cat: " + categoria.getNombre();
    }
}

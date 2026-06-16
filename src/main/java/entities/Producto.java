/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class Producto extends Base {
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;

    public Producto(String nombre, double precio, int stock, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
    
    //=================GETTERS Y SETTERS=============================

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

    //=================TERMINAN LOS GETTERS Y SETTERS=============================
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | $" + precio + " | Stock: " + stock + " | Cat: " + categoria.getNombre();
    }
}

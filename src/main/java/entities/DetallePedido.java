/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Santiago Villalba
 */
public class DetallePedido {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }
    
    //=================== GETTERS Y SETTERS=======================

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
    
    //===================TERMIANAN LOS GETTERS Y SETTERS=======================
    
    @Override
    public String toString() {
        return cantidad + "x " + producto.getNombre() + " ($" + precioUnitario + ") = $" + getSubtotal();
    }
}

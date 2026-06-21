/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Cortez - Lorenzo - Villalba - Flores
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
 
public class Categoria extends Base {
 //Gusty va el cambio
    private String nombre;
    private String descripcion;
    private List<Producto> productos;
 
    public Categoria() {
        this.productos = new ArrayList<>();
    }
 
    public Categoria(Long id, String nombre, String descripcion) {
        setId(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }
    
    public Categoria(String nombre, String descripcion) {
        super(); 
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }
 
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
 
    public List<Producto> getProductos() {
        return productos;
    }
 
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
 
    public void agregarProducto(Producto producto) {
        if (producto != null && !productos.contains(producto)) {
            productos.add(producto);
        }
    }
 
    public void quitarProducto(Producto producto) {
        productos.remove(producto);
    }
 
    public boolean tieneProductos() {
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(getId(), categoria.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
 
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductoService {

    private ProductoDAO dao = new ProductoDAO();

    public void crearProducto(String nombre, double precio, int stock, Categoria cat, String desc) throws Exception {
        if (nombre.isBlank()) {
            throw new Exception("Nombre obligatorio.");
        }
        if (precio < 0 || stock < 0) {
            throw new Exception("Valores no pueden ser negativos.");
        }

        Producto existente = dao.buscarPorNombre(nombre);
        if (existente != null) {
            throw new Exception("Error: Ya existe un producto activo con el nombre '" + nombre + "'.");
        }

        Producto nuevo = new Producto(nombre, precio, desc, stock, cat);
        dao.guardar(nuevo);
    }

    public List<Producto> listarProductos() throws Exception {
        return dao.listarTodos();
    }

    public void eliminarProducto(Long id) throws Exception {
        dao.eliminar(id);
    }

    public Producto buscarProductoPorId(Long id) throws Exception {
        return dao.buscarPorId(id);
    }

    public void modificarProducto(Producto p) throws Exception {
        if (p.getPrecio() < 0 || p.getStock() < 0) {
            throw new Exception("Error: Precio o Stock no pueden ser negativos.");
        }
        dao.modificar(p);
    }
}

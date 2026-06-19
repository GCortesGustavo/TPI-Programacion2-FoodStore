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

    public void crearProducto(String nombre, double precio, int stock, Categoria cat) throws Exception {
        // TODO: 1. Validar que el nombre no sea vacío
        // TODO: 2. Validar que precio y stock sean >= 0
        // TODO: 3. Crear el objeto Producto y llamar al dao.guardar()
        System.out.println("Lógica de creación de producto pendiente...");
    }

    public List<Producto> listarProductos() {
        try {
            // TODO: Llamar al dao.listarTodos()
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al listar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void eliminarProducto(Long id) throws Exception {
        // TODO: Llamar al dao.eliminar(id)
    }

    public void modificarProducto(Producto p) throws Exception {
        // TODO: Llamar al dao.modificar(p)
    }
}

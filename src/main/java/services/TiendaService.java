/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import dao.CategoriaDAO;
import dao.UsuarioDAO;
import entities.*;
import enums.*;
import exception.ExceptionsMenu;
        
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class TiendaService {
    
    private CategoriaDAO categoriaDao = new CategoriaDAO();
    // private UsuarioDAO usuarioDao = new UsuarioDAO(); // Descomentar cuando se cree el archivo

    // =========================================================================
    // MÉTODOS DE CATEGORÍAS (A completar)
    // =========================================================================
    
    public void crearCategoria(String nombre) {
        // TODO: Llamar al categoriaDao.guardar()
        System.out.println("Lógica de base de datos pendiente en Service...");
    }

    public List<Categoria> listarCategorias() {
        try {
            return categoriaDao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void eliminarCategoria(Long id) {
        try {
            categoriaDao.eliminar(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // =========================================================================
    // MÉTODOS DE USUARIOS (A completar)
    // =========================================================================

    public void crearUsuario(String nombre, String email, EnumsRol rol) throws ExceptionsMenu {
        // TODO: Validar email y llamar al usuarioDao.guardar()
    }

    public List<Usuario> listarUsuarios() {
        // TODO: Llamar al usuarioDao.listarTodos()
        return new ArrayList<>(); 
    }

    public void eliminarUsuario(Long id) throws ExceptionsMenu {
        // TODO: Llamar al usuarioDao.eliminar()
    }

    // =========================================================================
    // MÉTODOS DE PRODUCTOS (A completar)
    // =========================================================================

    public void crearProducto(String nombre, double precio, int stock, Long idCategoria) throws ExceptionsMenu {
        // TODO: Validar precio y llamar al productoDao.guardar()
    }

    public List<Producto> listarProductos() {
        return new ArrayList<>();
    }
}

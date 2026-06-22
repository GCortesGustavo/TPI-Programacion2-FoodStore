/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.CategoriaDAO;
import entities.Categoria;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class CategoriaService {

    private CategoriaDAO dao = new CategoriaDAO();

    public void crearCategoria(String nombre, String descripcion) throws Exception {
        // La lógica de validación de negocio va aquí
        Categoria nueva = new Categoria(nombre, descripcion);
        dao.guardar(nueva);
    }

    public List<Categoria> listarCategorias() throws Exception {
        return dao.listarTodos();
    }

    public void eliminarCategoria(Long id) throws Exception {
        dao.eliminar(id);
    }

    // Método para el menú de edición que vendrá después
    public void modificarCategoria(Long id, String nombre, String descripcion) throws Exception {
        Categoria cat = new Categoria(id, nombre, descripcion);
        dao.modificar(cat);
    }

    public Categoria buscarCategoriaPorId(Long id) throws Exception {
        Categoria cat = dao.buscarPorId(id);
        if (cat == null) {
            throw new Exception("No existe ninguna categoría con el ID: " + id);
        }
        return cat;
    }
}

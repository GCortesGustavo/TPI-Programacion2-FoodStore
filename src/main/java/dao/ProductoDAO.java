/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Categoria;
import entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductoDAO implements DAO<Producto> {

    public ProductoDAO() {
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        List<Producto> productos = new ArrayList<>();
        // JOIN para traer el nombre de la categoría y mostrarlo en el listado
        String sql = "SELECT p.*, c.nombre AS categoria_nombre "
                   + "FROM productos p "
                   + "INNER JOIN categorias c ON p.categoria_id = c.id "
                   + "WHERE p.eliminado = false";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("categoria_id"));
                categoria.setNombre(rs.getString("categoria_nombre"));

                // Usamos el constructor: String, double, int, Categoria
                Producto producto = new Producto(
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        categoria
                );
                
                producto.setId(rs.getLong("id"));
                producto.setEliminado(rs.getBoolean("eliminado"));

                productos.add(producto);
            }
        }
        return productos;
    }

    @Override
    public void guardar(Producto producto) throws Exception {
        // Validaciones de lógica de negocio heredadas de Facundo
        if (producto.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser menor a 0");
        if (producto.getStock() < 0) throw new IllegalArgumentException("El stock no puede ser menor a 0");

        String sql = "INSERT INTO productos (nombre, precio, stock, categoria_id, eliminado) VALUES (?, ?, ?, ?, false)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setLong(4, producto.getCategoria().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                producto.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void modificar(Producto producto) throws Exception {
        if (producto.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser menor a 0");

        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setLong(4, producto.getCategoria().getId());
            ps.setLong(5, producto.getId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // BAJA LÓGICA (Update en lugar de Delete físico)
        String sql = "UPDATE productos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws Exception {
        String sql = "SELECT p.*, c.nombre AS categoria_nombre FROM productos p "
                   + "JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ? AND p.eliminado = false";
        
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getLong("categoria_id"));
                c.setNombre(rs.getString("categoria_nombre"));
                
                Producto p = new Producto(rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"), c);
                p.setId(rs.getLong("id"));
                return p;
            }
        }
        return null;
    }
}

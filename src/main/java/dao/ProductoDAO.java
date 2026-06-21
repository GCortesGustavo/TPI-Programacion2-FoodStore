/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
 
import entities.Categoria;
import entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class ProductoDAO implements DAO<Producto> {
 
    private Connection conexion;
 
    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }
 /*
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id_categoria"));
        categoria.setNombre(rs.getString("categoria_nombre"));
 
        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setStock(rs.getInt("stock"));
        producto.setImagen(rs.getString("imagen"));
        producto.setDisponible(rs.getBoolean("disponible"));
        producto.setCategoria(categoria);
        producto.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
 
        return producto;
    }
 */
    
    @Override
    public List<Producto> listarTodos() throws SQLException {
 
        List<Producto> productos = new ArrayList<>();
 
        String sql =
                "SELECT p.*, c.nombre AS categoria_nombre " +
                "FROM producto p " +
                "INNER JOIN categoria c ON p.id_categoria = c.id " +
                "WHERE p.eliminado = false";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        }
 
        return productos;
    }
 
    public List<Producto> listarPorCategoria(Long idCategoria) throws SQLException {
 
        List<Producto> productos = new ArrayList<>();
 
        String sql =
                "SELECT p.*, c.nombre AS categoria_nombre " +
                "FROM producto p " +
                "INNER JOIN categoria c ON p.id_categoria = c.id " +
                "WHERE p.eliminado = false AND p.id_categoria = ?";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, idCategoria);
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        }
 
        return productos;
    }
 
    public Producto buscarPorId(Long id) throws SQLException {
 
        String sql =
                "SELECT p.*, c.nombre AS categoria_nombre " +
                "FROM producto p " +
                "INNER JOIN categoria c ON p.id_categoria = c.id " +
                "WHERE p.id = ? AND p.eliminado = false";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
 
        return null;
    }
 
    @Override
    public void modificar(Producto producto) throws SQLException {
 
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
 
        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser menor a 0");
        }
 
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser menor a 0");
        }
 
        if (producto.getCategoria() == null) {
            throw new IllegalArgumentException("El producto debe tener una categoría asociada");
        }
 
        String sql =
                "INSERT INTO producto " +
                "(nombre, precio, descripcion, stock, imagen, disponible, id_categoria, eliminado, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, false, NOW())";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.getDisponible() != null ? producto.getDisponible() : true);
            ps.setLong(7, producto.getCategoria().getId());
            //return ps.executeUpdate() > 0;
        }
    }
 
    @Override
    public void modificar(Producto producto) throws SQLException {
 
        String validar = "SELECT id FROM producto WHERE id = ? AND eliminado = false";
 
        try (PreparedStatement psValidar = conexion.prepareStatement(validar)) {
            psValidar.setLong(1, producto.getId());
 
            try (ResultSet rs = psValidar.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("No existe un producto activo con ese ID");
                }
            }
        }
 
        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser menor a 0");
        }
 
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser menor a 0");
        }
 
        String sql =
                "UPDATE producto " +
                "SET nombre = ?, precio = ?, descripcion = ?, stock = ?, " +
                "imagen = ?, disponible = ?, id_categoria = ? " +
                "WHERE id = ?";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getImagen());
            ps.setBoolean(6, producto.getDisponible() != null ? producto.getDisponible() : true);
            ps.setLong(7, producto.getCategoria().getId());
            ps.setLong(8, producto.getId());
            //return ps.executeUpdate() > 0;
        }
    }
 
    @Override
    public void eliminar(Long id) throws SQLException {
 
        String validar = "SELECT id FROM producto WHERE id = ? AND eliminado = false";
 
        try (PreparedStatement psValidar = conexion.prepareStatement(validar)) {
            psValidar.setLong(1, id);
 
            try (ResultSet rs = psValidar.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("No existe un producto activo con ese ID");
                }
            }
        }
 
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";
 
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            //return ps.executeUpdate() > 0;
        }
    }
}
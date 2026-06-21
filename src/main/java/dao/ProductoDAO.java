/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Categoria;
import entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements DAO<Producto> {

    public ProductoDAO() {
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id")); // Nombre de columna del SQL
        categoria.setNombre(rs.getString("categoria_nombre"));

        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setStock(rs.getInt("stock"));
        producto.setDisponible(rs.getBoolean("disponible"));
        producto.setCategoria(categoria);
        producto.setEliminado(rs.getBoolean("eliminado"));
        return producto;
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS categoria_nombre "
                + "FROM productos p INNER JOIN categorias c ON p.categoria_id = c.id "
                + "WHERE p.eliminado = false";

        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    @Override
    public void guardar(Producto p) throws Exception {
        String sql = "INSERT INTO productos (nombre, precio, descripcion, stock, categoria_id, disponible, eliminado) VALUES (?, ?, ?, ?, ?, ?, false)";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setLong(5, p.getCategoria().getId());
            ps.setBoolean(6, p.getDisponible() != null ? p.getDisponible() : true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void modificar(Producto p) throws Exception {
        String sql = "UPDATE productos SET nombre=?, precio=?, descripcion=?, stock=?, categoria_id=?, disponible=? WHERE id=?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setLong(5, p.getCategoria().getId());
            ps.setBoolean(6, p.getDisponible());
            ps.setLong(7, p.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE productos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws Exception {
        String sql = "SELECT p.*, c.nombre AS categoria_nombre FROM productos p "
                + "INNER JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ? AND p.eliminado = false";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapearProducto(rs) : null;
        }
    }
}

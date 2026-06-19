/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Usuario
 */
import entities.Categoria;
import entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conexion;

    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // LISTAR PRODUCTOS CON SU CATEGORÍA
    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql =
                "SELECT p.*, c.nombre AS categoria_nombre " +
                "FROM producto p " +
                "INNER JOIN categoria c ON p.id_categoria = c.id " +
                "WHERE p.eliminado = false";

        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Categoria categoria = new Categoria();
            categoria.setId(rs.getLong("id_categoria"));
            categoria.setNombre(rs.getString("categoria_nombre"));

            Producto producto = new Producto();

            producto.setId(rs.getLong("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setStock(rs.getInt("stock"));
            producto.setCategoria(categoria);

            productos.add(producto);
        }

        return productos;
    }

    // INSERTAR PRODUCTO
    public boolean insertar(Producto producto) throws SQLException {

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser menor a 0");
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser menor a 0");
        }

        String sql =
                "INSERT INTO producto " +
                "(nombre, precio, stock, id_categoria, eliminado) " +
                "VALUES (?, ?, ?, ?, false)";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setString(1, producto.getNombre());
        ps.setDouble(2, producto.getPrecio());
        ps.setInt(3, producto.getStock());
        ps.setLong(4, producto.getCategoria().getId());

        return ps.executeUpdate() > 0;
    }

    public boolean editar(Producto producto) throws SQLException {

        String validar =
                "SELECT id FROM producto " +
                "WHERE id = ? AND eliminado = false";

        PreparedStatement psValidar =
                conexion.prepareStatement(validar);

        psValidar.setLong(1, producto.getId());

        ResultSet rs = psValidar.executeQuery();

        if (!rs.next()) {
            throw new IllegalArgumentException(
                    "No existe un producto con ese ID");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser menor a 0");
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser menor a 0");
        }

        String sql =
                "UPDATE producto " +
                "SET nombre = ?, precio = ?, stock = ?, id_categoria = ? " +
                "WHERE id = ?";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setString(1, producto.getNombre());
        ps.setDouble(2, producto.getPrecio());
        ps.setInt(3, producto.getStock());
        ps.setLong(4, producto.getCategoria().getId());
        ps.setLong(5, producto.getId());

        return ps.executeUpdate() > 0;
    }

    // ELIMINAR (BAJA LÓGICA)
    public boolean eliminar(Long id) throws SQLException {

        String validar =
                "SELECT id FROM producto " +
                "WHERE id = ? AND eliminado = false";

        PreparedStatement psValidar =
                conexion.prepareStatement(validar);

        psValidar.setLong(1, id);

        ResultSet rs = psValidar.executeQuery();

        if (!rs.next()) {
            throw new IllegalArgumentException(
                    "No existe un producto con ese ID");
        }

        String sql =
                "UPDATE producto " +
                "SET eliminado = true " +
                "WHERE id = ?";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setLong(1, id);

        return ps.executeUpdate() > 0;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection conexion;

    public CategoriaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // LISTAR
    public List<Categoria> listar() throws SQLException {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categoria WHERE eliminado = false";

        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Categoria categoria = new Categoria();

            categoria.setId(rs.getLong("id"));
            categoria.setNombre(rs.getString("nombre"));
            categoria.setDescripcion(rs.getString("descripcion"));

            categorias.add(categoria);
        }

        return categorias;
    }

    // INSERTAR
    public boolean insertar(Categoria categoria) throws SQLException {

        if (categoria.getNombre() == null ||
            categoria.getNombre().trim().isEmpty()) {

            throw new IllegalArgumentException(
                "El nombre de la categoría no puede estar vacío");
        }

        String validar =
                "SELECT COUNT(*) FROM categoria " +
                "WHERE nombre = ? AND eliminado = false";

        PreparedStatement psValidar =
                conexion.prepareStatement(validar);

        psValidar.setString(1, categoria.getNombre());

        ResultSet rs = psValidar.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {

            throw new IllegalArgumentException(
                "Ya existe una categoría con ese nombre");
        }

        String sql =
                "INSERT INTO categoria " +
                "(nombre, descripcion, eliminado) " +
                "VALUES (?, ?, false)";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setString(1, categoria.getNombre());
        ps.setString(2, categoria.getDescripcion());

        return ps.executeUpdate() > 0;
    }

    // EDITAR
     public boolean editar(Categoria categoria) throws SQLException {

        String validar =
                "SELECT id FROM categoria " +
                "WHERE id = ? AND eliminado = false";

        PreparedStatement psValidar =
                conexion.prepareStatement(validar);

        psValidar.setLong(1, categoria.getId());

        ResultSet rs = psValidar.executeQuery();

        if (!rs.next()) {

            throw new IllegalArgumentException(
                "No existe una categoría con ese ID");
        }

        String sql =
                "UPDATE categoria " +
                "SET nombre = ?, descripcion = ? " +
                "WHERE id = ?";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setString(1, categoria.getNombre());
        ps.setString(2, categoria.getDescripcion());
        ps.setLong(3, categoria.getId());

        return ps.executeUpdate() > 0;
    }

    // ELIMINAR (BAJA LÓGICA)
    public boolean eliminar(Long id) throws SQLException {

        String validar =
                "SELECT id FROM categoria " +
                "WHERE id = ? AND eliminado = false";

        PreparedStatement psValidar =
                conexion.prepareStatement(validar);

        psValidar.setLong(1, id);

        ResultSet rs = psValidar.executeQuery();

        if (!rs.next()) {

            throw new IllegalArgumentException(
                "No existe una categoría con ese ID");
        }

        String sql =
                "UPDATE categoria " +
                "SET eliminado = true " +
                "WHERE id = ?";

        PreparedStatement ps =
                conexion.prepareStatement(sql);

        ps.setLong(1, id);

        return ps.executeUpdate() > 0;
    }
}

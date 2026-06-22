/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements DAO<Categoria> {


    @Override
    public List<Categoria> listarTodos() throws Exception {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE eliminado = false";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                categoria.setEliminado(rs.getBoolean("eliminado"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    @Override
    public void guardar(Categoria categoria) throws Exception {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }

        if (existeNombre(categoria.getNombre())) {
            throw new Exception("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }

        String sql = "INSERT INTO categorias (nombre, descripcion, eliminado) VALUES (?, ?, false)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void modificar(Categoria categoria) throws Exception {
        String sqlCheck = "SELECT id FROM categorias WHERE id = ? AND eliminado = false";
        String sqlUpdate = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";

        try (Connection con = ConexionDB.getConnection()) {
            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setLong(1, categoria.getId());
                if (!psCheck.executeQuery().next()) {
                    throw new Exception("No existe una categoría activa con el ID: " + categoria.getId());
                }
            }

            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                psUpdate.setString(1, categoria.getNombre());
                psUpdate.setString(2, categoria.getDescripcion());
                psUpdate.setLong(3, categoria.getId());
                psUpdate.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE categorias SET eliminado = true WHERE id = ?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new Exception("No se encontró la categoría con ID: " + id);
            }
        }
    }

    @Override
    public Categoria buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM categorias WHERE id = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion"));
            }
        }
        return null;
    }

    private boolean existeNombre(String nombre) throws Exception {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
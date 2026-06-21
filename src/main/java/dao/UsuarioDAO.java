/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Usuario;
import enums.EnumsRol;
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
public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public Usuario buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("mail"), rs.getString("celular"),
                        rs.getString("contrasenia"), EnumsRol.valueOf(rs.getString("rol")));
                u.setId(rs.getLong("id"));
                return u;
            }
        }
        return null;
    }

    // AGREGO ESTOS MÉTODOS VACÍOS PARA QUE EL CONTRATO SE CUMPLA Y NO HAYA ERROR ROJO
    @Override
    public void guardar(Usuario t) throws Exception {
        String sql = "INSERT INTO usuarios (nombre, apellido, mail, celular, contrasenia, rol, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getEmail());
            ps.setString(4, t.getCelular());
            ps.setString(5, t.getContrasenia());
            ps.setString(6, t.getRol().name());
            ps.setBoolean(7, false);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void modificar(Usuario t) throws Exception {
        String sql = """
            UPDATE usuarios
            SET nombre = ?,
                apellido = ?,
                mail = ?,
                celular = ?,
                contrasenia = ?, 
                rol = ?
            WHERE id = ?
            """;

        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getApellido());
            ps.setString(3, t.getEmail());
            ps.setString(4, t.getCelular());
            ps.setString(5, t.getContrasenia());
            ps.setString(6, t.getRol().name());
            ps.setLong(7, t.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = """
            UPDATE usuarios
            SET eliminado = true
            WHERE id = ?
            """;

        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        }
    }

    @Override
    public List<Usuario> listarTodos() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE eliminado = false";

        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("mail"));
                u.setCelular(rs.getString("celular"));
                u.setRol(EnumsRol.valueOf(rs.getString("rol")));
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    public boolean emailExistente(String email) throws Exception {
        String sql = "SELECT id FROM usuarios WHERE mail = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}

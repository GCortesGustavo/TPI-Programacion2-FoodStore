/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Producto;
import entities.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        try (Connection con = config.ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getString("nombre"), rs.getString("mail"), enums.EnumsRol.valueOf(rs.getString("rol")));
                u.setId(rs.getLong("id"));
                return u;
            }
        }
        return null; 
    }

    // AGREGO ESTOS MÉTODOS VACÍOS PARA QUE EL CONTRATO SE CUMPLA Y NO HAYA ERROR ROJO
    @Override
    public void guardar(Usuario t) throws Exception {
        // A completar 
    }

    @Override
    public void modificar(Usuario t) throws Exception {
        // A completar 
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // A completar
    }

    @Override
    public List<Usuario> listarTodos() throws Exception {
        // A completar
        return new ArrayList<>();
    }
}

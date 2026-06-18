/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Pedido;
import entities.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PedidoDAO {

    public Long guardar(Pedido pedido, Connection con) throws SQLException {
        String sql = "INSERT INTO pedidos (fecha, estado, total, forma_pago, usuario_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(pedido.getFecha()));
            ps.setString(2, pedido.getEstado().name());
            ps.setDouble(3, pedido.getTotal());
            ps.setString(4, pedido.getFormaPago().name());
            ps.setLong(5, pedido.getUsuario().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return null;
    }

    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as nombre_usuario FROM pedidos p "
                + "JOIN usuarios u ON p.usuario_id = u.id "
                + "WHERE p.eliminado = false";

        try (Connection con = ConexionDB.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                // Creamos un usuario temporal para el pedido
                Usuario u = new Usuario(rs.getString("nombre_usuario"), "", enums.EnumsRol.CLIENTE);
                u.setId(rs.getLong("usuario_id"));

                Pedido p = new Pedido(u, enums.EnumsFormaDePago.valueOf(rs.getString("forma_pago")));
                p.setId(rs.getLong("id"));
                p.setEstado(enums.EnumsEstado.valueOf(rs.getString("estado")));
                p.setEliminado(rs.getBoolean("eliminado"));

                lista.add(p);
            }
        }
        return lista;
    }

    public void actualizarEstado(Long id, enums.EnumsEstado nuevoEstado) throws SQLException {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    public void eliminar(Long id) throws SQLException {
        String sql = "UPDATE pedidos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.ConexionDB;
import entities.Pedido;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void eliminar(Long id) throws SQLException {
        String sql = "UPDATE pedidos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}

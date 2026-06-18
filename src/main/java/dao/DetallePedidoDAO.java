/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.DetallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class DetallePedidoDAO {

    public void guardar(DetallePedido detalle, Long pedidoId, Connection con) throws SQLException {
        String sql = "INSERT INTO detalles_pedidos (cantidad, subtotal, pedido_id, producto_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getSubtotal());
            ps.setLong(3, pedidoId);
            ps.setLong(4, detalle.getProducto().getId());

            ps.executeUpdate();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Calculable;
import config.ConexionDB;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
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

    public List<Pedido> listarTodos() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as nombre_usuario FROM pedidos p "
                + "JOIN usuarios u ON p.usuario_id = u.id "
                + "WHERE p.eliminado = false";

        try (Connection con = ConexionDB.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(rs.getString("nombre_usuario"), "", enums.EnumsRol.CLIENTE);
                u.setId(rs.getLong("usuario_id"));

                Pedido p = new Pedido(u, enums.EnumsFormaDePago.valueOf(rs.getString("forma_pago")));
                p.setId(rs.getLong("id"));
                p.setEstado(enums.EnumsEstado.valueOf(rs.getString("estado")));

                cargarDetallesAlPedido(p, con);

                lista.add(p);
            }
        }
        return lista;
    }

    public void actualizarEstado(Long id, enums.EnumsEstado nuevoEstado) throws Exception {
        String sql = "UPDATE pedidos SET estado = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE pedidos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private void cargarDetallesAlPedido(Pedido p, Connection con) throws SQLException {
        String sql = "SELECT dp.*, prod.nombre as prod_nombre, prod.precio as prod_precio "
                + "FROM detalles_pedidos dp "
                + "JOIN productos prod ON dp.producto_id = prod.id "
                + "WHERE dp.pedido_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, p.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // 1. Creamos el producto y le cargamos TODO lo necesario para el cálculo
                Producto prod = new Producto();
                prod.setId(rs.getLong("producto_id"));
                prod.setNombre(rs.getString("prod_nombre"));

                prod.setPrecio(rs.getDouble("prod_precio"));

                // 2. Creamos el detalle pasándole el producto ya con su precio cargado
                DetallePedido dp = new DetallePedido(prod, rs.getInt("cantidad"));

                // Seteamos el ID del detalle
                dp.setId(rs.getLong("id"));

                // 3. Lo agregamos al pedido
                p.addDetallePedido(dp);
            }
        }
    }
}

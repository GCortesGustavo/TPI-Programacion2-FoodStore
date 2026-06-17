package services;

import config.ConexionDB;
import dao.DetallePedidoDAO;
import dao.PedidoDAO;
import entities.DetallePedido;
import entities.Pedido;
import java.sql.Connection;

/**
 *
 * @author Usuario
 */
public class PedidoService {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

    public void registrarPedidoCompleto(Pedido pedido) throws Exception {
        Connection con = null;
        try {
            con = ConexionDB.getConnection();
            con.setAutoCommit(false);

            Long pedidoId = pedidoDAO.guardar(pedido, con);

            for (DetallePedido detalle : pedido.getDetalles()) {
                if (detalle.getProducto().getStock() < detalle.getCantidad()) {
                    throw new Exception("Stock insuficiente para: " + detalle.getProducto().getNombre());
                }

                detalleDAO.guardar(detalle, pedidoId, con);

            }

            con.commit();
            System.out.println("Pedido registrado con éxito.");

        } catch (Exception error) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception("Error en el pedido. Se canceló la operación: " + error.getMessage());
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
            }
        }
    }
}

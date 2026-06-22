/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.EnumsEstado;
import enums.EnumsFormaDePago;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author Villalba - Cortés - Lorenzo - Flores
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Usuario usuario;
    private List<DetallePedido> detalles = new ArrayList<>();
    private EnumsEstado estado;
    private EnumsFormaDePago formaPago;
    private double total;

    public Pedido(Usuario usuario, EnumsFormaDePago formaPago) {
        super();
        this.fecha = LocalDate.now();
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.estado = EnumsEstado.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public void addDetallePedido(int cantidad, Double subtotal, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(producto, cantidad);
        this.detalles.add(nuevoDetalle);
        this.total = calcularTotal();
    }

    public void addDetallePedido(DetallePedido detalle) {
        this.detalles.add(detalle);
        this.total = calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido aEliminar = findDetallePedidoByProducto(producto);
        if (aEliminar != null) {
            this.detalles.remove(aEliminar);
            this.total = calcularTotal();
        }
    }

    //=================== GETTERS Y SETTERS=======================
    public double getTotal() {
        double total = 0;
        for (DetallePedido dp : detalles) {
            total += dp.getSubtotal();
        }
        return total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public EnumsEstado getEstado() {
        return estado;
    }

    public void setEstado(EnumsEstado estado) {
        this.estado = estado;
    }

    public EnumsFormaDePago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(EnumsFormaDePago formaPago) {
        this.formaPago = formaPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate toLocalDate) {
        this.fecha = toLocalDate;
    }

    //===================TERMINAN LOS GETTERS Y SETTERS=======================
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n╔══════════════════════════════════════════════════════════╗");
        sb.append(String.format("\n║ PEDIDO #%-5d                                         ║", id));
        sb.append(String.format("\n║ Fecha: %-10s | Cliente: %-25s ║", fecha, usuario.getNombre()));
        sb.append(String.format("\n║ Estado: %-10s | Pago: %-28s ║", estado, formaPago));
        sb.append("\n╠══════════════════════════════════════════════════════════╣");
        sb.append("\n║ CANT. | PRODUCTO                | PRECIO U. | SUBTOTAL   ║");
        sb.append("\n╟───────┼─────────────────────────┼───────────┼────────────╢");

        if (detalles.isEmpty()) {
            sb.append("\n║          (No hay productos cargados en este pedido)      ║");
        } else {
            for (DetallePedido dp : detalles) {
                sb.append(String.format("\n║ %-5d | %-23.23s | %9.2f | %10.2f ║",
                        dp.getCantidad(),
                        dp.getProducto().getNombre(),
                        dp.getPrecioUnitario(),
                        dp.getSubtotal()));
            }
        }

        sb.append("\n╠═══════╧═════════════════════════╧═══════════╧════════════╣");
        sb.append(String.format("\n║ TOTAL A PAGAR:                             $ %11.2f ║", getTotal()));
        sb.append("\n╚══════════════════════════════════════════════════════════╝\n");

        return sb.toString();
    }

    @Override
    public double calcularTotal() {
        double acumulado = 0;
        for (DetallePedido dp : detalles) {
            acumulado += dp.getSubtotal();
        }
        return acumulado;
    }
}

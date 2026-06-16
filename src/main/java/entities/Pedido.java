/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.EnumsEstado;
import enums.EnumsFormaDePago;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class Pedido extends Base implements Calculable {

    private Usuario usuario;
    private List<DetallePedido> detalles = new ArrayList<>();
    private EnumsEstado estado;
    private EnumsFormaDePago formaPago;

    public Pedido(Usuario usuario, EnumsFormaDePago formaPago) {
        super();
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.estado = EnumsEstado.PENDIENTE;
    }

    public void agregarDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
    }

    public double getTotal() {
        double total = 0;
        for (DetallePedido dp : detalles) {
            total += dp.getSubtotal();
        }
        return total;
    }

    //=================== GETTERS Y SETTERS=======================
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

    //===================TERMINAN LOS GETTERS Y SETTERS=======================
    @Override
    public String toString() {
        return "Pedido #" + id + " | Usuario: " + usuario.getNombre() + " | Estado: " + estado + " | Total: $" + getTotal();
    }

    @Override
    public double calcularTotal() {
        return detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }
}

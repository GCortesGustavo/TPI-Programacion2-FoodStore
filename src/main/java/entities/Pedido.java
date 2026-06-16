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
 * @author Santiago Villalba
 */
public class Pedido {
    private int id;
    private Usuario usuario;
    private List<DetallePedido> detalles;
    private EnumsEstado estado;
    private EnumsFormaDePago formaPago;
    private boolean eliminado;

    public Pedido(int id, Usuario usuario, EnumsFormaDePago formaPago) {
        this.id = id;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.estado = EnumsEstado.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.eliminado = false;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    //===================TERMINAN LOS GETTERS Y SETTERS=======================


    @Override
    public String toString() {
        return "Pedido #" + id + " | Usuario: " + usuario.getNombre() + " | Estado: " + estado + " | Total: $" + getTotal();
    }
}

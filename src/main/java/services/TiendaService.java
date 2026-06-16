/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import dao.UsuarioDAO;
import entities.*;
import enums.*;
import exception.ExceptionsMenu;
        
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public class TiendaService {
    private UsuarioDAO usuarioDao = new usuarioDAO();

    //========== METO CONTADORES PARA INCREMENTAR LAS ID =========================
    private int catId = 1, prodId = 1, userId = 1, pedId = 1;

    //========== TIRA ERROR SI EL MAIL ES IGUAL AL QUE YA ESTA REGISTRADO ========
    public void crearUsuario(String nombre, String email, EnumsRol rol) throws ExceptionsMenu {
        // 1. Lógica de negocio: Validar si el mail ya existe en la DB
        if (usuarioDAO.buscarPorEmail(email) != null) {
            throw new ExceptionsMenu("Error: El email '" + email + "' ya está registrado.");
        }
        
        // 2. Crear el objeto y mandarlo al DAO para que lo guarde en MySQL
        Usuario nuevo = new Usuario(nombre, email, rol);
        usuarioDAO.guardar(nuevo);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) activos.add(u);
        }
        return activos;
    }

    public Usuario buscarUsuario(int id) throws ExceptionsMenu {
        for (Usuario u : usuarios) {
            if (u.getId() == id && !u.isEliminado()) return u;
        }
        throw new ExceptionsMenu("Usuario no encontrado.");
    }

    public void eliminarUsuario(long id) throws ExceptionsMenu {
        Usuario u = buscarUsuario(id);
        u.setEliminado(true); // Soft delete
    }

    //========== ACA VA A TIRAR ERROR SI EL PRECIO ES NEGATIVO ========
    public void crearProducto(String nombre, double precio, int stock, int idCategoria) throws ExceptionsMenu {
        if (precio < 0) throw new ExceptionsMenu("Error: El precio no puede ser negativo.");
        if (stock < 0) throw new ExceptionsMenu("Error: El stock no puede ser negativo.");
        
        Categoria cat = buscarCategoria(idCategoria);
        productos.add(new Producto(prodId++, nombre, precio, stock, cat));
    }

    public List<Producto> listarProductos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) activos.add(p);
        }
        return activos;
    }

    //========== ACA VA TIENEN LA PARTE DE CATEGORIAS, LAS CREAN, LAS LISTAN Y SI NO LAS ESCUENTRAN LES VA A TIRAR ERROR ========
    public void crearCategoria(String nombre) {
        categorias.add(new Categoria(catId++, nombre));
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) activas.add(c);
        }
        return activas;
    }

    public Categoria buscarCategoria(int id) throws ExceptionsMenu {
        for (Categoria c : categorias) {
            if (c.getId() == id && !c.isEliminado()) return c;
        }
        throw new ExceptionsMenu("Categoría no encontrada.");
    }

    //========== aCA LES VA A TIRAR ERROR SI EL PEDIDO NO TIENE UN USUARIO ========
    public Pedido crearPedido(int idUsuario, EnumsFormaDePago formaPago) throws ExceptionsMenu {
        Usuario u = buscarUsuario(idUsuario);
        if (u == null) throw new ExceptionsMenu("Error: No se puede crear un pedido sin usuario.");
        
        Pedido nuevo = new Pedido(pedId++, u, formaPago);
        pedidos.add(nuevo);
        return nuevo;
    }

    public void agregarDetalleAPedido(Pedido pedido, Producto producto, int cantidad) throws ExceptionsMenu {
        if (cantidad <= 0) throw new ExceptionsMenu("Error: La cantidad debe ser mayor a 0.");
        if (producto.getStock() < cantidad) throw new ExceptionsMenu("Error: Stock insuficiente.");
        
        
        producto.setStock(producto.getStock() - cantidad);
        pedido.agregarDetalle(new DetallePedido(producto, cantidad));
    }

    public void eliminarCategoria(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

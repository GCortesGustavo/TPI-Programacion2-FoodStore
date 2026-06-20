/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.UsuarioDAO;
import entities.Usuario;
import enums.EnumsRol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class UsuarioService {

    private UsuarioDAO dao = new UsuarioDAO();

    public void crearUsuario(String nombre, String email, EnumsRol rol) throws Exception {
        // TODO: 1. Validar que el email sea único en la base de datos
        // TODO: 2. Crear objeto Usuario y llamar al dao.guardar()
        System.out.println("Lógica de creación de usuario pendiente...");
    }

    public List<Usuario> listarUsuarios() {
        try {
            // TODO: Llamar al dao.listarTodos()
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void eliminarUsuario(Long id) throws Exception {
        // TODO: Llamar al dao.eliminar(id)
    }

    public Usuario buscarUsuarioPorId(Long id) throws Exception {
        return dao.buscarPorId(id);
    }

    // TODO: Implementar lógica para el PerfilDetalle (Relación 1:1)
}

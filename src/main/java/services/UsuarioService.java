/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.foodstore.Main;
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

    public void crearUsuario(String nombre, String apellido, String email, String celular, String contrasena, EnumsRol rol) throws Exception {
        if (email == null || email.isBlank()) {
            throw new Exception("El email no puede estar vacío.");
        }

        if (!email.contains("@")) {
            throw new Exception("Formato de email no válido.");
        }

        if (dao.emailExistente(email)) {
            throw new Exception("El email '" + email + "' ya está registrado.");
        }

        Usuario nuevo = new Usuario(nombre, apellido, email, celular, contrasena, rol);
        dao.guardar(nuevo);
    }

    public List<Usuario> listarUsuarios() throws Exception {
        return dao.listarTodos();
    }

    public void eliminarUsuario(Long id) throws Exception {
        if (dao.buscarPorId(id) == null) {
            throw new Exception("No existe ningún usuario con ese ID.");
        }
        dao.eliminar(id);
    }

    public Usuario buscarUsuarioPorId(Long id) throws Exception {
        Usuario u = dao.buscarPorId(id);
        if (u == null) {
            throw new Exception("Usuario no encontrado.");
        }
        return u;
    }

    public void actualizarUsuario(Usuario u) throws Exception {
        // Aquí podrías validar que el nuevo email no esté duplicado si cambió
        dao.modificar(u);
    }

    // TODO: Implementar lógica para el PerfilDetalle (Relación 1:1)
}

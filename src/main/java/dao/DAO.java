/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Villalba - Cortés - Lorenzo Flores
 */
public interface DAO<T> {

    void guardar(T t) throws Exception;

    void modificar(T t) throws Exception;

    void eliminar(Long id) throws Exception; // Soft Delete

    List<T> listarTodos() throws Exception;

    T buscarPorId(Long id) throws Exception;
}

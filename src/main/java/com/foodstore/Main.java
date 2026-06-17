/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.foodstore;

import services.TiendaService;
import config.ConexionDB;
import java.util.Scanner;
import enums.EnumsRol;
import exception.ExceptionsMenu;
import entities.*;
import java.sql.SQLException;

/**
 *
 * @author Villalba, Cortés, Lorenzo , Flores
 */
public class Main {
   private static final Scanner scanner = new Scanner(System.in);
    private static final TiendaService service = new TiendaService();

    public static void main(String[] args) {
        // 1. Intento de conexión inicial
        try {
            ConexionDB.getConnection();
            System.out.println("✅ FoodStore iniciado correctamente.");
        } catch (SQLException error) {
            System.out.println("❌ Error de conexión: " + error.getMessage());
        }

        // 2. Bucle principal del menú
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");

            opcion = leerEntero("Seleccione: ");

            if (opcion == 1) {
                menuCategorias();
            } else if (opcion == 2) {
                menuProductos();
            } else if (opcion == 3) {
                menuUsuarios();
            } else if (opcion == 4) {
                System.out.println("Menú Pedidos (Lógica de Jorge/PM)");
            } else if (opcion == 0) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuCategorias() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar | 2. Crear | 3. Editar | 4. Eliminar | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    for (Categoria c : service.listarCategorias()) {
                        System.out.println(c.toString());
                    }
                } else if (opcion == 2) {
                    String nom = leerString("Nombre de la categoría: ");
                    service.crearCategoria(nom);
                    System.out.println("¡Categoría creada con éxito!");
                } else if (opcion == 4) {
                    Long id = leerLong("Ingrese ID de categoría a eliminar: ");
                    service.eliminarCategoria(id);
                    System.out.println("¡Categoría eliminada!");
                } else if (opcion == 0) {
                    // Volver
                } else {
                    System.out.println("Opción incorrecta.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar | 2. Crear | 3. Editar | 4. Eliminar | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    for (Usuario u : service.listarUsuarios()) {
                        System.out.println(u.toString());
                    }
                } else if (opcion == 2) {
                    String nom = leerString("Nombre: ");
                    String email = leerString("Email: ");
                    service.crearUsuario(nom, email, EnumsRol.CLIENTE);
                    System.out.println("¡Usuario creado con éxito!");
                } else if (opcion == 4) {
                    Long id = leerLong("ID de usuario a eliminar: ");
                    service.eliminarUsuario(id);
                    System.out.println("¡Usuario eliminado (Soft Delete)!");
                }
            } catch (ExceptionsMenu e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void menuProductos() {
        System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
        System.out.println("(A completar basándose en los anteriores)");
    }

    // --- MÉTODOS DE ENTRADA DE DATOS ---

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número entero válido.");
            }
        }
    }

    private static Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un ID numérico válido.");
            }
        }
    }

    private static String leerString(String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
        }
        return texto;
    }
}

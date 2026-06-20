/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.foodstore;

import config.ConexionDB;
import java.util.Scanner;
import enums.EnumsRol;
import exception.ExceptionsMenu;
import entities.*;

/**
 *
 * @author Villalba, Cortés, Lorenzo , Flores
 */
public class Main {

    private static final services.PedidoService pedidoService = new services.PedidoService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final services.CategoriaService catService = new services.CategoriaService();
    private static final services.ProductoService prodService = new services.ProductoService();
    private static final services.UsuarioService userService = new services.UsuarioService();

    public static void main(String[] args) {
        try {
            ConexionDB.getConnection();
            System.out.println("FoodStore iniciado correctamente.");
        } catch (Exception error) {
            System.out.println("Error de conexión: " + error.getMessage());
        }

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
                menuPedidos();
            } else if (opcion == 0) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private static void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar Pedidos | 2. Cambiar Estado | 3. Eliminar (Baja Lógica) | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    System.out.println("\n--- LISTADO DE PEDIDOS ACTIVOS ---");
                    for (Pedido p : pedidoService.obtenerTodosLosPedidos()) {
                        System.out.println(p.toString());
                    }
                } else if (opcion == 2) {
                    Long id = leerLong("Ingrese ID del pedido a modificar: ");
                    System.out.println("Estados: 1. PENDIENTE | 2. PREPARACION | 3. ENTREGADO | 4. CANCELADO");
                    int est = leerEntero("Seleccione nuevo estado: ");

                    enums.EnumsEstado nuevoEstado = enums.EnumsEstado.PENDIENTE;
                    if (est == 1) {
                        nuevoEstado = enums.EnumsEstado.PENDIENTE;
                    }
                    if (est == 2) {
                        nuevoEstado = enums.EnumsEstado.PREPARACION;
                    }
                    if (est == 3) {
                        nuevoEstado = enums.EnumsEstado.ENTREGADO;
                    }
                    if (est == 4) {
                        nuevoEstado = enums.EnumsEstado.CANCELADO;
                    }

                    pedidoService.cambiarEstadoPedido(id, nuevoEstado);
                    System.out.println("Estado actualizado.");

                } else if (opcion == 3) {
                    Long id = leerLong("Ingrese ID del pedido a eliminar: ");
                    pedidoService.darDeBajaPedido(id);
                    System.out.println("Pedido eliminado (Baja Lógica).");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
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
                    for (Categoria c : catService.listarCategorias()) {
                        System.out.println(c.toString());
                    }
                } else if (opcion == 2) {
                    String nom = leerString("Nombre de la categoría: ");
                    String desc = leerString("Descripción de la categoría: ");
                    catService.crearCategoria(nom, desc);
                    System.out.println("¡Categoría creada con éxito!");
                } else if (opcion == 4) {
                    Long id = leerLong("Ingrese ID de categoría a eliminar: ");
                    catService.eliminarCategoria(id);
                    System.out.println("¡Categoría eliminada!");
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
                    for (Usuario u : userService.listarUsuarios()) {
                        System.out.println(u.toString());
                    }
                } else if (opcion == 2) {
                    String nom = leerString("Nombre: ");
                    String email = leerString("Email: ");
                    userService.crearUsuario(nom, email, enums.EnumsRol.CLIENTE);
                    System.out.println("¡Usuario creado con éxito!");
                } else if (opcion == 4) {
                    Long id = leerLong("ID de usuario a eliminar: ");
                    userService.eliminarUsuario(id);
                    System.out.println("¡Usuario eliminado (Soft Delete)!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar | 2. Crear | 3. Editar | 4. Eliminar | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    System.out.println("\n--- LISTADO DE PRODUCTOS ---");
  
                    for (Producto p : prodService.listarProductos()) {
                        System.out.println(p.toString());
                    }
                } else if (opcion == 2) {
                    System.out.println("\n--- NUEVO PRODUCTO ---");
                    String nom = leerString("Nombre del producto: ");
                    String desc = leerString("Descripción: ");
                    Double precio = leerDouble("Precio: ");
                    Long idCategoria = leerLong("ID de la Categoría: ");

                    //prodService.crearProducto(nom, desc, precio, idCategoria);  CHEEE ACA FALTAN LOS METODOS DE LA CLASE PRODUCTO
                    //System.out.println("¡Producto creado con éxito!");
                    
                } else if (opcion == 3) {
                    System.out.println("\n--- EDITAR PRODUCTO ---");
                    Long id = leerLong("Ingrese ID del producto a editar: ");
                    String nom = leerString("Nuevo nombre: ");
                    String desc = leerString("Nueva descripción: ");
                    Double precio = leerDouble("Nuevo precio: ");
                    Long idCategoria = leerLong("Nuevo ID de Categoría: ");
                    
                    //prodService.editarProducto(id, nom, desc, precio, idCategoria); ACA TAMBIEN FALTAN LOS METODOS DE LA CLASE PRODUCTO
                    //System.out.println("¡Producto editado con éxito!");
                    
                } else if (opcion == 4) {
                    System.out.println("\n--- ELIMINAR PRODUCTO ---");
                    Long id = leerLong("Ingrese ID de producto a eliminar: ");
                    
                    prodService.eliminarProducto(id);
                    System.out.println("¡Producto eliminado!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private static Double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número decimal válido (ej: 1500.50).");
            }
        }
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

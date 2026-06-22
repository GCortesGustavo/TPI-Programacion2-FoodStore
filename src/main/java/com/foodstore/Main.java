/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.foodstore;

import config.ConexionDB;
import java.util.Scanner;
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

            System.out.println("1. Crear Nuevo Pedido | 2. Listar Pedidos | 3. Cambiar Estado | 4. Eliminar | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    crearNuevoPedido();
                } else if (opcion == 2) {
                    System.out.println("\n--- LISTADO DE PEDIDOS ACTIVOS ---");
                    for (Pedido p : pedidoService.obtenerTodosLosPedidos()) {
                        System.out.println(p.toString());
                    }
                } else if (opcion == 3) {
                    Long id = leerLong("ID del pedido: ");
                    System.out.println("1. PENDIENTE | 2. PREPARACION | 3. ENTREGADO | 4. CANCELADO");
                    int est = leerEntero("Estado: ");
                    enums.EnumsEstado nuevo = (est == 1) ? enums.EnumsEstado.PENDIENTE : (est == 2) ? enums.EnumsEstado.PREPARACION : (est == 3) ? enums.EnumsEstado.ENTREGADO : enums.EnumsEstado.CANCELADO;
                    pedidoService.cambiarEstadoPedido(id, nuevo);
                    System.out.println("Estado actualizado.");
                } else if (opcion == 4) {
                    Long id = leerLong("ID del pedido a eliminar: ");
                    pedidoService.darDeBajaPedido(id);
                    System.out.println("Pedido eliminado.");
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
                } else if (opcion == 3) {
                    Long id = leerLong("ID de la categoría a editar: ");
                    String nuevoNom = leerString("Nuevo nombre: ");
                    String nuevaDesc = leerString("Nueva descripción: ");
                    catService.modificarCategoria(id, nuevoNom, nuevaDesc);
                    System.out.println("Categoría actualizada.");
                } else if (opcion == 4) {
                    Long id = leerLong("Ingrese ID de categoría a eliminar: ");
                    catService.eliminarCategoria(id);
                    System.out.println("Categoría eliminada!");
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
                    String apellido = leerString("Apellido: ");
                    String email = leerString("Email: ");
                    String celular = leerString("Celular: ");
                    String contasena = leerString("Contraseña: ");
                    userService.crearUsuario(nom, apellido, email, celular, contasena, enums.EnumsRol.CLIENTE);
                    System.out.println("¡Usuario creado con éxito!");
                } else if (opcion == 3) {
                    Long id = leerLong("ID del usuario a editar: ");
                    Usuario uExistente = userService.buscarUsuarioPorId(id);

                    if (uExistente != null) {
                        System.out.println("Modificando a: " + uExistente.getNombre());
                        String nom = leerString("Nuevo Nombre: ");
                        String ape = leerString("Nuevo Apellido: ");
                        String mail = leerString("Nuevo Email: ");
                        String cel = leerString("Nuevo Celular: ");
                        String pass = leerString("Nueva Contraseña: ");

                        uExistente.setNombre(nom);
                        uExistente.setApellido(ape);
                        uExistente.setEmail(mail);
                        uExistente.setCelular(cel);
                        uExistente.setContrasenia(pass);

                        userService.actualizarUsuario(uExistente);
                        System.out.println("Usuario actualizado con éxito.");
                    }
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
            System.out.println("1. Listar | 2. Crear | 3.Editar | 4. Eliminar | 0. Volver");
            opcion = leerEntero("Seleccione: ");

            try {
                if (opcion == 1) {
                    for (Producto p : prodService.listarProductos()) {
                        System.out.println(p.toString());
                    }
                } else if (opcion == 2) {
                    String nom = leerString("Nombre: ");
                    double precio = (double) leerLong("Precio: ");
                    int stock = leerEntero("Stock: ");
                    String desc = leerString("Descripción: ");
                    Long idCat = leerLong("ID de Categoría: ");

                    Categoria cat = catService.buscarCategoriaPorId(idCat);
                    if (cat != null) {
                        prodService.crearProducto(nom, precio, stock, cat, desc);
                        System.out.println("Producto creado.");
                    } else {
                        System.out.println("Categoría no encontrada.");
                    }
                } else if (opcion == 3) {
                    Long id = leerLong("ID del producto a editar: ");
                    Producto p = prodService.buscarProductoPorId(id);

                    if (p != null) {
                        System.out.println("Editando: " + p.getNombre());
                        p.setNombre(leerString("Nuevo nombre: "));
                        p.setPrecio((double) leerLong("Nuevo precio: "));
                        p.setStock(leerEntero("Nuevo stock: "));
                        p.setDescripcion(leerString("Nueva descripción: "));

                        Long idCat = leerLong("Nuevo ID de Categoría: ");
                        Categoria cat = catService.buscarCategoriaPorId(idCat);
                        if (cat != null) {
                            p.setCategoria(cat);
                            prodService.modificarProducto(p);
                            System.out.println("Producto actualizado correctamente.");
                        }
                    }
                } else if (opcion == 4) {
                    Long id = leerLong("ID a eliminar: ");
                    prodService.eliminarProducto(id);
                    System.out.println("Producto eliminado.");
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

    private static void crearNuevoPedido() throws Exception {
        System.out.println("\n--- NUEVO PEDIDO ---");

        // OPCIÓN A: Pedir por teclado (Si el usuario ID 1 existe en tu DB, funcionará)
        Long idUser = leerLong("Ingrese ID del Usuario (Usa el 1 para probar): ");
        Usuario user = userService.buscarUsuarioPorId(idUser);

        /* 
    // OPCIÓN B: Simulacro total (Si no querés ni buscarlo en la DB)
    Usuario user = new Usuario("Usuario Prueba", "test@mail.com", EnumsRol.CLIENTE);
    user.setId(1L); // Ojo: Este ID 1 DEBE existir en tu tabla 'usuarios' de MySQL
         */
        if (user == null) {
            System.out.println("Usuario no encontrado. Asegurate de correr el script SQL de datos de prueba.");
            return;
        }

        System.out.println("Forma de pago: 1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA");
        int fp = leerEntero("Seleccione: ");
        enums.EnumsFormaDePago forma = (fp == 1) ? enums.EnumsFormaDePago.EFECTIVO : (fp == 2) ? enums.EnumsFormaDePago.TARJETA : enums.EnumsFormaDePago.TRANSFERENCIA;

        Pedido nuevoPedido = new Pedido(user, forma);

        boolean agregando = true;
        while (agregando) {
            System.out.println("\n--- AGREGAR PRODUCTO AL PEDIDO ---");
            Long idProd = leerLong("Ingrese ID del Producto: ");
            Producto prod = prodService.buscarProductoPorId(idProd);

            if (prod != null) {
                int cant = leerEntero("Cantidad: ");
                nuevoPedido.addDetallePedido(cant, prod.getPrecio() * cant, prod);
                System.out.println("Producto agregado al carrito.");
            } else {
                System.out.println("Producto no encontrado.");
            }

            String seguir = leerString("¿Desea agregar otro producto? (S/N): ");
            if (seguir.equalsIgnoreCase("N")) {
                agregando = false;
            }
        }

        System.out.println("Guardando pedido en la base de datos...");
        pedidoService.registrarPedidoCompleto(nuevoPedido);
    }
}

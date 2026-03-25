package Vista;

import Controlador.Controlador;
import Modelo.Pedido;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Instanciamos el controlador que maneja toda la lógica
        Controlador controlador = new Controlador();
        int opcion = -1;

        System.out.println("==========================================");
        System.out.println(" BIENVENIDO A LA TIENDA ONLINE ");
        System.out.println("==========================================");

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Añadir Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("3. Añadir Cliente");
            System.out.println("4. Mostrar Clientes");
            System.out.println("5. Mostrar Clientes Estándar");
            System.out.println("6. Mostrar Clientes Premium");
            System.out.println("--------------------------------");
            System.out.println("7. Añadir Pedido");
            System.out.println("8. Eliminar Pedido");
            System.out.println("9. Mostrar Pedidos Pendientes");
            System.out.println("10. Mostrar Pedidos Enviados");
            System.out.println("--------------------------------");
            System.out.println("0. Salir");
            System.out.print("👉 Elige una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, introduce un número válido.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                // ==========================================
                // ZONA DE TUS COMPAÑEROS (ARTÍCULOS Y CLIENTES)
                // ==========================================
                case 1:
                    System.out.println("\n--- AÑADIR ARTÍCULO ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Artículos)");
                    // controlador.añadirArticulo(...);
                    break;
                case 2:
                    System.out.println("\n--- MOSTRAR ARTÍCULOS ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Artículos)");
                    break;
                case 3:
                    System.out.println("\n--- AÑADIR CLIENTE ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Clientes)");
                    break;
                case 4:
                    System.out.println("\n--- MOSTRAR CLIENTES ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Clientes)");
                    break;
                case 5:
                    System.out.println("\n--- MOSTRAR CLIENTES ESTÁNDAR ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Clientes)");
                    break;
                case 6:
                    System.out.println("\n--- MOSTRAR CLIENTES PREMIUM ---");
                    System.out.println("⏳ (Aquí va el código de tu compañero para Clientes)");
                    break;

                // ==========================================
                // PEDIDOS
                // ==========================================
                case 7:
                    System.out.println("\n--- AÑADIR PEDIDO ---");
                    try {
                        System.out.print("ID del Cliente: ");
                        int idCliente = Integer.parseInt(teclado.nextLine());

                        // 1. COMPROBAR SI EL CLIENTE EXISTE
                        if (!controlador.existeCliente(idCliente)) {
                            System.out.println("El cliente con ID " + idCliente + " no existe.");
                            System.out.println("Vamos a registrarlo en este momento.");

                            // Pedimos los datos para crear al cliente (basado en vuestra BD)
                            System.out.print("Email: ");
                            String email = teclado.nextLine();
                            System.out.print("Nombre: ");
                            String nombre = teclado.nextLine();
                            System.out.print("Domicilio: ");
                            String domicilio = teclado.nextLine();
                            System.out.print("NIF: ");
                            String nif = teclado.nextLine();
                            System.out.print("Tipo de cliente (estandar/premium): ");
                            String tipo = teclado.nextLine();


                            controlador.añadirClienteRapido(idCliente, email, nombre, domicilio, nif, tipo);
                        }

                        System.out.print("ID del Artículo: ");
                        int idArticulo = Integer.parseInt(teclado.nextLine());

                        System.out.print("Cantidad de unidades: ");
                        int cantidad = Integer.parseInt(teclado.nextLine());

                        // Creamos el pedido
                        Pedido nuevoPedido = new Pedido(idCliente, idArticulo, cantidad, "PENDIENTE");
                        controlador.añadirPedido(nuevoPedido);

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debes introducir números enteros para los IDs y la cantidad.");
                    }
                    break;

                case 8:
                    System.out.println("\n--- 🗑ELIMINAR PEDIDO ---");
                    try {
                        System.out.print("Introduce el ID del Pedido que quieres cancelar: ");
                        int idPedidoCancelar = Integer.parseInt(teclado.nextLine());
                        controlador.eliminarPedido(idPedidoCancelar);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El ID del pedido debe ser un número entero.");
                    }
                    break;

                case 9:
                    System.out.println("\n--- MOSTRAR PEDIDOS PENDIENTES ---");
                    try {
                        System.out.print("¿Quieres filtrar por un Cliente en concreto? (Introduce su ID o pon '0' para ver todos): ");
                        int filtroPendientes = Integer.parseInt(teclado.nextLine());
                        controlador.mostrarPedidosPendientes(filtroPendientes);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Introduce un número válido.");
                    }
                    break;

                case 10:
                    System.out.println("\n--- MOSTRAR PEDIDOS ENVIADOS ---");
                    try {
                        System.out.print("¿Quieres filtrar por un Cliente en concreto? (Introduce su ID o pon '0' para ver todos): ");
                        int filtroEnviados = Integer.parseInt(teclado.nextLine());
                        controlador.mostrarPedidosEnviados(filtroEnviados);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Error: Introduce un número válido.");
                    }
                    break;

                case 0:
                    System.out.println("\n¡Gracias por usar la Tienda Online! Guardando datos y cerrando...");
                    break;

                default:
                    System.out.println("⚠Opción no válida. Por favor, elige un número del 0 al 10.");
            }
        } while (opcion != 0);

        teclado.close();
    }
}
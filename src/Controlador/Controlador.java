package Controlador;

import DAO.Interfaces.PedidoDAO;
import DAO.Interfaces.ClienteDAO;
import DAO.Interfaces.ArticuloDAO;
import Factory.DAOFactory;
import Modelo.Pedido;
import Modelo.Cliente;
import Modelo.Articulo;
import Excepciones.DAOException;
import java.util.List;

public class Controlador {

    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private ArticuloDAO articuloDAO;

    public Controlador() {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

        // Obtenemos todos los DAOs necesarios
        this.pedidoDAO = factory.getPedidoDAO();
        this.clienteDAO = factory.getClienteDAO();
        this.articuloDAO = factory.getArticuloDAO();
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN DE PEDIDOS
    // ==========================================

    public void añadirPedido(Pedido pedido) {
        try {
            pedidoDAO.insertar(pedido);
            System.out.println("Pedido guardado correctamente en la base de datos.");
        } catch (DAOException e) {
            System.err.println("Error al guardar el pedido: " + e.getMessage());
        }
    }

    public void eliminarPedido(int idPedido) {
        try {
            pedidoDAO.eliminar(idPedido);
            System.out.println("El pedido " + idPedido + " ha sido cancelado y eliminado con éxito.");
        } catch (DAOException e) {
            System.err.println("❌ " + e.getMessage());
        }
    }

    public void mostrarPedidosPendientes(int idCliente) {
        try {
            List<Pedido> pendientes = pedidoDAO.obtenerPedidosPendientes(idCliente);
            if (pendientes.isEmpty()) {
                System.out.println("No hay pedidos pendientes de envío.");
            } else {
                System.out.println("\n--- PEDIDOS PENDIENTES ---");
                for (Pedido p : pendientes) {
                    double total = calcularTotalPedido(p);
                    System.out.println(p.toString() + "TOTAL a pagar: " + String.format("%.2f", total) + "€");
                }
            }
        } catch (DAOException e) {
            System.err.println("❌ Error al recuperar los pedidos pendientes: " + e.getMessage());
        }
    }

    public void mostrarPedidosEnviados(int idCliente) {
        try {
            List<Pedido> enviados = pedidoDAO.obtenerPedidosEnviados(idCliente);
            if (enviados.isEmpty()) {
                System.out.println("No hay pedidos enviados.");
            } else {
                System.out.println("\n--- PEDIDOS ENVIADOS ---");
                for (Pedido p : enviados) {
                    double total = calcularTotalPedido(p);
                    System.out.println(p.toString() + "TOTAL pagado: " + String.format("%.2f", total) + "€");
                }
            }
        } catch (DAOException e) {
            System.err.println("❌ Error al recuperar los pedidos enviados: " + e.getMessage());
        }
    }

    // ==========================================
    // LÓGICA DE NEGOCIO (Descuentos y Totales)
    // ==========================================

    private double calcularTotalPedido(Pedido pedido) {
        double total = 0.0;

        try {
            Articulo articulo = articuloDAO.obtenerPorId(pedido.getIdArticulo());
            Cliente cliente = clienteDAO.obtenerPorId(pedido.getIdCliente());

            if (articulo != null && cliente != null) {
                double costeArticulos = articulo.getPrecioVenta() * pedido.getCantidad();
                double gastosEnvio = articulo.getGastosEnvio();


                if (cliente.getTipoCliente().equalsIgnoreCase("premium")) {
                    gastosEnvio = gastosEnvio * 0.80;
                }


                total = costeArticulos + gastosEnvio;
            }
        } catch (DAOException e) {
            System.err.println("❌ Error al obtener datos para calcular el total: " + e.getMessage());
        }

        return total;
    }

    // ==========================================
    // MÉTODOS PUENTE PARA CLIENTES
    // ==========================================

    public boolean existeCliente(int idCliente) {
        try {
            return clienteDAO.obtenerPorId(idCliente) != null;
        } catch (DAOException e) {
            System.err.println("Error al verificar si el cliente existe: " + e.getMessage());
            return false;
        }
    }

    public void añadirClienteRapido(int idCliente, String email, String nombre, String domicilio, String nif, String tipo) {
        try {
            Cliente nuevoCliente = new Cliente(idCliente, email, nombre, domicilio, nif, tipo);

            clienteDAO.insertar(nuevoCliente);
            System.out.println("Cliente registrado con éxito. Retomando el pedido...");
        } catch (DAOException e) {
            System.err.println("Error al crear el cliente rápido: " + e.getMessage());
        }
    }
}
package Vista;

import Factory.DAOFactory;
import DAO.Interfaces.ArticuloDAO;
import DAO.Interfaces.ClienteDAO;
import DAO.Interfaces.PedidoDAO;

import Modelo.Articulo;
import Modelo.Cliente;
import Modelo.ClienteEstandar;
import Modelo.ClientePremium;
import Modelo.Pedido;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            // =========================
            // ARTICULOS
            // =========================
            ArticuloDAO daoArticulo = DAOFactory.getArticuloDAO();

            Articulo articulo = new Articulo(
                    "A004",
                    "Teclado Logitech",
                    49.99,
                    5.0,
                    24
            );

            daoArticulo.insertar(articulo);
            System.out.println("✔ Articulo insertado correctamente");

            List<Articulo> listaArticulos = daoArticulo.obtenerTodos();

            System.out.println("\n📦 LISTA DE ARTICULOS:");
            for (Articulo a : listaArticulos) {
                System.out.println(
                        "Codigo: " + a.getCodigo() +
                                " | Desc: " + a.getDescripcion() +
                                " | Precio: " + a.getPrecioVenta()
                );
            }

            // =========================
            // CLIENTES
            // =========================
            ClienteDAO daoCliente = DAOFactory.getClienteDAO();

            Cliente cliente1 = new ClienteEstandar(
                    "cliente3@mail.com",
                    "Carlos",
                    "Calle C",
                    "11111111C"
            );

            Cliente cliente2 = new ClientePremium(
                    "cliente4@mail.com",
                    "Laura",
                    "Calle D",
                    "22222222D"
            );

            daoCliente.insertar(cliente1);
            daoCliente.insertar(cliente2);

            System.out.println("\n✔ Clientes insertados correctamente");

            List<Cliente> listaClientes = daoCliente.obtenerTodos();

            System.out.println("\n👤 LISTA DE CLIENTES:");
            for (Cliente c : listaClientes) {

                String tipo = (c instanceof ClientePremium) ? "Premium" : "Estandar";

                System.out.println(
                        "Email: " + c.getEmail() +
                                " | Nombre: " + c.getNombre() +
                                " | Tipo: " + tipo
                );
            }

            // =========================
            // PEDIDOS
            // =========================
            PedidoDAO daoPedido = DAOFactory.getPedidoDAO();

            // ⚠️ IMPORTANTE: IDs deben existir en BD
            Pedido pedido = new Pedido(2, 2, 2, "PENDIENTE");

            daoPedido.insertar(pedido);
            System.out.println("\n✔ Pedido insertado correctamente");

            List<Pedido> listaPedidos = daoPedido.obtenerTodos();

            System.out.println("\n📋 LISTA DE PEDIDOS:");
            for (Pedido p : listaPedidos) {
                System.out.println(
                        "Cliente ID: " + p.getIdCliente() +
                                " | Articulo ID: " + p.getIdArticulo() +
                                " | Cantidad: " + p.getCantidad() +
                                " | Estado: " + p.getEstado()
                );
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR GENERAL:");
            e.printStackTrace();
        }
    }
}
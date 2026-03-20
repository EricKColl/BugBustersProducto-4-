package DAO.MySQL;

import DAO.Interfaces.PedidoDAO;
import Modelo.Pedido;
import Excepciones.DAOException;
import Util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOMySQL implements PedidoDAO {

    @Override
    public void insertar(Pedido pedido) throws DAOException {
        String sql = "INSERT INTO pedidos (id_cliente, id_articulo, cantidad, fecha_hora, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdCliente());
            ps.setInt(2, pedido.getIdArticulo());
            ps.setInt(3, pedido.getCantidad());
            ps.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHora()));
            ps.setString(5, pedido.getEstado());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al insertar pedido: " + e.getMessage());
        }
    }

    @Override
    public Pedido obtenerPorId(int id) throws DAOException {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Pedido(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_articulo"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener pedido: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_articulo"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener pedidos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Pedido> obtenerPorCliente(int idCliente) throws DAOException {
        String sql = "SELECT * FROM pedidos WHERE id_cliente = ?";
        List<Pedido> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Pedido(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_articulo"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener pedidos por cliente: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void actualizar(Pedido pedido) throws DAOException {
        String sql = "UPDATE pedidos SET cantidad = ?, estado = ? WHERE id_pedido = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pedido.getCantidad());
            ps.setString(2, pedido.getEstado());
            ps.setInt(3, pedido.getIdCliente());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar pedido: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar pedido: " + e.getMessage());
        }
    }
}
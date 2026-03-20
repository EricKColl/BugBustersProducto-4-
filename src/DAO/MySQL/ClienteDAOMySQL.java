package DAO.MySQL;

import DAO.Interfaces.ClienteDAO;
import Modelo.Cliente;
import Modelo.ClienteEstandar;
import Modelo.ClientePremium;
import Excepciones.DAOException;
import Util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMySQL implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) throws DAOException {
        String sql = "INSERT INTO clientes (email, nombre, domicilio, nif, tipo_cliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getEmail());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDomicilio());
            ps.setString(4, cliente.getNif());
            ps.setString(5, cliente instanceof ClientePremium ? "premium" : "estandar");

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al insertar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente obtenerPorId(int id) throws DAOException {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return crearCliente(rs);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Cliente obtenerPorEmail(String email) throws DAOException {
        String sql = "SELECT * FROM clientes WHERE email = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return crearCliente(rs);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener cliente por email: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM clientes";
        List<Cliente> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearCliente(rs));
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener clientes: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void actualizar(Cliente cliente) throws DAOException {
        String sql = "UPDATE clientes SET nombre = ?, domicilio = ?, nif = ?, tipo_cliente = ? WHERE email = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDomicilio());
            ps.setString(3, cliente.getNif());
            ps.setString(4, cliente instanceof ClientePremium ? "premium" : "estandar");
            ps.setString(5, cliente.getEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar cliente: " + e.getMessage());
        }
    }

    // =========================
    // METODO CLAVE (IMPORTANTE)
    // =========================
    private Cliente crearCliente(ResultSet rs) throws SQLException {

        String email = rs.getString("email");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String tipo = rs.getString("tipo_cliente");

        if ("premium".equalsIgnoreCase(tipo)) {
            return new ClientePremium(email, nombre, domicilio, nif);
        } else {
            return new ClienteEstandar(email, nombre, domicilio, nif);
        }
    }
}
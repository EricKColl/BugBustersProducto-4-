package DAO.MySQL;

import DAO.Interfaces.ClienteDAO;
import Excepciones.DAOException;
import Modelo.Cliente;
import Modelo.ClienteEstandar;
import Modelo.ClientePremium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMySQL implements ClienteDAO {

    private final Connection conexion;

    public ClienteDAOMySQL(Connection conexion) {
        this.conexion = conexion;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_cliente");
        String email = rs.getString("email");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String tipo = rs.getString("tipo_cliente");

        Cliente cliente;

        if ("premium".equalsIgnoreCase(tipo)) {
            cliente = new ClientePremium(email, nombre, domicilio, nif);
        } else {
            cliente = new ClienteEstandar(email, nombre, domicilio, nif);
        }

        cliente.setIdCliente(id);
        return cliente;
    }

    @Override
    public List<Cliente> obtenerClientesEstandar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE tipo_cliente = 'estandar'";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Cliente> obtenerClientesPremium() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE tipo_cliente = 'premium'";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void insertar(Cliente cliente) throws DAOException {
        String sql = "{CALL insertar_cliente(?, ?, ?, ?, ?)}";

        boolean autoCommitAnterior;
        try {
            autoCommitAnterior = conexion.getAutoCommit();
            conexion.setAutoCommit(false);

            try (CallableStatement cs = conexion.prepareCall(sql)) {
                cs.setString(1, cliente.getEmail());
                cs.setString(2, cliente.getNombre());
                cs.setString(3, cliente.getDomicilio());
                cs.setString(4, cliente.getNif());

                String tipo = (cliente instanceof ClientePremium) ? "premium" : "estandar";
                cs.setString(5, tipo);

                cs.executeUpdate();
                conexion.commit();

            } catch (SQLException e) {
                conexion.rollback();
                throw new DAOException("Error al insertar el cliente con email: " + cliente.getEmail(), e);
            } finally {
                conexion.setAutoCommit(autoCommitAnterior);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al gestionar la transacción al insertar cliente", e);
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener todos los clientes", e);
        }

        return lista;
    }

    @Override
    public Cliente obtenerPorId(Integer id) throws DAOException {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener cliente por ID: " + id, e);
        }

        return null;
    }

    public boolean existe(Integer idCliente) {
        String sql = "SELECT 1 FROM clientes WHERE id_cliente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean tienePedidosAsociados(Integer idCliente) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE id_cliente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            return true;
        }

        return false;
    }

    @Override
    public void eliminar(Integer idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        if (tienePedidosAsociados(idCliente)) {
            System.err.println("No se puede eliminar el cliente porque tiene pedidos asociados.");
            return;
        }

        boolean autoCommitAnterior;
        try {
            autoCommitAnterior = conexion.getAutoCommit();
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
                conexion.commit();

            } catch (SQLException e) {
                conexion.rollback();
                System.err.println("Error al eliminar cliente: " + e.getMessage());
            } finally {
                conexion.setAutoCommit(autoCommitAnterior);
            }

        } catch (SQLException e) {
            System.err.println("Error al gestionar la transacción al eliminar cliente: " + e.getMessage());
        }
    }

    @Override
    public boolean existePorEmail(String email) throws DAOException {
        String sql = "SELECT 1 FROM clientes WHERE email = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DAOException("Error al comprobar email", e);
        }
    }

    @Override
    public Cliente obtenerPorEmail(String email) throws DAOException {
        String sql = "SELECT * FROM clientes WHERE email = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar por email", e);
        }

        return null;
    }
}
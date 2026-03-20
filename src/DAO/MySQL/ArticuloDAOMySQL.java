package DAO.MySQL;

import DAO.Interfaces.ArticuloDAO;
import Modelo.Articulo;
import Excepciones.DAOException;
import Util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOMySQL implements ArticuloDAO {

    @Override
    public void insertar(Articulo articulo) throws DAOException {
        String sql = "INSERT INTO articulos (codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, articulo.getCodigo());
            ps.setString(2, articulo.getDescripcion());
            ps.setDouble(3, articulo.getPrecioVenta());
            ps.setDouble(4, articulo.getGastosEnvio());
            ps.setInt(5, articulo.getTiempoPreparacionMin());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al insertar artículo: " + e.getMessage());
        }
    }

    @Override
    public Articulo obtenerPorId(int id) throws DAOException {
        String sql = "SELECT * FROM articulos WHERE id_articulo = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener artículo: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Articulo> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM articulos";
        List<Articulo> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Articulo a = new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                );
                lista.add(a);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener artículos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void actualizar(Articulo articulo) throws DAOException {
        String sql = "UPDATE articulos SET descripcion = ?, precio_venta = ?, gastos_envio = ?, tiempo_preparacion = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, articulo.getDescripcion());
            ps.setDouble(2, articulo.getPrecioVenta());
            ps.setDouble(3, articulo.getGastosEnvio());
            ps.setInt(4, articulo.getTiempoPreparacionMin());
            ps.setString(5, articulo.getCodigo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar artículo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM articulos WHERE id_articulo = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar artículo: " + e.getMessage());
        }
    }
}
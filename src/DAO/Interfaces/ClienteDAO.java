package DAO.Interfaces;

import Modelo.Cliente;
import Excepciones.DAOException;
import java.util.List;

public interface ClienteDAO {

    void insertar(Cliente cliente) throws DAOException;

    Cliente obtenerPorId(int id) throws DAOException;

    Cliente obtenerPorEmail(String email) throws DAOException;

    List<Cliente> obtenerTodos() throws DAOException;

    void actualizar(Cliente cliente) throws DAOException;

    void eliminar(int id) throws DAOException;
}
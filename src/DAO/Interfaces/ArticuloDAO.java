package DAO.Interfaces;

import Modelo.Articulo;
import Excepciones.DAOException;
import java.util.List;

public interface ArticuloDAO {

    void insertar(Articulo articulo) throws DAOException;

    Articulo obtenerPorId(int id) throws DAOException;

    List<Articulo> obtenerTodos() throws DAOException;

}
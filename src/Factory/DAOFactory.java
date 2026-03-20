package Factory;

import DAO.Interfaces.ArticuloDAO;
import DAO.Interfaces.ClienteDAO;
import DAO.Interfaces.PedidoDAO;

import DAO.MySQL.ArticuloDAOMySQL;
import DAO.MySQL.ClienteDAOMySQL;
import DAO.MySQL.PedidoDAOMySQL;

public class DAOFactory {

    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOMySQL();
    }

    public static ClienteDAO getClienteDAO() {
        return new ClienteDAOMySQL();
    }

    public static PedidoDAO getPedidoDAO() {
        return new PedidoDAOMySQL();
    }
}
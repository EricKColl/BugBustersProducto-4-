package Factory;

import DAO.Interfaces.PedidoDAO;
import DAO.MySQL.PedidoDAOMySQL;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public PedidoDAO getPedidoDAO() {
        return new PedidoDAOMySQL();
    }

}
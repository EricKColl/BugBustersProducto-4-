package Factory;

import DAO.Interfaces.PedidoDAO;
// import DAO.Interfaces.ArticuloDAO;
// import DAO.Interfaces.ClienteDAO;

public abstract class DAOFactory {

    public static final int MYSQL = 1;

    // --- MÉTODOS PARA OBTENER LOS DAOs ---

    public abstract PedidoDAO getPedidoDAO();


    public static DAOFactory getDAOFactory(int tipoBD) {
        switch (tipoBD) {
            case MYSQL:
                return new MySQLDAOFactory();
            default:
                return null;
        }
    }
}
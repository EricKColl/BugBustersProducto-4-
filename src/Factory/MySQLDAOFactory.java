package Factory;

import DAO.Interfaces.ArticuloDAO;
import DAO.MySQL.ArticuloDAOMySQL;
import Excepciones.DAOException;
import DAO.Interfaces.PedidoDAO;
import DAO.MySQL.PedidoDAOMySQL;
import DAO.Interfaces.ClienteDAO;
import DAO.MySQL.ClienteDAOMySQL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MySQLDAOFactory extends DAOFactory {

    // El factory de JPA que leerá el persistence.xml
    private static EntityManagerFactory emf;
    private EntityManager em;

    public MySQLDAOFactory() {
        if (emf == null) {
            // Inicia JPA usando el nombre del persistence-unit
            emf = Persistence.createEntityManagerFactory("Producto4PU");
        }
    }

    @Override
    public EntityManager getEntityManager() throws DAOException {
        // Si no hay un EntityManager o se cerró, creamos uno nuevo
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    @Override
    public ArticuloDAO getArticuloDAO() throws DAOException {
        // Pedimos la instancia única de ConexionBD
        EntityManager emActual = getEntityManager();

        //Nos aseguramos que esté la base de datos conectada.
        // (JPA gestiona la conexión automáticamente bajo demanda)

        // Construimos el DAO de MySQL y le inyectamos la conexión abierta
        return new ArticuloDAOMySQL(emActual);
    }

    @Override
    public PedidoDAO getPedidoDAO() throws DAOException{
        // Pedimos la instancia única de ConexionBD (Ahora EntityManager)
        EntityManager emActual = getEntityManager();

        //Nos aseguramos que esté la base de datos conectada.

        return new PedidoDAOMySQL(emActual);
    }

    @Override
    public ClienteDAO getClienteDAO() throws DAOException {
        // Pedimos la instancia única de ConexionBD (Ahora EntityManager)
        EntityManager emActual = getEntityManager();

        //Nos aseguramos que esté la base de datos conectada.

        return new ClienteDAOMySQL(emActual);
    }

    @Override
    public void iniciarTransaccion() throws DAOException {
        try {
            getEntityManager().getTransaction().begin();
        } catch (Exception e) {
            throw new DAOException("Error al iniciar transacción JPA", e);
        }
    }

    @Override
    public void confirmarTransaccion() throws DAOException {
        try {
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            throw new DAOException("Error al confirmar transacción JPA", e);
        }
    }

    @Override
    public void cancelarTransaccion() throws DAOException {
        try {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        } catch (Exception e) {
            throw new DAOException("Error al cancelar transacción JPA", e);
        }
    }
}
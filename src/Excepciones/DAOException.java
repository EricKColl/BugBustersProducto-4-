package Excepciones;

public class DAOException extends Exception {
    // MODO 1: Cuando hay un fallo de SQL
    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // MODO 2: Cuando quieres lanzar un error TÚ (ej.: "Tiene pedidos")
    public DAOException(String mensaje) {
        super(mensaje);
    }
}
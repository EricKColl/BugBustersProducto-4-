package Modelo;

public class Cliente {

    protected int idCliente;
    protected String email;
    protected String nombre;
    protected String domicilio;
    protected String nif;

    // CONSTRUCTOR
    public Cliente(String email, String nombre, String domicilio, String nif) {
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
    }

    // GETTERS
    public int getIdCliente() {
        return idCliente;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getNif() {
        return nif;
    }

    // SETTERS (opcionales pero recomendados)
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
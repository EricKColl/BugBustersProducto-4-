package Modelo;

import java.time.LocalDateTime;

public class Pedido {

    private int idPedido;
    private int idCliente;
    private int idArticulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private String estado;

    public Pedido(int idCliente, int idArticulo, int cantidad, String estado) {
        this.idCliente = idCliente;
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
        this.estado = estado;
        this.fechaHora = LocalDateTime.now();
    }

    // GETTERS
    public int getIdCliente() {
        return idCliente;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }
}
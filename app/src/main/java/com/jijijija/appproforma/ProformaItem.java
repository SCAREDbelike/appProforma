package com.jijijija.appproforma;

public class ProformaItem {

    private String codigo;
    private String producto;
    private double precio;
    private int cantidad;

    public ProformaItem(String codigo, String producto, double precio, int cantidad) {
        this.codigo = codigo;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProducto() {
        return producto;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                "\nProducto: " + producto +
                "\nPrecio: S/. " + precio +
                "\nCantidad: " + cantidad +
                "\nTotal: S/. " + getTotal();
    }
}
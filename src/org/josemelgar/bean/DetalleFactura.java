/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.bean;

/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class DetalleFactura {

    private int codigoDeDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int codigoDeFactura;
    private int codigoProducto;

    public DetalleFactura() {
    }

    public DetalleFactura(int codigoDeDetalleFactura, double precioUnitario, int cantidad, int codigoDeFactura, int codigoProducto) {
        this.codigoDeDetalleFactura = codigoDeDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.codigoDeFactura = codigoDeFactura;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoDeDetalleFactura() {
        return codigoDeDetalleFactura;
    }

    public void setCodigoDeDetalleFactura(int codigoDeDetalleFactura) {
        this.codigoDeDetalleFactura = codigoDeDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigoDeFactura() {
        return codigoDeFactura;
    }

    public void setCodigoDeFactura(int codigoDeFactura) {
        this.codigoDeFactura = codigoDeFactura;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

}

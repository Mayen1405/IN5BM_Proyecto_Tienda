/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.bean;

/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class TelefonoProveedor {

    private int codigoTelefonoProveedor;
    private String numeroPincipal;
    private String numeroSecundario;
    private String observaciones;
    private int codigoProveedor;

    public TelefonoProveedor() {
    }

    public TelefonoProveedor(int codigoTelefonoProveedor, String numeroPincipal, String numeroSecundario, String observaciones, int codigoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numeroPincipal = numeroPincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public String getNumeroPincipal() {
        return numeroPincipal;
    }

    public void setNumeroPincipal(String numeroPincipal) {
        this.numeroPincipal = numeroPincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

}

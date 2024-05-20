/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.bean;

/**
 * Nombre completo:Jose Pablo Melgar Mayen
 * carnet: 2020478
 * seccion: IN5BM
 */
public class Clientes {
    private int CodigoClientes;
    private String nit;
    private String NombreClientes;
    private String ApellidosClientes;
    private String DireccionCliente;
    private String TelefonoCliente;
    private String CorreoCliente;
    


    public Clientes() {
    }

    public Clientes(int CodigoClientes,String nit, String NombreClientes, String ApellidosClientes, String DireccionCliente, String TelefonoCliente, String CorreoCliente) {
        this.CodigoClientes = CodigoClientes;
        this.nit = nit;
        this.NombreClientes = NombreClientes;
        this.ApellidosClientes = ApellidosClientes;
        this.DireccionCliente = DireccionCliente;
        this.TelefonoCliente = TelefonoCliente;
        this.CorreoCliente = CorreoCliente;
    }

    public Clientes(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getCodigoClientes() {
        return CodigoClientes;
    }

    public void setCodigoClientes(int CodigoClientes) {
        this.CodigoClientes = CodigoClientes;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreClientes() {
        return NombreClientes;
    }

    public void setNombreClientes(String NombreClientes) {
        this.NombreClientes = NombreClientes;
    }

    public String getApellidosClientes() {
        return ApellidosClientes;
    }

    public void setApellidosClientes(String ApellidosClientes) {
        this.ApellidosClientes = ApellidosClientes;
    }

    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public void setDireccionCliente(String DireccionCliente) {
        this.DireccionCliente = DireccionCliente;
    }

    public String getTelefonoCliente() {
        return TelefonoCliente;
    }

    public void setTelefonoCliente(String TelefonoCliente) {
        this.TelefonoCliente = TelefonoCliente;
    }

    public String getCorreoCliente() {
        return CorreoCliente;
    }

    public void setCorreoCliente(String CorreoCliente) {
        this.CorreoCliente = CorreoCliente;
    }

    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.bean;

/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class Productos {

    private int numeroDocumento;
    private String fechaDocumento;
    private String descripcion;
    private double descripcionCargo;

    public Productos() {
    }

    public Productos(int numeroDocumento, String fechaDocumento, String descripcion, double descripcionCargo) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.descripcionCargo = descripcionCargo;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(double descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

}

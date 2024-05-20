/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.josemelgar.system.Principal;
import org.josemelgar.bean.Compras;
import org.josemelgar.db.Conexion;
import org.josemelgar.system.Principal;

/**
 *
 * @author informatica
 */
public class MenuComprasController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {
        AgregarCompra, EliminarCompra, EditarCompra, ActualizarCompra, CancelarCompra, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Compras> listaDeCompras;
    
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAgregarD;
    @FXML
    private Button btnEliminarD;
    @FXML
    private Button btnEditarD;
    @FXML
    private Button btnReportes;
    @FXML
    private TextField txtNumeroD;
    @FXML
    private TextField txtFechaD;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTotalD;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroD;
    @FXML
    private TableColumn colFechaD;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colTotalD;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnBack) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public void setBtnBack(Button btnBack) {
        this.btnBack = btnBack;
    }
    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
              ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDeCompras = FXCollections.observableList(lista);
    }
    
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroD.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaD.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalD.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalDocumento"));
    }
    
    public void seleccionar() {
        txtNumeroD.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaD.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescripcion.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalD.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }
    
    public void desactivarControles() {
        txtNumeroD.setEditable(false);
        txtFechaD.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalD.setEditable(false); 
    }
    
    public void activarControles() {
        txtNumeroD.setEditable(true);
        txtFechaD.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalD.setEditable(true); 
    }
    
    public void limpiarControles() {
        txtNumeroD.clear();
        txtFechaD.clear();
        txtDescripcion.clear();
        txtTotalD.clear();
    }
    
    public void agregarCliente() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregarD.setText("Guardar");
                btnEliminarD.setText("Cancelar");
                btnEditarD.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperaciones = operaciones.ActualizarCompra;
                break;
            case ActualizarCompra:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarD.setText("Agregar");
                btnEliminarD.setText("Eliminar");
                btnEditarD.setDisable(false);
                btnReportes.setDisable(false);
                cargarDatos();
                tipoDeOperaciones = operaciones.Ninguno;
                break;
        }
    }
    
    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroD.getText()));
        registro.setFechaDocumento(txtFechaD.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalD.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaDeCompras.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

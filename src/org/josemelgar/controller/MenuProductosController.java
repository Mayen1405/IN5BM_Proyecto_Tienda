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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.josemelgar.bean.Productos;
import org.josemelgar.bean.Proveedores;
import org.josemelgar.bean.TipoDeProducto;
import org.josemelgar.db.Conexion;
import org.josemelgar.system.Principal;


/**
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {
        AgregarCliente, EliminarCliente, EditarCliente, ActualizarCliente, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Productos> ListaProductos;
    private ObservableList<Proveedores> ListaProveedores;
    private ObservableList<TipoDeProducto> ListaTipoDeProducto;
    @FXML
    private Button btnReturn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnReturn) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}

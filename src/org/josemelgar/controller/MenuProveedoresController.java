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
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.JOptionPane;
import org.josemelgar.system.Principal;
import org.josemelgar.bean.Proveedores;
import org.josemelgar.db.Conexion;
import org.josemelgar.report.GenerarReportes;

/**
 *
 * @author informatica
 */
public class MenuProveedoresController implements Initializable {

    private ObservableList<Proveedores> ListaProveedores;

    private enum operaciones {
        AgregarProveedor, EliminarProveedor, EditarProveedor, ActualizarProveedor, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnReturn;
    @FXML
    private TableView tblPoveedores;
    @FXML
    private TableColumn colCodProveedor;
    @FXML
    private TableColumn colNitProveedor;
    @FXML
    private TableColumn colNomProvedor;
    @FXML
    private TableColumn colApeProvedor;
    @FXML
    private TableColumn colDireProvedor;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoProvedor;
    @FXML
    private TableColumn colSitioWeb;
    @FXML
    private Button btnAgregarProvedor;
    @FXML
    private Button btnEditarProvedor;
    @FXML
    private Button btnEliminarProvedor;
    @FXML
    private Button btnReportesProvedor;
    @FXML
    private TextField txtCodigoProvedor;
    @FXML
    private TextField txtNitProvedor;
    @FXML
    private TextField txtNombresProvedor;
    @FXML
    private TextField txtApellidosProvedor;
    @FXML
    private TextField txtDireccionProvedor;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoProveedor;
    @FXML
    private TextField txtSitioWeb;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreoProveedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblPoveedores.setItems(getProveedores());
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNomProvedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApeProvedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireProvedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colSitioWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
        colContactoProvedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));

    }

    public void seleccionarElemento() {
        txtCodigoProvedor.setText(String.valueOf(((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitProvedor.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getNitProveedor()));
        txtNombresProvedor.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtApellidosProvedor.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtDireccionProvedor.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRazonSocial.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtContactoProveedor.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtSitioWeb.setText((((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NitProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaweb")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaProveedores = FXCollections.observableList(listaP);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregarProvedor.setText("Guardar");
                btnEliminarProvedor.setText("Cancelar");
                btnEditarProvedor.setDisable(true);
                btnReportesProvedor.setDisable(true);
                tipoDeOperaciones = tipoDeOperaciones.ActualizarProveedor;
                break;
            case ActualizarProveedor:
                guardar();
                limpiarControles();

                desactivarControles();
                btnAgregarProvedor.setText("Agregar");
                btnEliminarProvedor.setText("Eliminar");
                btnEditarProvedor.setDisable(false);
                btnReportesProvedor.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProvedor.getText()));
        registro.setNitProveedor(txtNitProvedor.getText());
        registro.setNombreProveedor(txtNombresProvedor.getText());
        registro.setApellidoProveedor(txtApellidosProvedor.getText());
        registro.setDireccionProveedor(txtDireccionProvedor.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoProveedor.getText());
        registro.setPaginaWeb(txtSitioWeb.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarproveedor(?,?,?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            ListaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ActualizarProveedor:
                desactivarControles();
                limpiarControles();
                btnAgregarProvedor.setText("Agregar");
                btnEliminarProvedor.setText("Eliminar");
                btnEditarProvedor.setDisable(false);
                btnReportesProvedor.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                break;
            default:
                if (tblPoveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminacion del registro?", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblPoveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            ListaProveedores.remove(tblPoveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }

                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                if (tblPoveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarProvedor.setText("Actualizar");
                    btnReportesProvedor.setText("Cancelar");
                    btnEliminarProvedor.setDisable(true);
                    btnAgregarProvedor.setDisable(true);
                    activarControles();
                    txtCodigoProvedor.setEditable(false);
                    tipoDeOperaciones = tipoDeOperaciones.ActualizarProveedor;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ActualizarProveedor:
                actualizar();
                btnEditarProvedor.setText("Editar");
                btnReportesProvedor.setText("Reportes");
                btnAgregarProvedor.setDisable(false);
                btnEliminarProvedor.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                imprimirReporte();
                break;
            case ActualizarProveedor:
                btnReportesProvedor.setText("Reportes");
                btnEditarProvedor.setText("Editar");
                btnAgregarProvedor.setDisable(false);
                btnEliminarProvedor.setDisable(false);
                btnReturn.setDisable(false);
                limpiarControles();
                desactivarControles();
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatos();
        }
    }
    
    public void imprimirReporte() {
        Map parametro = new HashMap();
        parametro.put("IDCliente", null);
        GenerarReportes.mostrarReportes("reportesProveedores.jasper", "Reportes de Proveedores", parametro);
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarproveedor(?,?,?,?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores) tblPoveedores.getSelectionModel().getSelectedItem();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProvedor.getText()));
            registro.setNitProveedor(txtNitProvedor.getText());
            registro.setNombreProveedor(txtNombresProvedor.getText());
            registro.setApellidoProveedor(txtApellidosProvedor.getText());
            registro.setDireccionProveedor(txtDireccionProvedor.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoProveedor.getText());
            registro.setPaginaWeb(txtSitioWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public void desactivarControles() {
        txtCodigoProvedor.setEditable(false);
        txtNitProvedor.setEditable(false);
        txtNombresProvedor.setEditable(false);
        txtApellidosProvedor.setEditable(false);
        txtDireccionProvedor.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoProveedor.setEditable(false);
        txtSitioWeb.setEditable(false);
        txtTelefono.setEditable(false);
        txtCorreoProveedor.setEditable(false);
    }

    public void activarControles() {
        txtCodigoProvedor.setEditable(true);
        txtNitProvedor.setEditable(true);
        txtNombresProvedor.setEditable(true);
        txtApellidosProvedor.setEditable(true);
        txtDireccionProvedor.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoProveedor.setEditable(true);
        txtSitioWeb.setEditable(true);
        txtTelefono.setEditable(true);
        txtCorreoProveedor.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoProvedor.clear();
        txtNitProvedor.clear();
        txtNombresProvedor.clear();
        txtApellidosProvedor.clear();
        txtDireccionProvedor.clear();
        txtRazonSocial.clear();
        txtContactoProveedor.clear();
        txtSitioWeb.clear();
        txtTelefono.clear();
        txtCorreoProveedor.clear();
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

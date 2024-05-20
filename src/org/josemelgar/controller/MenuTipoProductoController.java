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
import javax.swing.JOptionPane;
import org.josemelgar.system.Principal;
import org.josemelgar.bean.TipoDeProducto;
import org.josemelgar.db.Conexion;

/**
 *
 * @author informatica
 */
public class MenuTipoProductoController implements Initializable {

    private ObservableList<TipoDeProducto> ListaTipoDeProducto;

    private enum operaciones {
        AgregarTP, EliminarTP, EditarTP, ActualizarTP, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnReturn;
    private TableView tblTipoProducto;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn coldescripcion;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCodigoTipoP;
    @FXML
    private Button btnAgregarTP;
    @FXML
    private Button btnEditarTP;
    @FXML
    private Button btnEliminarTP;
    @FXML
    private Button btnReportes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, Integer>("codigoTipoProducto"));
        coldescripcion.setCellValueFactory(new PropertyValueFactory<TipoDeProducto, String>("descripcion"));

    }

    public void seleccionarElemento() {
        txtCodigoTipoP.setText(String.valueOf(((TipoDeProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcion.setText((((TipoDeProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }

    public ObservableList<TipoDeProducto> getTipoProducto() {
        ArrayList<TipoDeProducto> listaTipoP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaTipoP.add(new TipoDeProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaTipoDeProducto = FXCollections.observableList(listaTipoP);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregarTP.setText("Guardar");
                btnEliminarTP.setText("Cancelar");
                btnEditarTP.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperaciones = tipoDeOperaciones.ActualizarTP;
                break;
            case ActualizarTP:
                guardar();
                limpiarControles();

                desactivarControles();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        TipoDeProducto registro = new TipoDeProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoP.getText()));
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTipoProducto(?,?,)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            ListaTipoDeProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ActualizarTP:
                desactivarControles();
                limpiarControles();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                break;
            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminacion del registro?", "Eliminar TipoP", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoDeProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            ListaTipoDeProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTP.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnEliminarTP.setDisable(true);
                    btnAgregarTP.setDisable(true);
                    activarControles();
                    txtCodigoTipoP.setEditable(false);
                    tipoDeOperaciones = tipoDeOperaciones.ActualizarTP;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ActualizarTP:
                actualizar();
                btnEditarTP.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ActualizarTP:
                desactivarControles();
                limpiarControles();
                btnEditarTP.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.Ninguno;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?,?)}");
            TipoDeProducto registro = (TipoDeProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoP.getText()));
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoTipoP.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    public void activarControles() {
        txtCodigoTipoP.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoTipoP.clear();
        txtDescripcion.clear();
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

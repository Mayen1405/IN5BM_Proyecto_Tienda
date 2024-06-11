package org.josemelgar.controller;

import com.sun.javaws.Main;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josemelgar.db.Conexion;
import org.josemelgar.system.Principal;
import org.josemelgar.bean.CargoEmpleado;


/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class MenuCargoEmpleado implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        AgregarCE, EliminarCE, EditarCE, ActualizarCE, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<CargoEmpleado> ListaCargoEmpleado;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoCargoEmpleado;
    @FXML
    private TextField txtnombreCargo;
    @FXML
    private TextField txtdescripcionCargo;
    @FXML
    private TableView tblCargoE;
    @FXML
    private TableColumn colCodigoCargoC;
    @FXML
    private TableColumn colNombreCargoC;
    @FXML
    private TableColumn colDescripcionC;
    @FXML
    private Button btnAgregarCargoE;
    @FXML
    private Button btnEditarCargoE;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnEliminarCargoE;
    @FXML
    private ImageView imgAgregarCargoE;
    @FXML
    private ImageView imgEliminarCargoE;
    @FXML
    private ImageView imgActualizarCargoE;
    @FXML
    private ImageView imgReportes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosCargoE();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    public void cargarDatosCargoE() {
        tblCargoE.setItems(getCargoE());
        colCodigoCargoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }
    
     public void seleccionarElementoCargoEmpleado() {
        txtCodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtnombreCargo.setText(((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtdescripcionCargo.setText(((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }
     
    public ObservableList<CargoEmpleado> getCargoE() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_ListarCargoEmpleado() }");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("CodigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListaCargoEmpleado = FXCollections.observableList(lista);
    }
    
    public void guardarCargoEmpleado() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
        registro.setNombreCargo(txtnombreCargo.getText());
        registro.setDescripcionCargo(txtdescripcionCargo.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());

            procedimiento.execute();
            ListaCargoEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarCargoEmpleado() {

        CargoEmpleado registro = (CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
        registro.setNombreCargo(txtnombreCargo.getText());
        registro.setDescripcionCargo(txtdescripcionCargo.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizaRCargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());

            procedimiento.execute();
            ListaCargoEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void AgregarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregarCargoE.setText("Guardar");
                btnReportes.setText("Cancelar");
                btnAgregarCargoE.setDisable(false);
                btnReportes.setDisable(false);
                btnEliminarCargoE.setDisable(true);
                btnEditarCargoE.setDisable(true);
                tipoDeOperaciones = operaciones.ActualizarCE;
                break;
            case ActualizarCE:
                guardarCargoEmpleado();
                desactivarControles();
                btnAgregarCargoE.setText("Agregar");
                btnReportes.setText("Cancelar");
                btnEditarCargoE.setDisable(false);
                btnEliminarCargoE.setDisable(false);
                btnEliminarCargoE.setDisable(true);
                btnEditarCargoE.setDisable(true);
                tipoDeOperaciones = operaciones.Ninguno;
                break;
        }
    }
    
    public void eliminarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case ActualizarCE:
                desactivarControles();
                limpiarControles();
                btnAgregarCargoE.setText("Agregar");
                btnEliminarCargoE.setText("Eliminar");
                btnEditarCargoE.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.Ninguno;
                break;
            default:
                if (tblCargoE.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del Cargo de Empleado",
                            "Eliminar Cargo de Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ CALL sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            ListaCargoEmpleado.remove(tblCargoE.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un Cargo de Empleado para eliminar");
                }
                break; 
        }
    }
        
        
        public void editarCargoEmpleado() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                if (tblCargoE.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCargoE.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnEditarCargoE.setDisable(false);
                    btnReportes.setDisable(false);
                    btnAgregarCargoE.setDisable(true);
                    btnEliminarCargoE.setDisable(true);
                    activarControles();
                    txtCodigoCargoEmpleado.setEditable(false);
                    tipoDeOperaciones = operaciones.ActualizarCE;
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un Cargo de Empleado para editar");
                }
                break;
            case ActualizarCE:
                actualizarCargoEmpleado();
                btnEditarCargoE.setText("Actualizar");
                btnReportes.setText("Reporte");
                btnEditarCargoE.setDisable(false);
                btnReportes.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatosCargoE();
                break;
        }
    }
    
    public void cancelarAccion() {
        limpiarControles();
        desactivarControles();
        btnAgregarCargoE.setText("Agregar");
        btnEditarCargoE.setText("Editar");
        btnEliminarCargoE.setText("Eliminar");
        btnRegresar.setText("Regresar");
        btnReportes.setText("Reportes");
        btnAgregarCargoE.setDisable(false);
        btnEditarCargoE.setDisable(false);
        btnEliminarCargoE.setDisable(false);
        btnReportes.setDisable(false);
        tipoDeOperaciones = operaciones.Ninguno;
    }

    public void desactivarControles() {
        txtCodigoCargoEmpleado.setEditable(false);
        txtnombreCargo.setEditable(false);
        txtdescripcionCargo.setEditable(false);
    }

    public void activarControles() {
        txtCodigoCargoEmpleado.setEditable(true);
        txtnombreCargo.setEditable(true);
        txtdescripcionCargo.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoCargoEmpleado.clear();
        txtnombreCargo.clear();
        txtdescripcionCargo.clear();
    }
    
}


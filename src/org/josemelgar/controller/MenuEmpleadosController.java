/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.josemelgar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josemelgar.bean.CargoEmpleado;
import org.josemelgar.bean.Empleados;
import org.josemelgar.db.Conexion;
import org.josemelgar.report.GenerarReportes;
import org.josemelgar.system.Principal;

/**
 *
 * @author Jose
 */
public class MenuEmpleadosController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        Agregar, Eliminar, Editar, Actualizar, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Empleados> ListaEmpleados;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtTurno;
    @FXML
    private TextField txtDireccion;
    @FXML
    private ComboBox cmbCargoC;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodigo;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colCargoEmpleado;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgActualizar;
    @FXML
    private ImageView imgReportes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCargoC.setItems(getCargoE());
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

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("IDEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idCargoEmpleado"));
    }

    public void selecionarElementos() {
        txtCodigo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombre.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellido.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurno.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cmbCargoC.getSelectionModel().select(buscarCargoEmpleado(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }

    public CargoEmpleado buscarCargoEmpleado(int idCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarCargoEmpleado(?)}");
            procedimiento.setInt(1, idCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("idCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> ListaEm = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                ListaEm.add(new Empleados(resultado.getInt("IDEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("idCargoEmpleado")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaEmpleados = FXCollections.observableList(ListaEm);
    }

    public ObservableList<CargoEmpleado> getCargoE() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_ListarCargoEmpleado() }");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("CodigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcion")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(lista);
    }

    public void guardarEmpleados() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigo.getText()));
        registro.setNombresEmpleado(txtNombre.getText());
        registro.setApellidosEmpleado(txtApellido.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCargoC.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ActualizarEmpleados() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();

            registro.setCodigoEmpleado(Integer.parseInt(txtCodigo.getText()));
            registro.setNombresEmpleado(txtNombre.getText());
            registro.setApellidosEmpleado(txtApellido.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCargoC.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ActualizarEmpleado() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnActualizar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigo.setEditable(false);
                    tipoDeOperaciones = operaciones.Actualizar;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe SELECCIONAR un empleado para actualizar");
                }
                break;
            case Actualizar:
                try {
                ActualizarEmpleados();
            } catch (Exception e) {
                e.printStackTrace();
            }
            btnActualizar.setText("Editar");
            btnReportes.setText("Reporte");
            btnAgregar.setDisable(false);
            btnEliminar.setDisable(false);
            desactivarControles();
            limpiarControles();
            tipoDeOperaciones = operaciones.Ninguno;
            cargarDatos();
            break;
        }

    }

    public void AgregarEmpleados() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnActualizar.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperaciones = operaciones.Actualizar;
                break;
            case Actualizar:
                guardarEmpleados();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnActualizar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void EliminarEmpleado() {
        switch (tipoDeOperaciones) {
            case Actualizar:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnActualizar.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.Ninguno;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirma la eliminar el registro", "Eliminar?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            ListaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para eliminar");
                }
                break;
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                imprimirReporte();
                break;
            case Actualizar:
                btnReportes.setText("Reportes");
                btnEditar.setText("Editar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnRegresar.setDisable(false);
                limpiarControles();
                desactivarControles();
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatos();
        }
    }

    public void imprimirReporte() {
        Map parametro = new HashMap();
        parametro.put("IDCliente", null);
        GenerarReportes.mostrarReportes("reporteEmpleados.jasper", "Reportes de Empleados", parametro);
    }

    public void activarControles() {
        txtCodigo.setEditable(true);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cmbCargoC.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigo.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cmbCargoC.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigo.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        cmbCargoC.getSelectionModel().getSelectedItem();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.josemelgar.controller;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.josemelgar.bean.Clientes;
import org.josemelgar.bean.Empleados;
import org.josemelgar.bean.Factura;
import org.josemelgar.db.Conexion;
import org.josemelgar.report.GenerarReportes;
import org.josemelgar.system.Principal;

/**
 *
 * @author Jose
 */
public class MenuFacturaController implements Initializable {
    
    private Principal escenarioPrincipal;
    private ObservableList<Factura> listasFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private enum operaciones {
        Agregar, Editar, Actualizar, Eliminar, Cancelar, Ninguno
    }

    private operaciones tipoOperaciones = operaciones.Ninguno;

    @FXML
    private DatePicker DpFechaF;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnReportes;

    @FXML
    private ComboBox cmbClienteC;

    @FXML
    private ComboBox cmbEmpleadoC;

    @FXML
    private TableColumn colClienteC;

    @FXML
    private TableColumn colFacturaC;

    @FXML
    private TableColumn colEmpleadoC;

    @FXML
    private TableColumn colEstado;

    @FXML
    private TableColumn colFechaF;

    @FXML
    private TableColumn colTotalF;

    @FXML
    private TableView tblFactura;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtFactura;

    @FXML
    private TextField txtTotalFactura;
    
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

    public void CargarDatosFactura() {
        tblFactura.setItems(getFactura());
        colFacturaC.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("IDDeFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colClienteC.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("IDCliente"));
        colEmpleadoC.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("IDEmpleado"));
    }

    public void seleccionarElementosFactura() {
        int IDDeFactura = ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoDeFactura();

        txtFactura.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoDeFactura()));
        txtEstado.setText(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalFactura.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoDeFactura()));
        DpFechaF.setValue(LocalDate.parse(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbClienteC.getSelectionModel().select(buscarClientes(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbEmpleadoC.getSelectionModel().select(buscarEmpleados(IDDeFactura));
    }

    public Clientes buscarClientes(int CodigoCliente) {
        Clientes resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, CodigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("CodigoCliente"),
                        registro.getString("nitCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Empleados buscarEmpleados(int CodigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, CodigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Empleados(registro.getInt("CodigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("CodigoCargoEmpleado")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("CodigoDeFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("CodigoCliente"),
                        resultado.getInt("CodigoEmpleado")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listasFactura = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("IDEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("idCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(lista);
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("CodigoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(lista);
    }
    
    public void guardarFactura() {
        Factura registro = new Factura();
        registro.setCodigoDeFactura(Integer.parseInt(txtFactura.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
        registro.setFechaFactura(DpFechaF.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setCodigoCliente(((Clientes) cmbClienteC.getSelectionModel().getSelectedItem()).getCodigoClientes());
        registro.setCodigoEmpleado(((Empleados) cmbEmpleadoC.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarFactura(?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDeFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.setInt(6, registro.getCodigoCliente());

            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarFactura() {
        switch (tipoOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                tipoOperaciones = operaciones.Actualizar;
                break;
            case Actualizar:
                guardarFactura();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                tipoOperaciones = operaciones.Ninguno;
                CargarDatosFactura();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoOperaciones) {
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
                tipoOperaciones =  operaciones.Ninguno;
                CargarDatosFactura();
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int facturaID = Integer.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getCodigoDeFactura());
        parametros.put("facturaID", facturaID);
        GenerarReportes.mostrarReportes("ReporteFactura.jasper", "Factura", parametros);
    }
    
    public void desactivarControles() {
        txtFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        DpFechaF.setEditable(false);
        cmbClienteC.setDisable(true);
        cmbEmpleadoC.setDisable(true);
    }

    public void activarControles() {
        txtFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFactura.setEditable(true);
        DpFechaF.setEditable(true);
        cmbClienteC.setDisable(false);
        cmbEmpleadoC.setDisable(false);
    }

    public void limpiarControles() {
        txtFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        DpFechaF.setValue(null);
        cmbClienteC.setValue(null);
        cmbEmpleadoC.setValue(null);
    }

    
}

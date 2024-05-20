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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josemelgar.db.Conexion;
import org.josemelgar.system.Principal;
import org.josemelgar.bean.Clientes;

/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class MenuClientesController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        AgregarCliente, EliminarCliente, EditarCliente, ActualizarCliente, Cancelar, Ninguno
    }
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Clientes> ListaCliente;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colNitCliente;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private Button btnAgregarCliente;
    @FXML
    private Button btnEditarCliente;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnEliminarCliente;
    @FXML
    private ImageView imgAgregarCliente;
    @FXML
    private ImageView imgEliminarCliente;
    @FXML
    private ImageView imgActualizarCliente;
    @FXML
    private ImageView imgReportes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosCliente"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void seleccionar() {
        txtCodigoC.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoClientes()));
        txtNit.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNit());
        txtNombreC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNit());
        txtApellidoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidosClientes());
        txtDireccionC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtCorreoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
        txtTelefonoC.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nit"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaCliente = FXCollections.observableList(lista);
    }

    public void agregarCliente() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                activarControles();
                btnAgregarCliente.setText("Guardar");
                btnEliminarCliente.setText("Cancelar");
                btnEditarCliente.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregarCliente.setImage((new Image("/org/josemelgar/images/ImagenGuardar.png")));
                imgEliminarCliente.setImage((new Image("/org/josemelgar/images/ImagenCancelar.png")));
                tipoDeOperaciones = operaciones.ActualizarCliente;
                break;
            case ActualizarCliente:
                guardar();
                desactivarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminarCliente.setText("Eliminar");
                btnEditarCliente.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregarCliente.setImage(new Image("/org/josemelgar/images/ImagenAgregar.png"));
                imgEliminarCliente.setImage(new Image("/org/josemelgar/images/ImagenEliminar.png"));
                tipoDeOperaciones = operaciones.Ninguno;
                break;
        }
    }

    public void guardar() {
        Clientes registro = new Clientes();
        registro.setCodigoClientes(Integer.parseInt(txtCodigoC.getText()));
        registro.setNit(txtNit.getText());
        registro.setNombreClientes(txtNombreC.getText());
        registro.setApellidosClientes(txtApellidoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarClientes(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoClientes());
            procedimiento.setString(2, registro.getNit());
            procedimiento.setString(3, registro.getNombreClientes());
            procedimiento.setString(4, registro.getApellidosClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            ListaCliente.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarCliente() {
        switch (tipoDeOperaciones) {
            case ActualizarCliente:
                desactivarControles();
                limpiarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminarCliente.setText("Eliminar");
                btnEditarCliente.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregarCliente.setImage((new Image("/org/josemelgar/images/ImagenAgregar.png")));
                imgEliminarCliente.setImage((new Image("/org/josemelgar/images/ImagenEliminar.png")));
                tipoDeOperaciones = operaciones.Ninguno;
                break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "seguro quiere eliminar el registro",
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{sp_eliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoClientes());
                            procedimiento.execute();
                            ListaCliente.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "lo siento, tiene que seleccionar un cliente para eliminar");
                }
                break;
        }
    }

    public void editarCliente() {
        switch (tipoDeOperaciones) {
            case Ninguno:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCliente.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregarCliente.setDisable(true);
                    btnEliminarCliente.setDisable(true);
                    imgActualizarCliente.setImage((new Image("/org/josemelgar/images/ImagenGuardar.png")));
                    imgReportes.setImage((new Image("/org/josemelgar/images/ImagenCancelar.png")));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ActualizarCliente;
                } else {
                    JOptionPane.showMessageDialog(null, "lo siento, tiene que seleccionar un cliente para editar");
                }
                break;
            case ActualizarCliente:
                actualizarCliente();
                btnEditarCliente.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregarCliente.setDisable(false);
                btnEliminarCliente.setDisable(false);
                imgActualizarCliente.setImage(new Image("/org/josemelgar/images/editar.png"));
                imgReportes.setImage(new Image("/org/josemelgar/images/reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatos();
                break;
        }
    }

    public void actualizarCliente() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_actualizarCliente (?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNit(txtNit.getText());
            registro.setNombreClientes(txtNombreC.getText());
            registro.setApellidosClientes(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoClientes());
            procedimiento.setString(2, registro.getNit());
            procedimiento.setString(3, registro.getNombreClientes());
            procedimiento.setString(4, registro.getApellidosClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ActualizarCliente:
                desactivarControles();
                limpiarControles();
                btnEditarCliente.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregarCliente.setDisable(false);
                btnEliminarCliente.setDisable(false);
                imgActualizarCliente.setImage(new Image("/org/josemelgar/images/editar.png"));
                imgReportes.setImage(new Image("/org/josemelgar/images/reporte.png"));
                tipoDeOperaciones = operaciones.Ninguno;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtNit.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtTelefonoC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtNit.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtTelefonoC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtNit.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
        txtTelefonoC.clear();
        tblClientes.getSelectionModel().clearSelection();
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

}


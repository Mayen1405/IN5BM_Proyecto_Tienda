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
        AgregarCliente, EliminarCliente, EditarCliente, ActualizarCliente, Cancelar, Ninguno
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
        cargarDatos();
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
        tblCargoE.setItems(getCargoE);
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
}


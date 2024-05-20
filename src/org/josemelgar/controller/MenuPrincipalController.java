
package org.josemelgar.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.josemelgar.system.Principal;

/**
 * Nombre completo:Jose Pablo Melgar Mayen carnet: 2020478 seccion: IN5BM
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnProgramador;
    @FXML
    MenuItem btnMenuCompras;
    @FXML
    MenuItem btnProveedores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.programadorView();
        } else if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuComprasView();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.menuProveedoresView();
        }
    }
}

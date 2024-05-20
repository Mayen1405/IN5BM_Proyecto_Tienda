/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.josemelgar.system.Principal;

/**
 *
 * @author informatica
 */
public class ProgramadorController implements Initializable {

    private Principal escenarioPrincipal;
    @FXML
    private Button btnHome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cargarDatos() {

    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public void desactivarControles() {

    }
}

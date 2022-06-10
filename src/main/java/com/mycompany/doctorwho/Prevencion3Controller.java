/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Cambralla
 */
public class Prevencion3Controller implements Initializable {

    @FXML
    private Pane panel_Cambiar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void alPulsarWeb(MouseEvent event) {
        Web.mostrarEnlace("https://paraulcerasporpresion.com");
    }

    @FXML
    private void alPulsarWebAlmohadillados(MouseEvent event) {
         Web.mostrarEnlace("https://view.genial.ly/61865fb23ec2450e0cdd1db2/presentation-almohadillados-upp-cae");
    }
    
}

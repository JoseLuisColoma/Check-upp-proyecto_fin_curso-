/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 26899630a
 */
public class prevCalculadoraController implements Initializable {

    @FXML
    private Pane prev_calc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startCaclculadora(ActionEvent event) {
        Parent root=null;
        try {
            root=App.loadFXML("calculadora");
        } catch (IOException ex) {
            Logger.getLogger(prevCalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        prev_calc.getChildren().setAll(root);
       
    }

    @FXML
    private void pulsarEnfermeria(MouseEvent event) {
         Web.mostrarEnlace("https://enfermeriacreativa.com/");
    }

    @FXML
    private void pulsrCreative(MouseEvent event) {
         Web.mostrarEnlace("https://creativecommons.org/licenses/by-nc-nd/4.0/");
    }

    
    
    
}

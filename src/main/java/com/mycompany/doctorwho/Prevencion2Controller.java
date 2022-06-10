/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author wendy
 */
public class Prevencion2Controller implements Initializable {

   @FXML
    private Pane panel_Cambiar;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Te manda a la siguiente página.
     *
     * @param event
     */    
    @FXML
    private void alPulsarSiguiente(ActionEvent event) throws IOException {
           Parent root = App.loadFXML("prevencion_3");
//    
        panel_Cambiar.getChildren().setAll(root);
    }

    @FXML
    private void primeraWeb(MouseEvent event) {
         Web.mostrarEnlace("https://www.youtube.com/watch?v=UhmGGa3oQ7Y");
    }

    private void segundaWeb(MouseEvent event) {
         Web.mostrarEnlace("https://fernamor.wixsite.com/hiegiene-postural/cambiosposturales");
    }

    @FXML
    private void youtube(MouseEvent event) {
         Web.mostrarEnlace("https://www.youtube.com/watch?v=m7Os47ISRpg");
    }
    
}

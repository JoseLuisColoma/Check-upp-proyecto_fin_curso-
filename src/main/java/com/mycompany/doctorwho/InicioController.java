/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;


import Modelo.Item;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class. Controlador del menú de inicio.
 *
 * @author Cambralla
 */
public class InicioController implements Initializable {

   
    /**
     * Imagen de las sillas.
     */
    @FXML
    private ImageView img_Sillas;

    /**
     * Imagen de la camilla.
     */
    @FXML
    private ImageView img_camilla;
    @FXML
    private Text txtInicio;
    @FXML
    private Text txtInicio1;
    
    
    private Item item = new Item();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    /**
     * Gestiona lo que ocurrirá cuando pulses un enlace.
     *
     * @param event
     */
    @FXML
    private void alPulsarWeb(MouseEvent event) {
        Web.mostrarEnlace("http://paraulcerasporpresion.com/aceites/tipos-de-ulceras-por-presion/");
    }

}

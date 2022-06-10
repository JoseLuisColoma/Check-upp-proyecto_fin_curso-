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

/**
 * FXML Controller class. Controlador de la interfaz gráfica del menú de enlaces
 * de interés.
 *
 * @author 26899630a
 */
public class ContactoController{

    //Web enlaces = new Web();
//lo he pasado a static para no crear un objeto de esa clase


    /**
     * Muestra el primer enlace.
     *
     * @param event
     */
    @FXML
    private void alPulsarWebPrimera(MouseEvent event) {
        // pero hay que llamar a la clase con su método static
        Web.mostrarEnlace("http://www.hospitalregionaldemalaga.es/Profesionales/%C3%81readeSeguridaddelPaciente/Pr%C3%A1cticasSeguras/UPP.aspx");
    }

    /**
     * Muestra el segundo enlace.
     *
     * @param event
     */

    /**
     * Muestra el tercer enlace.
     *
     * @param event
     */
    @FXML
    private void alPulsarWebTercera(MouseEvent event) {
        Web.mostrarEnlace("https://www.hgc.es/es/pacientes-visitantes/consejos-salud/cuidados-piel/ulcera-presion-upp");
    }

    /**
     * Muestra el cuarto enlace.
     *
     * @param event
     */
  
    @FXML
    private void alPulsarWebTerceraServ(MouseEvent event) {
           Web.mostrarEnlace("https://chguv.san.gva.es/servicios-salud/servicios-unidades/ulceras");
    }

    @FXML
    private void alPulsarWebSegunda(MouseEvent event) {
         Web.mostrarEnlace("https://www.hgc.es/es/pacientes-visitantes/consejos-salud/cuidados-piel/ulcera-presion-upp.ficheros/1378390-011-CORP3.6-P13-A4-Tr%C3%ADptico%20Recomendaciones%20al%20alta%20parapersonas%20con%20riesgo%20UPPV.0.pdf");
    }

}

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
import javafx.scene.text.Text;

/**
 * FXML Controller class. Controlador de la interfaz gráfica de la primera
 * pagina de prevención.
 *
 * @author wendy
 */
public class PrevencionController implements Initializable {

    /**
     * Panel que contiene toda la información de la primera página de prevención
     * de riesgos.
     */
    @FXML
    private Pane panel_Cambiar;
  @FXML
  private Text txtInicio1;

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

//      @FXML
//    private void alPulsarSiguiente(ActionEvent event) throws IOException {
//    }
    /**
     * Te manda a la siguiente página.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void alPulsarSiguiente(ActionEvent event) throws IOException {
        Parent root = App.loadFXML("prevencion_2");
//    
        panel_Cambiar.getChildren().setAll(root);
    }

    @FXML
    private void alPulsarWeb(MouseEvent event) {
        Web.mostrarEnlace("https://www.ulceras.net/monográfico.net/monografico/112/100/ulceras-por-presion-prevencion.html");
    }
}

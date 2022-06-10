package com.mycompany.doctorwho;

import BDA.GestionBaseDatos;
import Modelo.GestionCliente;
import Modelo.Usuario;
import Modelo.Paciente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.Notifications;

public class NavigationController implements Initializable {

    /**
     * Panel que lo contiene todo.
     */
    @FXML
    private Pane braden;

    /**
     * Botón de la sección de inicio.
     */
    @FXML
    private ToggleButton btn_inicio;

    /**
     * Conjunto de todos los botones de la barra lateral.
     */
    @FXML
    private ToggleGroup navegacion;

    /**
     * Botón que te manda a la sección de la calculadora.
     */
    @FXML
    private ToggleButton btn_calcular;

    /**
     * Botón que te manda a la sección de prevención de riesgos.
     */
    @FXML
    private ToggleButton btn_riesgo;

    /**
     * Botón que te manda a la sección de enlaces de interés.
     */
    @FXML
    private ToggleButton btn_contacto;

    /**
     * Panel en el que se cargará la información de cada sección.
     */
    @FXML
    private Pane main_content;
//    private GestionBaseDatos bd;

    /**
     * Texto que muestra el nombre del usuario conectado.
     */
    @FXML
    private Label infoUser;

    /**
     * Texto que muestra el nombre del paciente al que se le está haciendo el
     * test.
     */
    @FXML
    private Label infoPaciente;

    /**
     * Paciente al que se le está realizando el test.
     */
    private static Paciente paciente;

    /**
     * Usuario conectado.
     */
    private Usuario usuario;

    /**
     *
     * @return Devuelve un paciente
     */
    @FXML
    private ImageView home_icon;

    @FXML
    private ToggleButton btn_historial;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_inicio.setSelected(true);

//        btn_riesgo.setDisable(true);
        try {
            setDatosCliente();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        if (GestionBaseDatos.getConexion() == null) {
            errorConexion();
        }
        goHome();

    }

    public static Paciente getPaciente() {
        return paciente;
    }

    /**
     * Te manda a la escena inicial.
     */
    @FXML
    private void goHome() {

        btn_inicio.setSelected(true);
        loadScene("inicio");

    }

    /**
     * Te manda a la calculadora.
     */
    @FXML
    private void goCalculadora() {
        btn_calcular.setSelected(true);
        loadScene("prev_calculadora");
    }

    /**
     * Te manda a la sección de prevención.
     */
    @FXML
    private void goPrevencion() {
        btn_riesgo.setSelected(true);
        //NO ESTA IMPLEMENTADO
        loadScene("prevencion");

    }

    /**
     * Te manda a los enlaces de interés.
     */
    @FXML
    private void goContacto() {
        btn_contacto.setSelected(true);
        loadScene("contacto");

    }

    @FXML
    private void goHistorial(ActionEvent event) {
        btn_historial.setSelected(true);
        loadScene("historial");
    }

    /**
     * Carga una escena.
     *
     * @param page
     */
    private void loadScene(String page) {

        Parent root = null;

        try {
            root = App.loadFXML(page);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        main_content.getChildren().setAll(root);

    }

    /**
     * Avisa de que no se ha podido conectar al servidor.
     */
    private void errorConexion() {
        btn_calcular.setDisable(true);
        btn_riesgo.setDisable(true);
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("ERROR");
        alerta.setHeaderText("ERROR CONEXION SERVIDOR");
        alerta.setContentText("No hemos podido conectarnos al servidor asegurate de que estas en la red de CIPFP MISLATA, la Calculadora No funcionara");
        alerta.showAndWait();
    }

    /**
     * Modifica los datos de un cliente.
     *
     * @throws SQLException
     */
    private void setDatosCliente() throws SQLException {

        try {
            usuario = GestionCliente.getUsuario();
            paciente = GestionCliente.getPaciente();

            infoUser.setText("USUARIO: " + usuario.getNombre());
            infoPaciente.setText("PACIENTE: " + paciente.getNombre());
        } catch (NullPointerException e) {

            System.out.println("Cusa:" + e.getClass() + "  \nerr" + e.getMessage());
        }

    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        
        if (!askLogOut()) {
            return;
        }

        try {
            App.setRoot("login");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            GestionBaseDatos.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    private boolean askLogOut(){
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar Sesion");
        alerta.setHeaderText("");
        alerta.setContentText("¿Estas seguro de que quieres Cerrar Sesión?");
        ButtonType SI=new ButtonType("SI");
        alerta.getButtonTypes().setAll(SI,new ButtonType("NO"));
        Optional<ButtonType> respuesta = alerta.showAndWait();
        
        return respuesta.get()==SI;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import BDA.GestionBaseDatos;
import BDA.ItemDAO;
import BDA.PacienteDAO;
import BDA.ResultadosDAO;
import BDA.SituacionesDAO;
import BDA.UsuarioDAO;
import Modelo.GestionCliente;
import Modelo.Paciente;
import Modelo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author 26899630a
 */
public class LoginController implements Initializable {

    /**
     * Panel que lo contiene todo.
     */
    @FXML
    private Pane braden;

    /**
     * Campo de texto que recoge el nombre del usuario.
     */
    @FXML
    private TextField userName;

    /**
     * Campo que recoge la contraseña.
     */
    @FXML
    private PasswordField password;

    @FXML
    private Button btn_login;
    @FXML
    private Button btn_registrarse;
    @FXML
    private Label textoResgistro;
    @FXML
    private Label registroText;
    @FXML
    private Text nombre;
    @FXML
    private TextField field_nombre;
    @FXML
    private Text txt_email;
    @FXML
    private Text txt_passwd;
    @FXML
    private Button btn_registroPaciente;
    @FXML
    private TextField fechaNacPac;

    @FXML
    private Label titulo_registro;
    @FXML
    private Button btn_cancelar;

    private String correo;
    private String passwdUsuario;
    private String nombreUsuario;
    /**
     * Objeto que permite buscar un usuario.
     */
    private UsuarioDAO userDAO = new UsuarioDAO();

    /**
     * Objeto que permite buscar un paciente.
     */
    private PacienteDAO pacienteDAO = new PacienteDAO();

    /**
     * Objeto que hace posible la gestión de los clientes.
     */
    private GestionCliente gc = new GestionCliente();
    private SituacionesDAO situacionesDAO = new SituacionesDAO();
    private ItemDAO itemsDAO = new ItemDAO();
    private ResultadosDAO resultados = new ResultadosDAO();

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
     * Gestiona el acceso a la aplicación.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    private void acceder(ActionEvent event) throws SQLException {

        correo = userName.getText();
        passwdUsuario = password.getText();

        if (!checkLogin()) {
            setColorField(0);
            return;
        }
        setColorField(1);

        boolean conectado = GestionBaseDatos.conectar();

        if (!conectado) {
            showAlerta("Inicio Sesion", "No hemos podido acceder revisa que estes en CIPFP MISLATA");
            return;

        }

        try {

            Usuario user = userDAO.getUsuario(correo, passwdUsuario);

            Paciente paciente = pacienteDAO.getPaciente(user.getId());

            if (!showCondiciones()) {
                showAlerta("TERMINOS Y CONDICIONES", "ES NECESARIO ACEPTAR LOS TERMINOS Y CONDICIONES");
                return;
            }

            gc.setUsuario(user);
            gc.setPaciente(paciente);

            try {
                itemsDAO.setImgtems();
                situacionesDAO.setImgSituaciones();

                resultados.getAllTest(paciente.getId());

                App.setRoot("navigation");

            } catch (IOException ex) {
                showAlerta("Inicio Sesion", "No hemos podido acceder");
            }

        } catch (SQLException ex) {

            setColorField(0);

            showAlerta("Inicio Sesion", "CONTRASEÑA O CORREO incorrectos");

            GestionBaseDatos.desconectar();

        }

    }

    /**
     * Te manda al menú del registro.
     *
     * @param event
     */
    @FXML
    private void registrarse(MouseEvent event) {
      registrar();

    }
    
    
    private void registrar(){
          btn_login.setDisable(true);
        btn_login.setVisible(false);
        registroText.setDisable(true);
        textoResgistro.setVisible(false);
        registroText.setVisible(false);
        userName.setText("");
        password.setText("");
        btn_registrarse.setDisable(false);
        btn_registrarse.setVisible(true);
        nombre.setVisible(true);
        field_nombre.setVisible(true);
        field_nombre.setDisable(false);

        btn_cancelar.setDisable(false);
        btn_cancelar.setVisible(true);

        titulo_registro.setText("Datos Usuario");
        setColorField(1);
    }

    /**
     * Te registra en la base de datos de la aplicación.
     *
     * @param event
     */
    @FXML
    private void registro(ActionEvent event) {

        correo = userName.getText();
        passwdUsuario = password.getText();
        nombreUsuario = field_nombre.getText();

        if (!checkSignUp()) {
            return;
        }

        try {
            GestionBaseDatos.conectar();

            if (!userDAO.existeUsuario(correo)) {

                userDAO.insertarUsuario(nombreUsuario, "", "", correo, passwdUsuario);
                setRegistroPaciente();

            } else {
                showAlerta("Usuario", "Ya Existe un Usuario con ese correo");
                GestionBaseDatos.desconectar();
                volverLogin();
            }

        } catch (SQLException ex) {
            showAlerta("Usuario", "No se ha podido crear el usuario");
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void registrarPac(ActionEvent event) {

        crearPaciente(correo, passwdUsuario);
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
        volverLogin();
    }

    /**
     * Añade un paciente a la base de datos.
     *
     * @param correoUsuario
     * @param passwdUsuario
     */
    private void crearPaciente(String correoUsuario, String passwdUsuario) {

        DateTimeFormatter patronFechaSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter patronFechaEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String nombrePaciente = userName.getText();
        String fechaEntrada = fechaNacPac.getText();

        if (!checkDatosPaciente()) {
            return;
        }

        LocalDate fechaFormato = null;
        try {
            fechaFormato = LocalDate.parse(fechaEntrada, patronFechaEntrada);
        } catch (DateTimeParseException e) {
            showAlerta("Fecha", "Introduce una FECHA siguiendo el formato 02/06/2005");
            return;
        }

        if (fechaFormato.isAfter(LocalDate.now())) {
            showAlerta("FECHA", "La fecha no puede ser superior a la de hoy");
        }

        LocalDate fecha = LocalDate.parse(fechaFormato.toString(), patronFechaSQL);

        try {
            int idUser = userDAO.getUsuario(correoUsuario, passwdUsuario).getId();
            pacienteDAO.insertarPaciente(nombrePaciente, fecha, idUser);
            volverLogin();
        } catch (SQLException ex) {
            showAlerta("Paciente", "No se ha podido crear al paciente");
            volverLogin();

        }

    }

    /**
     * Te devuelve a la pantalla de inicio de sesión.
     */
    private void volverLogin() {

        txt_email.setText("Email");
        txt_passwd.setText("Contraseña");
        userName.setText("");

        userName.setPromptText("ejemplo@gmail.com");
        password.setText("");
        password.setText("Contraseña");
        titulo_registro.setText("Iniciar Sesion");
        textoResgistro.setVisible(true);
        registroText.setVisible(true);
        registroText.setDisable(false);

        password.setVisible(true);
        password.setDisable(false);
        password.setText("");
        nombre.setVisible(false);

        btn_registroPaciente.setVisible(false);
        btn_registroPaciente.setDisable(true);
        btn_registrarse.setVisible(false);
        btn_registrarse.setDisable(true);

        fechaNacPac.setVisible(false);
        fechaNacPac.setDisable(true);

        field_nombre.setDisable(true);
        field_nombre.setVisible(false);

        btn_login.setDisable(false);
        btn_login.setVisible(true);

        btn_cancelar.setDisable(true);
        btn_cancelar.setVisible(false);

    }

    /**
     * Cambia a la pantalla del registro.
     */
    private void setRegistroPaciente() {
        btn_cancelar.setDefaultButton(true);
        btn_cancelar.setVisible(false);
        fechaNacPac.setVisible(true);
        fechaNacPac.setDisable(false);

        titulo_registro.setText("Datos Paciete");

        txt_email.setText("Nombre paciente");

        txt_passwd.setText("Fecha Nacimiento Paciente");

        userName.setPromptText("");

        password.setDisable(true);
        password.setVisible(false);

        btn_registroPaciente.setVisible(true);
        btn_registroPaciente.setDisable(false);

        field_nombre.setDisable(true);
        field_nombre.setVisible(false);
        btn_registrarse.setVisible(false);
        btn_registrarse.setDisable(true);
        nombre.setVisible(false);
        userName.setText("");
    }

    private boolean checkLogin() {

        boolean todoBien = true;

        if (passwdUsuario.isBlank()) {
            showAlerta("DATOS Vacios", "La CONTRASEÑA esta vacia");
            todoBien = false;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[a-z]+(.com)$")) {
            showAlerta("CORREO", "Introduce un correo valido");
            todoBien = false;
        }

        return todoBien;

    }

    private boolean checkSignUp() {
        boolean datosOk = true;

        if (passwdUsuario.isBlank()) {
            showAlerta("DATOS", "La CONTRASEÑA esta vacia");
            datosOk = false;
        }

        if (nombreUsuario.isBlank()) {
            showAlerta("DATOS", "El NOMBRE esta vacio");
            datosOk = false;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[a-z]+(.com)$")) {
            showAlerta("CORREO", "Introduce un correo valido");
            datosOk = false;
        }
        return datosOk;
    }

    private boolean checkDatosPaciente() {
        boolean datosOk = true;
        if (userName.getText().isBlank()) {
            showAlerta("DATOS", "El NOMBRE esta vacio");
            datosOk = false;
        }

        if (fechaNacPac.getText().matches("^[0]$")) {
            showAlerta("CORREO", "Introduce un correo valido");
            datosOk = false;
        }
        return datosOk;
    }

    private void showAlerta(String titulo, String contenido) {
        Notifications.create()
                .title(titulo)
                .text(contenido)
                .position(Pos.CENTER)
                .showError();
    }

    private void setColorField(int resultado) {
        final int INCORRECTO = 0;

        if (resultado == INCORRECTO) {
            userName.setStyle("-fx-border-color:red;");
            password.setStyle("-fx-border-color:red;");
        } else {
            userName.setStyle("-fx-border-color:#00d900;");
            password.setStyle("-fx-border-color:#00d900;");
        }
    }

    private boolean showCondiciones() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        ButtonType botonSI = new ButtonType("Aceptar");
        ButtonType botonNO = new ButtonType("Rechazar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.setTitle("Avisos legales");
        alerta.setWidth(1000);
        alerta.setHeight(700);
        alerta.setX(1.5);
        alerta.setY(100.0);

        alerta.setContentText("· Aplicación con fines educativos dirigida a: médicos, estudiantes de medicina, enfermería, auxiliares de enfermería, personal paramédico y demás áreas afines a la salud.\n\n"
                + "· La siguiente información se aplica a CHECK UPP Al utilizar esta Aplicación de escritorio aceptas este descargo de responsabilidad.\n\n"
                + "· La información y declaraciones hechas son para propósitos de educación y no pretenden sustituir el consejo de su médico. Los autores y el administrador de esta aplicación no asumen ninguna responsabilidad en el uso de esta información.\n\n"
                + "· CHECK UPP no dispensa consejos médicos, recetas o  diagnostica enfermedades. Las opiniones y consejos de nutrición expresados no están destinados a ser un sustituto para el servicio médico convencional.\n\n"
                + "· Si usted tiene una condición médica grave, consulte a su médico. Esta app contiene publicidad y enlaces a sitios web operados por otras partes. Estos vínculos se proporcionan por un tercero. No somos responsables por el contenido o los productos de cualquier otro sitio.\n\n");

        alerta.getButtonTypes().setAll(botonSI, botonNO);
        Optional<ButtonType> respuesta = alerta.showAndWait();

        return respuesta.get() == botonSI;
    }

}

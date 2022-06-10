/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import Modelo.GestionCliente;
import BDA.ResultadosDAO;
import Modelo.Item;
import Modelo.Paciente;
import Modelo.Resultado;
import Modelo.Situacion;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class. Controlador de la interfaz gráfica de la calculadora.
 *
 * @author Gabriel
 */
public class CalculadoraController implements Initializable {

    /**
     * Primera imagen.
     */
    @FXML
    private ImageView img1;

    /**
     * Segunda imagen.
     */
    @FXML
    private ImageView img2;

    /**
     * Tercera imagen.
     */
    @FXML
    private ImageView img3;

    /**
     * Cuarta imagen.
     */
    @FXML
    private ImageView img4;

    /**
     * Texto que muestra el nivel de riesgo.
     */
    @FXML
    private Label riesgoFinal;

    /**
     * Paciente que realiza el test.
     */
    private Paciente paciente;

    /**
     * Gestor de los clientes.
     */
    private GestionCliente gc;

    /**
     * Fecha en la que se realiza el test.
     */
    private LocalDate fecha;

    /**
     * Lista que contiene la puntuación obtenida en cada una de las categorías.
     */
    private int[] listaPuntos = new int[6];

    /**
     * Puntos obtenidos en la sección actual.
     */
    private int puntosSeccion;

    /**
     * Ventana emergente.
     */
    private Alert alerta;

    /**
     * Puntuación total del test.
     */
    private int puntuacionTotal;

    /**
     * Indicador de la sección en la que se encuentra el usuario.
     */
    private int contadorSecciones = 1;

    /**
     * Opacidad establecida cuando la imagen está seleccionada.
     */
    private final int SELECCIONADO = 1;

    /**
     * Opacidad establecida a todos los elementos excepto al que está
     * seleccionado.
     */
    private final double NO_SELECCIONADO = 0.5;

    /**
     * Opacidad predeterminada.
     */
    private final double INICIAL = 0.8;

    /**
     * Instancia de la clase que gestiona los resultados del test.
     */
    private ResultadosDAO resultadoDAO = new ResultadosDAO();
    private Item item = new Item();
    private Situacion situacion = new Situacion();

    /**
     * Identificador del nivel de riesgo.
     */
    private int idRiesgo;

    /**
     * Botón para pasar de página.
     */
    @FXML
    private Button btn_siguiente;

    /**
     * Puntuación de cada sección.
     */
    private int posicionListaPuntos = 0;

    /**
     * Imagen que muestra el logo de la categoría junto con su título.
     */
    @FXML
    private ImageView itemTitulo;

    /**
     *
     */
    @FXML
    private Text pregunta;
    @FXML
    private Text titulo;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            imgPercepcion();
            opacidadInicial();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
        }

        gc = new GestionCliente();
        paciente = NavigationController.getPaciente();

        riesgoFinal.setDisable(true);

    }

    /**
     * Reduce la opacidad a la mitad a todas las imágenes y luego solo se la
     * aumenta a la imagen seleccionada.
     */
    private void opacidadImagenClick(ImageView img) {

        img1.setOpacity(NO_SELECCIONADO);
        img2.setOpacity(NO_SELECCIONADO);
        img3.setOpacity(NO_SELECCIONADO);
        img4.setOpacity(NO_SELECCIONADO);

        img.setOpacity(SELECCIONADO);
    }

    /**
     * Selecciona la primera imagen.
     */
    @FXML
    private void selectImg1(MouseEvent event) {
        opacidadImagenClick(img1);

    }

    /**
     * Selecciona la segunda imagen.
     */
    @FXML
    private void selectImg2(MouseEvent event) {
        opacidadImagenClick(img2);

    }

    /**
     * Selecciona la tercera imagen.
     */
    @FXML
    private void selectImg3(MouseEvent event) {
        opacidadImagenClick(img3);

    }

    /**
     * Selecciona la cuarta imagen.
     */
    @FXML
    private void selectImg4(MouseEvent event) {
        opacidadImagenClick(img4);

    }

    /**
     * Permite al usuario avanzar a la siguiente página.
     */
    @FXML
    private void irSiguiente(ActionEvent event) throws SQLException, IOException {

        final int ULTIMA_SECCION = 6, REINICIO = 0;

        if (img1.getOpacity() == SELECCIONADO && img2.getOpacity() == SELECCIONADO
                && img3.getOpacity() == SELECCIONADO && img4.getOpacity() == SELECCIONADO) {
            return;
        }

        if (img1.getOpacity() != SELECCIONADO && img2.getOpacity() != SELECCIONADO
                && img3.getOpacity() != SELECCIONADO && img4.getOpacity() != SELECCIONADO) {
            return;
        }

        contadorSecciones++;

        if (contadorSecciones == ULTIMA_SECCION) {
            img4.setDisable(true);

        } else {
            img4.setDisable(false);

        }
        sumarPuntos(contadorSecciones);

        if (contadorSecciones > ULTIMA_SECCION) {
            posicionListaPuntos = REINICIO;
            btn_siguiente.setDisable(true);
            btn_siguiente.setVisible(false);

            cleanCalculadora();
            calcularRiesgo();

            if (askSaveTest()) {
                guardarTest();
            }

        }

        try {
            setImagenesCalc(contadorSecciones);
        } catch (IOException | SQLException s) {
            System.out.println(s.getMessage());

        }
        opacidadInicial();

    }

    /**
     * Coloca unas imágenes u otras dependiendo de la sección en la que se
     * encuentra el usuario.
     *
     * @param seccion Sección del test en la que se encuentra el usuario.
     */
    private void setImagenesCalc(int seccion) throws SQLException, IOException {

        if (seccion == 1) {
            imgPercepcion();

        }
        if (seccion == 2) {
            imgHumedad();

        }
        if (seccion == 3) {
            imgActividad();

        }
        if (seccion == 4) {
            imgMovilidad();

        }
        if (seccion == 5) {
            imgNutricion();

        }
        if (seccion == 6) {
            imgFriccion();
        }

    }

    /**
     * Pregunta al usuario si quiere guardar el test.
     */
    private boolean askSaveTest() {
        alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("TEST");
        alerta.setHeaderText("GUARDAR RESULTADO");
        alerta.setContentText("DESEA GUARDAR EL RESULTADO DEL TEST REALIZADO?");
        Optional<ButtonType> result = alerta.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Coloca las imágenes del apartado de percepción sensorial.
     */
    private void imgPercepcion() throws SQLException, IOException {
        itemTitulo.setImage(item.getItem("Percepcion sensorial"));
        titulo.setText("PERCEPCIÓN SENSORIAL");
        pregunta.setText("¿Cuánta capacidad de reacción conserva?");

        img1.setImage(situacion.getSituacion("P1"));
        img2.setImage(situacion.getSituacion("P2"));
        img3.setImage(situacion.getSituacion("P3"));
        img4.setImage(situacion.getSituacion("P4"));
    }

    /**
     * Coloca las imágenes de la sección de exposición a la humedad.
     */
    private void imgHumedad() throws SQLException, IOException {
        itemTitulo.setImage(item.getItem("Exposición a la humedad"));
        titulo.setText("EXPOSICIÓN A LA HUMEDAD");
        pregunta.setText("¿Con qué frecuencia se encuentra humedo?");
        img1.setImage(situacion.getSituacion("H1"));
        img2.setImage(situacion.getSituacion("H2"));
        img3.setImage(situacion.getSituacion("H3"));
        img4.setImage(situacion.getSituacion("H4"));
    }

    /**
     * Coloca las imágenes de la sección de actividad física.
     */
    private void imgActividad() throws SQLException, IOException {

        itemTitulo.setImage(item.getItem("Actividad"));
        titulo.setText("ACTIVIDAD FÍSICA");
        pregunta.setText("¿Qué nivel de actividad física tiene?");
        img1.setImage(situacion.getSituacion("A1"));
        img2.setImage(situacion.getSituacion("A2"));
        img3.setImage(situacion.getSituacion("A3"));
        img4.setImage(situacion.getSituacion("A4"));
    }

    /**
     * Coloca las imágenes del apartado de movilidad.
     */
    private void imgMovilidad() throws SQLException, IOException {

        itemTitulo.setImage(item.getItem("Movilidad"));
        titulo.setText("MOVILIDAD");
        pregunta.setText("¿Puede cambiar de postura por sí mismo?");
        img1.setImage(situacion.getSituacion("M1"));
        img2.setImage(situacion.getSituacion("M2"));
        img3.setImage(situacion.getSituacion("M3"));
        img4.setImage(situacion.getSituacion("M4"));
    }

    /**
     * Coloca las imágenes del apartado de nutrición.
     */
    private void imgNutricion() throws SQLException, IOException {

        itemTitulo.setImage(item.getItem("Nutrición"));
        titulo.setText("NUTRICIÓN");
        pregunta.setText("¿Tiene una alimentación o hidratación adecuada?");
        img1.setImage(situacion.getSituacion("N1"));
        img2.setImage(situacion.getSituacion("N2"));
        img3.setImage(situacion.getSituacion("N3"));
        img4.setImage(situacion.getSituacion("N4"));
    }

    /**
     * Coloca las imágenes de la sección de fricción y cizallamiento.
     */
    private void imgFriccion() throws SQLException, IOException {

        itemTitulo.setImage(item.getItem("Fricción y cizallamiento"));
        titulo.setText("FRICCIÓN CIZALLAMIENTO");
        pregunta.setText("¿Su piel roza habitualmente con otras superficies?");
        img1.setImage(situacion.getSituacion("F1"));
        img2.setImage(situacion.getSituacion("F2"));
        img3.setImage(situacion.getSituacion("F3"));
        img3.setPreserveRatio(false);
        img3.setFitWidth(300);
        img4.setImage(null);

    }

    /**
     * Establece la opacidad inicial a 1, el valor por defecto.
     */
    private void opacidadInicial() {
        img1.setOpacity(INICIAL);
        img2.setOpacity(INICIAL);
        img3.setOpacity(INICIAL);
        img4.setOpacity(INICIAL);
    }

    /**
     * Limpia la interfaz gráfica de la sección de la calculadora.
     */
    private void cleanCalculadora() {
        riesgoFinal.setDisable(false);
        img1.setImage(null);
        img2.setImage(null);
        img3.setImage(null);
        img4.setImage(null);
        titulo.setText(null);
        pregunta.setText(null);
        itemTitulo.setImage(null);

        img1.setDisable(true);
        img2.setDisable(true);
        img3.setDisable(true);
        img4.setDisable(true);

    }

    /**
     * Calcula el riesgo de sufrir úlceras por presión según la puntuación
     * obtenida en el test.
     */
    private void calcularRiesgo() {

        final int BAJO = 1, MODERADO = 2, ALTO = 3, SIN_RIESGO = 4;

        int edad = paciente.getEdad();

        String riesgo = "SIN RIESGO";
        int idRiesgoTemporal = SIN_RIESGO;

        if (puntuacionTotal < 12) {

            idRiesgoTemporal = ALTO;
            riesgo = "ALTO";

        }
        if (puntuacionTotal >= 12 && puntuacionTotal <= 14) {

            riesgo = "MODERADO";
            idRiesgoTemporal = MODERADO;

        }
        if (puntuacionTotal >= 15 && puntuacionTotal <= 16 && (edad < 75)) {

            riesgo = "BAJO";
            idRiesgoTemporal = BAJO;

        }
        if (puntuacionTotal >= 15 && puntuacionTotal <= 18 && (edad >= 75)) {

            riesgo = "BAJO";
            idRiesgoTemporal = BAJO;

        }

        riesgoFinal.setText("Tu Riesgo es: " + riesgo);
        this.idRiesgo = idRiesgoTemporal;
    }

    /**
     * Suma una cantidad de puntos determinada u otra según cuál haya sido la
     * imagen seleccionada en cada sección.
     *
     * @param seccion Categoría a la que el usuario está respondiendo.
     */
    private void sumarPuntos(int seccion) {

        if (img1.getOpacity() == SELECCIONADO) {
            puntuacionTotal += 1;
            puntosSeccion = 1;

        }
        if (img2.getOpacity() == SELECCIONADO) {
            puntuacionTotal += 2;
            puntosSeccion = 2;
        }
        if (img3.getOpacity() == SELECCIONADO) {
            puntuacionTotal += 3;
            puntosSeccion = 3;
        }
        if (img4.getOpacity() == SELECCIONADO) {
            puntuacionTotal += 4;
            puntosSeccion = 4;
        }
        listaPuntos[posicionListaPuntos] = puntosSeccion;
        posicionListaPuntos++;

    }

    /**
     * Guarda los resultados obtenidos en el test en un nuevo objeto resultado y
     * se lo manda a ResultadoDAO para que los inserte en la base de datos.
     */
    private void guardarTest() {
        fecha = LocalDate.now();

        Resultado test;

        try {
            test = new Resultado(fecha, paciente, listaPuntos, idRiesgo);

            resultadoDAO.insertarTest(test);

            GestionCliente.resetHistorial();
            resultadoDAO.getAllTest(paciente.getId());

            showAlerta("TEST", "Test Guardado");

        } catch (SQLException ex) {

            if (ex.getMessage().contains("Duplicate entry")) {
                showAlerta("TEST", "SOLO SE PUEDE HACER UN TEST AL DIA");
            } else {
                showAlerta("TEST", "NO SE HA PODIDO GUARDAR EL RESULTADO");
            }

            System.out.println(ex.getMessage());
        }

    }

    /**
     * Se encarga de mostrar mensajes en ventanas emergentes.
     *
     * @param tipo Tipo de mensaje a mostrar. (Información, error, confirmación,
     * advertencia o ninguno)
     * @param cabecera Texto que se ha de mostrar como cabecera.
     * @param titulo Título del mensaje.
     * @param contenido Contenido del mensaje.
     */
    private void showAlerta(String titulo, String contenido) {
        Notifications.create()
                .title(titulo)
                .text(contenido)
                .position(Pos.CENTER)
                .show();

    }

    /**
     * Muestra la pregunta aclaratoria.
     *
     * @param event
     */
    @FXML
    private void showPregunta(MouseEvent event) {

        pregunta.setVisible(true);

    }

    /**
     * Esconde la pregunta aclaratoria.
     *
     * @param event
     */
    @FXML
    private void hidePregunta(MouseEvent event) {
        pregunta.setVisible(false);
    }

}

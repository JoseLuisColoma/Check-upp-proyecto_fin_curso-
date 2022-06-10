/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import BDA.ResultadosDAO;
import Modelo.GestionCliente;
import Modelo.ResultadoTableView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Cambralla
 */
public class HistorialController implements Initializable {

    @FXML
    private TableView<ResultadoTableView> tablaResultados;
    @FXML
    private TableColumn<ResultadoTableView, String> fecha;
    @FXML
    private TableColumn<ResultadoTableView, Integer> paciente;
    @FXML
    private TableColumn<ResultadoTableView, Integer> pt;
    @FXML
    private TableColumn<ResultadoTableView, Integer> percepcion;
    @FXML
    private TableColumn<ResultadoTableView, Integer> humedad;
    @FXML
    private TableColumn<ResultadoTableView, Integer> actividad;
    @FXML
    private TableColumn<ResultadoTableView, Integer> movilidad;
    @FXML
    private TableColumn<ResultadoTableView, Integer> nutricion;
    @FXML
    private TableColumn<ResultadoTableView, Integer> friccion;
    @FXML
    private TableColumn<ResultadoTableView, String> riesgo;
    @FXML
    private DatePicker calendario;
    @FXML
    private Pane paneHis;

    private ObservableList<ResultadoTableView> listResultados = FXCollections.observableArrayList();
    private Set<ResultadoTableView> exportados = new HashSet();
    private Set<ResultadoTableView> allTest;
    private Alert alerta = new Alert(AlertType.CONFIRMATION);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calendario.setValue(LocalDate.now());
        calendario.setEditable(false);

        setResultados();

    }

    @FXML
    private void alPulsarFiltrar(ActionEvent event) {
        Set<ResultadoTableView> historialFiltrado = new HashSet<>();

        LocalDate fechaSeleccionada = calendario.getValue().plusDays(1);

        for (ResultadoTableView resultado : GestionCliente.getHistorialTest()) {

            if (resultado.getFecha().isBefore(fechaSeleccionada)) {
                historialFiltrado.add(resultado);
            }

        }
        listResultados.setAll(historialFiltrado);

    }

    private void setResultados() {

        allTest = GestionCliente.getHistorialTest();
        listResultados.setAll(allTest);

        tablaResultados.setItems(listResultados);

        try {
            fecha.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));
            paciente.setCellValueFactory(new PropertyValueFactory<>("pacienteNombre"));
            pt.setCellValueFactory(new PropertyValueFactory<>("puntosTotales"));
            percepcion.setCellValueFactory(new PropertyValueFactory<>("p1"));
            humedad.setCellValueFactory(new PropertyValueFactory<>("p2"));
            actividad.setCellValueFactory(new PropertyValueFactory<>("p3"));
            movilidad.setCellValueFactory(new PropertyValueFactory<>("p4"));
            nutricion.setCellValueFactory(new PropertyValueFactory<>("p5"));
            friccion.setCellValueFactory(new PropertyValueFactory<>("p6"));
            riesgo.setCellValueFactory(new PropertyValueFactory<>("riesgo"));
        } catch (Exception e) {
            Notifications.create().title("RESULTADOS").text("No hemos podido encontrar el historico de test").showError();
        }
    }

    @FXML
    private void exportar(ActionEvent event) {

        if (askCancel()) {
            return;
        }

        setExportFile();

        String nombrePaciente = GestionCliente.getPaciente().getNombre().toUpperCase();
        String nombreArchivo = nombrePaciente + "_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".txt";

        String ruta = "";

        DirectoryChooser dirChooser = new DirectoryChooser();
        Stage stage = (Stage) paneHis.getScene().getWindow();

        File file = dirChooser.showDialog(stage);

        if (file == null) {
            return;
        }

        ruta = file.getAbsolutePath() + "\\" + nombreArchivo;

        writeExport(ruta);

    }

    private void writeExport(String ruta) {
        Path directorio = Paths.get(ruta);
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyy 'a las' H:m");
        String cabecera = "HISTORIAL RESULTADOS del " + LocalDateTime.now().format(formatoFechaHora);

        if (directorio.toFile().exists() && !checkOverWrite()) {

            return;
        }

        try (BufferedWriter out = Files.newBufferedWriter(directorio,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            out.write(cabecera);
            out.newLine();
            out.newLine();

            for (ResultadoTableView resultado : exportados) {
                out.write(resultado.toString());
                out.newLine();
            }

            Notifications.create()
                    .title("EXPORTACION")
                    .text("Todo ha ido de manera correcta, el archivo se encuentra en " + ruta)
                    .position(Pos.CENTER)
                    .showInformation();

        } catch (IOException e) {

            Notifications.create()
                    .title("ERROR EXPORTACION")
                    .text("NO SE HA PODIDO EXPORTAR")
                    .position(Pos.CENTER)
                    .showError();
        }

    }

    private boolean checkOverWrite() {

        boolean OverWrite = false;

        alerta.setTitle("EXPORTACION ARCHIVO");
        alerta.setHeaderText("EXPORTACION");
        alerta.setContentText("Ya existe este archivo ¿Quieres sobreescribirlo?");
        Optional<ButtonType> result = alerta.showAndWait();

        if (result.get() == ButtonType.OK) {
            OverWrite = true;
        }
        return OverWrite;

    }

    private void setExportFile() {

        ButtonType FILTRADOS = new ButtonType("FILTRADOS");
        ButtonType TODOS = new ButtonType("TODOS");

        alerta.setTitle("EXPORTAR");
        alerta.setContentText("Exportar Historial Test");
        alerta.setContentText("¿Quieres exportar los test filtrados o todos?");
        alerta.getButtonTypes().setAll(FILTRADOS, TODOS);

        Optional<ButtonType> result = alerta.showAndWait();

        if (result.get() == FILTRADOS) {
            tablaResultados.getItems().forEach(test -> {
                exportados.add(test);
            });
        } else {
            exportados.addAll(allTest);
        }

    }

    private boolean askCancel() {

        ButtonType CANCELAR = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.setTitle("EXPORTAR");
        alerta.setContentText("Exportar Historial Test");
        alerta.setContentText("¿Estas seguro/a de que quires continuar?");
        alerta.getButtonTypes().setAll(new ButtonType("CONTINUAR"), CANCELAR);

        Optional<ButtonType> result = alerta.showAndWait();

        return result.get() == CANCELAR;

    }
}

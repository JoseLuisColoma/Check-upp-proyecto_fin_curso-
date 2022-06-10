package com.mycompany.doctorwho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * Escena actual.
     */
    private static Scene scene;

    /**
     * Cargador de FXML
     */
    private static FXMLLoader fxmlLoader;

    /**
     * Inicia la aplicación.
     *
     * @param stage Ventana de la aplicación.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("login"));

        stage.setScene(scene);
        stage.getIcons().add(new Image("\\Imagenes\\1048492.png"));


        stage.setResizable(false);
        stage.show();
        stage.setTitle("CHECK UPP");
    }

    /**
     * Establece un fichero fxml como raíz de la ventana.
     *
     * @param fxml
     * @throws IOException
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carga un fichero fxml.
     *
     * @param fxml Nombre del archivo fxml a utilizar.
     * @return
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException {
        /*FXMLLoader*/ fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     *
     * @return Devuelve un objeto de la clase FxmlLoader.
     */
    public static FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    /**
     * Método principal desde el que siempre se inicia una aplicación Java.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}

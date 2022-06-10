/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import Modelo.Item;
import Modelo.Situacion;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Cambralla
 */
public class SituacionesDAO {

    private Situacion situacion = new Situacion();

    /**
     * Método para descargar todas las situaciones de la base de datos
     *
     * @throws SQLException
     * @throws IOException
     */
    public void setImgSituaciones() throws SQLException, IOException {

        String sentencia = "SELECT * FROM situaciones";

        Image imagenFinal = null;

        try (PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Blob imagenSQL = rs.getBlob("imagen");

                    String nombre = rs.getString("nombre");

                    InputStream in = imagenSQL.getBinaryStream();

                    BufferedImage imagen = ImageIO.read(in);
                    try {
                        imagenFinal = SwingFXUtils.toFXImage(imagen, null);
                    } catch (NullPointerException e) {

                    }

                    situacion.addSituacion(nombre, imagenFinal);

                }

            } catch (SQLException e) {
                Notifications.create().title("IMAGENES").text("no se han podido cargar las imagenes de situaciones").showError();
            }

        } catch (SQLException | IOException sql) {
            Notifications.create().title("IMAGENES").text("no se han podido cargar las imagenes de situaciones").showError();
        }

    }

}

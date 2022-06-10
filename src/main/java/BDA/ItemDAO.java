/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import Modelo.Item;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Cambralla
 */
public class ItemDAO {

    private Item item = new Item();

    /**
     * Método para descargar todas los items de la base de datos
     *
     *
     * @throws SQLException
     * @throws IOException
     */
    public void setImgtems() throws SQLException, IOException {

        String sentencia = "SELECT * FROM items";

        try (PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Blob imagenSQL = rs.getBlob("imagen");

                    String nombre = rs.getString("nombre");
                    InputStream in = imagenSQL.getBinaryStream();

                    BufferedImage imagen = ImageIO.read(in);

                    Image imagenFinal = null;

                    try {
                        imagenFinal = SwingFXUtils.toFXImage(imagen, null);
                    } catch (NullPointerException e) {

                    }

                    item.addItem(nombre, imagenFinal);
                }

            } catch (SQLException e) {
                Notifications.create().title("IMAGENES").text("no se han podido cargar las imagenes de items").showError();
            }

        } catch (SQLException | IOException sql) {
            Notifications.create().title("IMAGENES").text("no se han podido cargar las imagenes de items").showError();
        }

    }

}

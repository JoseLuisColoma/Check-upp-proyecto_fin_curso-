/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

/**
 * Clase que se encarga de gestionar la conexión a la base de datos.
 *
 * @author 26899630a
 */
public final class GestionBaseDatos {

    /**
     * Nombre del usuario.
     */
    private final static String usuario = "gcast";

    /**
     * Contraseña del usuario.
     */
    private final static String passwd = "Cast_21";

    /**
     * Nombre de la base de datos a la que se conecta la aplicación.
     */
    private final static String bda = "escala_braden";

    /**
     * Dirección IP del servidor donde está la base de datos.
     */
    private final static String host = "192.168.59.152";

    /**
     * Objeto Connection que representa la conexión con la base de datos.
     */
    private static Connection conexion;

    /**
     * Constructor de la clase GestionBaseDatos
     */
    public GestionBaseDatos() {
//        conectar();
    }

    /**
     * Permite el acceso al atributo conexión desde fuera de la clase.
     *
     * @return Devuelve la conexión que ha establecido el método conectar.
     */
    public static Connection getConexion() {
        return conexion;

    }

    /**
     * Establece la conexión entre la aplicación y la base de datos.
     *
     * @return Devuelve true si la conexión se ha realizado con éxito y false en
     * caso contrario.
     * @throws SQLException
     */
    public static boolean conectar() throws SQLException {

        String url = "jdbc:mysql://" + host + ":3306/" + bda;
        conexion = DriverManager.getConnection(url, usuario, passwd);

        return (conexion != null);
    }

    /**
     * Cierra la conexión con la base de datos.
     *
     * @throws SQLException
     */
    public static void desconectar() throws SQLException {

        conexion.close();

    }


}

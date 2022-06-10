/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import Modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de hacer de intermediario entre la base de datos y la clase
 * Usuario, permitiendo su comunicación.
 *
 * @author Cambralla
 */
public class UsuarioDAO {

    private static PreparedStatement consulta;

    /**
     * Constructor.
     */
    public UsuarioDAO() {
    }

    /**
     * Añade un usuario a la base de datos.
     *
     * @param nombre Nombre del usuario.
     * @param apellido1 Primer apellido.
     * @param apellido2 Segundo apellido.
     * @param correo Correo electrónico.
     * @param contrasenya Contraseña del usuario.
     * @return Devuelve "OK" si todo ha ido correctamente, un mensaje de error
     * en caso contrario.
     * @throws SQLException
     */
    public String insertarUsuario(String nombre, String apellido1, String apellido2, String correo, String contrasenya) throws SQLException {
        String error = "";
        String sentencia = "INSERT  INTO usuarios values (?,?,?,?,?,?)";
        consulta = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        String Id_Usuario = null;
        consulta.setString(1, Id_Usuario);
        consulta.setString(2, nombre);
        consulta.setString(3, apellido1);
        consulta.setString(4, apellido2);
        consulta.setString(5, correo);
        consulta.setString(6, contrasenya);
        consulta.executeUpdate();
        return "OK";
    }

    /**
     *
     * @param correo Correo electrónico.
     * @param contrasenya Contraseña del usuario.
     * @return Devuelve un objeto de la clase Usuario.
     * @throws SQLException
     */
    public Usuario getUsuario(String correo, String contrasenya) throws SQLException {
        String sentencia = "SELECT * FROM usuarios WHERE correo=? and contrasenya=?";

        PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        ps.setString(1, correo);
        ps.setString(2, contrasenya);

        ResultSet rs = ps.executeQuery();
        String nombre = "";
        String email = "";
        String password = "";
        int id;
        rs.next();
        id = rs.getInt("id");
        nombre = rs.getString("nombre");
        email = rs.getString("correo");
        password = rs.getString("contrasenya");

        return new Usuario(id, nombre, email, password);

    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id Identificador del usuario.
     * @return Devuelve el mensaje "Eliminado correctamente" en caso de haber
     * ido bien, un mensaje de error en caso contrario.
     */
    public String borrarUsuario(int id) {
        try {

            String sentencia = "DELETE FROM USUARIOS" + " WHERE id = ?";
            consulta = GestionBaseDatos.getConexion().prepareStatement(sentencia);
            consulta.setInt(1, id);
            consulta.executeUpdate();
            consulta.clearParameters();
        } catch (SQLException ex) {
            return ex.getMessage();
        }
        return "Eliminado correctamente";
    }

    public boolean existeUsuario(String correo) throws SQLException {

        boolean existe = false;

        String sentencia = "SELECT COUNT(*) as existe FROM usuarios WHERE correo=?";

        PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        ps.setString(1, correo);

        ResultSet rs = ps.executeQuery();
        int existeSQL = 0;

        rs.next();

        existeSQL = rs.getInt("existe");
        
        if (existeSQL==1) {
            existe=true;
        }

        return existe;

    }
}

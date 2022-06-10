/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import Modelo.Paciente;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * Clase que ejerce de intermediario entre la base de datos y la clase Paciente,
 * permitiendo su comunicación.
 *
 * @author Cambralla
 */
public class PacienteDAO {

    /**
     * Constructor de la clase PacienteDAO
     */
    public PacienteDAO() {
    }

    /**
     * Método encargado de crear objetos de la clase Paciente con los datos
     * recuperados de la base de datos.
     *
     * @param nombre Nombre del paciente.
     * @return Devuelve un nuevo objeto de la clase paciente.
     * @throws SQLException
     */
    public Paciente getPaciente(String nombre) throws SQLException {
        String sentencia = "SELECT * FROM pacientes WHERE nombre=?";

        PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        ps.setString(1, nombre);
        /*ps.setString(2, fechaNac);*/

        String name = "";
        LocalDate fecha = null;
        int edad = 0;
        int id = 0;

        try (ResultSet rs = ps.executeQuery()) {

            rs.next();
            name = rs.getString("nombre");
            id = rs.getInt("id");
            fecha = rs.getDate("fecha_nac").toLocalDate();

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }

        return new Paciente(id, name, fecha);

    }

    public Paciente getPaciente(int idUser) throws SQLException {
        String sentencia = "SELECT * FROM pacientes WHERE idUser=?";

        PreparedStatement ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        ps.setInt(1, idUser);
        /*ps.setString(2, fechaNac);*/

        String name = "";
        LocalDate fecha = null;
//        int edad = 0;
        int id = 0;

        try (ResultSet rs = ps.executeQuery()) {

            rs.next();
            id = rs.getInt("id");
            name = rs.getString("nombre");
            fecha = rs.getDate("fecha_nac").toLocalDate();

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }

        return new Paciente(id, name, fecha);

    }

    public String insertarPaciente(String nombre, LocalDate fechaNac, int idUser) throws SQLException {
       
        String sentencia = "INSERT INTO pacientes values (?,?,?,?)";

        PreparedStatement consulta = GestionBaseDatos.getConexion().prepareStatement(sentencia);
        try {
            String Id_Paciente = null;
            consulta.setString(1, Id_Paciente);
            consulta.setString(2, nombre);
            consulta.setDate(3, Date.valueOf(fechaNac));
            consulta.setInt(4, idUser);
            
    
            
            consulta.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
            return "OK";
        }
    }

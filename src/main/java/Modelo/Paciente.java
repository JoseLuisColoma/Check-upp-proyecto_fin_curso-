/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;
import java.time.Period;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;


/**
 * Clase que define cómo ha de ser un paciente.
 *
 * @author Cambralla
 */
public class Paciente {

    /**
     * Identificador del paciente.
     */
    private int id;

    /**
     * Nombre del paciente.
     */
    private String nombre;

    /**
     * Fecha de nacimiento del paciente.
     */
    private LocalDate fechaNac;

    /**
     * Edad del paciente.
     */
    private int edad;
    private int idUser;

    /**
     * Constructor de la clase Paciente.
     *
     * @param id Identificador del paciente.
     * @param nombre Nombre del paciente.
     * @param fechaNac Fecha de nacimiento.
     */
    public Paciente(int id, String nombre, LocalDate fechaNac) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        try {
            Period period = Period.between(fechaNac, LocalDate.now());
            this.edad = period.getYears();
        } catch (Exception e) {
            this.edad=20;
        }

    }

    /**
     * Se encarga de convertir toda la información del paciente en una cadena de
     * texto.
     *
     * @return Devuelve el texto con la información del paciente.
     */
    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nombre=" + nombre + ", fechaNac=" + fechaNac + ", edad=" + edad + '}';
    }

    /**
     *
     * @return Devuelve el nombre del paciente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return Devuelve la fecha de nacimiento de un paciente.
     */
    public LocalDate getFechaNac() {
        return fechaNac;
    }

    /**
     *
     * @return Devuelve la edad del paciente.
     */
    public int getEdad() {
        return edad;
    }

    /**
     *
     * @return Devuelve el identificador del paciente.
     */
    public int getId() {
        return id;
    }
    
    public Paciente(int id, String nombre, LocalDate fechaNac, int idUser) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        
        try {
            Period period = Period.between(fechaNac, LocalDate.now());
            this.edad = period.getYears();
        } catch (Exception e) {
            Notifications.create().title("Fecha").text("No se ha encontrado la edad del paciente").position(Pos.CENTER).showWarning();
        }
        this.idUser=idUser;

    }

}

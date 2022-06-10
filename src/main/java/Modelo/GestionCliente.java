/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import BDA.ResultadosDAO;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase encargada de la gestión de la interacción entre usuarios y pacientes.
 * Asigna pacientes a los usuarios.
 *
 * @author Cambralla
 */
public class GestionCliente {

    /**
     * Paciente al que se evalúa.
     */
    private static Paciente paciente;

    /**
     * Usuario que está utilizando la aplicación.
     */
    private static Usuario usuario;
    
    

    private static Set<ResultadoTableView> historialTest = new HashSet<>();
    
    
    /**
     * Constructor de la clase que gestiona los clientes.
     */
    public GestionCliente() {
    
    
    }
    
    public static void resetHistorial(){
        historialTest.clear();
    }
    
    
    

    public static Set<ResultadoTableView> getHistorialTest() {
        
        return historialTest;
    }

    public static void setHistorialTest(Set<ResultadoTableView> historialTest) {
        GestionCliente.historialTest = historialTest;
    }

    public static void addResultado(ResultadoTableView res) {

        historialTest.add(res);
    }

    /**
     * Asigna al atributo paciente el paciente pasado por parámetro.
     *
     * @param paciente Paciente que se quiere asignar a un usuario.
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * Asigna al atributo usuario el usuario pasado por parámetro.
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return Devuelve un usuario.
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    

    /**
     *
     * @return Devuelve un paciente.
     */
    public static Paciente getPaciente() {
        return paciente;
    }

}

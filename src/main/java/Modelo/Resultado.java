/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;


/**
 * Clase que gestiona los resultados de cada test.
 *
 * @author 26899630a
 */
public class Resultado {

    /**
     * Fecha en la que se realizó el test.
     */
    private LocalDate fecha;
    
    /**
     * Paciente al que se le realizó el test.
     */
    private Paciente paciente;

 

    /**
     * Lista de la puntuación obtenida en cada uno de los apartados.
     */
    private int[] listaPuntos;

    /**
     * Identificador del grado de riesgo (alto, medio, bajo, sin riesgo).
     */
    private int idRiesgo;
    
    /**
     * Constructor de la clase Resultado. Inicializa los atributos.
     *
     * @param fecha Fecha en la que se realizó el test.
     * @param paciente Paciente al que se le hizo el test.
     * @param listaPuntos Lista de la puntuación obtenida en cada uno de los
     * apartados.
     * @param idRiesgo Identificador del grado de riesgo (alto, medio, bajo, sin
     * riesgo).
     */
    public Resultado(LocalDate fecha, Paciente paciente, int[] listaPuntos, int idRiesgo) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.listaPuntos = listaPuntos;
        this.idRiesgo = idRiesgo;
    }



    /**
     *
     * @return Devuelve la fecha en la que se realizó el test.
     */
    public LocalDate getFecha() {
        return fecha;
    }



    /**
     *
     * @return Devuelve el paciente al que se le realizó el test.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     *
     * @return Devuelve el conjunto de los puntos obtenidos en cada categoría.
     */
    public int[] getListaPuntos() {
        return listaPuntos;
    }

    /**
     *
     * @return Devuelve el identificador del grado de riesgo (alto, medio, bajo,
     * sin riesgo).
     */
    public int getIdRiesgo() {

        return idRiesgo;
    }



}

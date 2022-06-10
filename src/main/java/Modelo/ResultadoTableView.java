/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author 26899630a
 */
public class ResultadoTableView {

    private String fechaFormateada;
    private LocalDate fecha;
    private int idRiesgo;

    

    private String pacienteNombre;
    private int p1;
    private int p2;
    private int p3;
    private int p4;
    private int p5;
    private int p6;
    private String riesgo = "NoRIESGO";
    
    public ResultadoTableView(LocalDate fecha, int p1, int p2, int p3, int p4, int p5, int p6, int idRiesgo) {
        this.fecha = fecha;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.idRiesgo = idRiesgo;
        this.pacienteNombre=setNombrePaciente();
    }
    
    private String setNombrePaciente(){
        return GestionCliente.getPaciente().getNombre();
    }
    
    public String getFechaFormateada() {
        DateTimeFormatter patron = DateTimeFormatter.ofPattern("EE dd 'de' MMMM 'de' yyyy");
        fechaFormateada=fecha.format(patron);
        return fechaFormateada;
    }
    
    public String getRiesgo() {

        if (idRiesgo == 1) {
            riesgo = "BAJO";
        }
        if (idRiesgo == 2) {

            riesgo = "MODERADO";
        }
        if (idRiesgo == 3) {

            riesgo = "ALTO";
        }

        return riesgo;
    }
    
    public String getPacienteNombre() {
        return pacienteNombre;
    }
    
    public int getPuntosTotales() {
        return p1 + p2 + p3 + p4 + p5 + p6;
    }
    
    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    

    public int getP3() {
        return p3;
    }

    public int getP4() {
        return p4;
    }

    public int getP5() {
        return p5;
    }

    public int getP6() {
        return p6;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "TEST-> " + "Fecha: " + fechaFormateada + ", paciente: " + pacienteNombre + ", Percepcion Sensorial: " + p1 
                + ", Humedad: " + p2 + ", Actividad: " + p3 + ", Movilidad: " + p4 + ", Nutricion: " + p5 
                + ", Friccion y Cizallamiento: " + p6 + ", Riesgo: " + riesgo;
    }
    
    
    
    

}

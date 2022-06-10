/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

/**
 *
 * @author Cambralla
 */
public class Situacion {

    private static Map<String, Image> situaciones = new HashMap<>();

    public void setSituaciones(Map<String, Image> situaciones) {
        this.situaciones = situaciones;
    }

    public Image getSituacion(String clave) {
        return situaciones.get(clave);
    }

    public void addSituacion(String clave, Image situacion) {
        situaciones.put(clave, situacion);
    }
}

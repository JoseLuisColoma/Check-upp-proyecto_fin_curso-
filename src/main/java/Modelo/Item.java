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
public class Item {

    private static Map<String, Image> items = new HashMap<>();
    
    
    /**
     * 
     * @param nombre nombre que tiene la imagen en la base de datos
     * @return devuelve la imagen asociada a ese nombre
     */
    public Image getItem(String nombre) {

        return items.get(nombre);

    }

    /**
     * 
     * @param items map de imagenes
     */
    public void setItems(Map<String, Image> items) {
        this.items = items;
    }

    /**
     * 
     * @param clave nombre con el que se guardara la imagen
     * @param item imagen a guardar
     */
    public void addItem(String clave, Image item) {
        items.put(clave, item);
    }

}

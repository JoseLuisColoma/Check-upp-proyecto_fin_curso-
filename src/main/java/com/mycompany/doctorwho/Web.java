/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.doctorwho;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Clase encargada de gestionar los enlaces de interés mostrados en la
 * aplicación.
 *
 * @author wendy
 */
public class Web {

    private String web;

    /**
     * Constructor de la clase Web
     */    
    public Web() {

    }

    //seter
    //hemos creado el método static
    
    /**
     * Método encargado de abrir el navegador por defecto del cliente y mostrar
     * la web referenciada por el enlace proporcionado.
     *
     * @param web
     */    
    public static void mostrarEnlace(String web) {

        try {

            Desktop.getDesktop().browse(new URI(web));

        } catch (URISyntaxException ex) {

            System.out.println(ex);

        } catch (IOException e) {

            System.out.println(e);

        }

    }

    /**
     * Getter de la clase Web.
     *
     * @return Devuelve el enlace de la página web en cuestión.
     */    
    public String getWeb() {
        return web;
    }
}

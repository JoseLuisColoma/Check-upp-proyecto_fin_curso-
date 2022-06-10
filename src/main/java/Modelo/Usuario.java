/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * Clase que gestiona a los usuarios.
 *
 * @author Cambralla
 */
public class Usuario {

    /**
     * Identificador del usuario.
     */
    private int id;

    /**
     * Nombre de usuario.
     */
    private String nombre;

    /**
     * Primer apellido.
     */
    private String apellido1;

    /**
     * Segundo apellido.
     */
    private String apellido2;

    /**
     * Correo electrónico.
     */
    private String email;
    
    /**
     * Contraseña del usuario.
     */    
    private String contrasenya;

    /**
     * Constructor de la clase Usuario sobrecargado. Éste inicializa todos los
     * datos.
     *
     * @param nombre Nombre del usuario.
     * @param apellido1 Primer apellido.
     * @param apellido2 Segundo apellido.
     * @param email Correo electrónico.
     * @param contrasenya Contraseña del usuario.
     */    
    public Usuario(int id,String nombre, String apellido1, String apellido2, String email, String contrasenya) {
        this.id=id;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    

    /**
     * Constructor de la clase Usuario. Inicializa los datos de éste.
     *
     * @param nombre
     * @param email
     * @param contrasenya
     */    
    public Usuario(int id,String nombre, String email, String contrasenya) {
        this.id=id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    /**
     *
     * @return Devuelve el nombre del usuario.
     */    
    public String getNombre() {
        return nombre;
    }
    
    public int getId() {
        return id;
    }
}

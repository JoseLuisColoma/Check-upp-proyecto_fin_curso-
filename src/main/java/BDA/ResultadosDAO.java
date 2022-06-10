/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDA;

import Modelo.GestionCliente;
import Modelo.Resultado;
import Modelo.ResultadoTableView;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase encargada de hacer de intermediaria entre la base de datos y la clase
 * resultados, permitiendo su comunicación.
 *
 * @author Cambralla
 */
public class ResultadosDAO {

    private static PreparedStatement ps;

    /**
     * Inserta un nuevo test en la base de datos.
     *
     * @param test Cuestionario para evaluar el riesgo de sufrir úlceras por
     * presión.
     * @return Devuelve true si el test se ha insertado correctamente en la base
     * de datos, false en caso contrario.
     * @throws SQLException
     */
    public boolean insertarTest(Resultado test) throws SQLException {

        boolean seHaInsertado = false;
        String sentencia = "INSERT  INTO resultados values (?,?,?,?,?,?,?,?,?)";

        ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);

        ps.setDate(1, Date.valueOf(test.getFecha()));
        ps.setInt(2, test.getPaciente().getId());

        int posicionPuntos = 0;

        for (int i = 3; i <= 8; i++) {
            
            ps.setInt(i, test.getListaPuntos()[posicionPuntos]);
            posicionPuntos++;
        }

        ps.setInt(9, test.getIdRiesgo());

        int resultado = ps.executeUpdate();

        if (resultado == 1) {
            seHaInsertado = true;
        }
        return seHaInsertado;

    }

    public void getAllTest(int id) throws SQLException {

        boolean seHaInsertado = false;
        Set<Resultado> resultados = new HashSet<>();

        String sentencia = "SELECT * FROM resultados where paciente=?";

        ps = GestionBaseDatos.getConexion().prepareStatement(sentencia);
        
        ps.setInt(1,id);

        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            
            LocalDate fecha=rs.getDate("fecha").toLocalDate();
//            int paciente=rs.getInt("paciente");
            int p1=rs.getInt("puntos_item1");
            int p2=rs.getInt("puntos_item2");
            int p3=rs.getInt("puntos_item3");
            int p4=rs.getInt("puntos_item4");
            int p5=rs.getInt("puntos_item5");
            int p6=rs.getInt("puntos_item6");
            int riesgo=rs.getInt("id_riesgo");
            
            GestionCliente.addResultado(new ResultadoTableView(fecha,p1,p2,p3,p4,p5,p6,riesgo));
            
        }

       
        

    }

}

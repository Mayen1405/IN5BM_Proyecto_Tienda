/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemelgar.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author informatica
 */
public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKinalExpress?useSSL=false", "root", "admin");
            System.out.println("Conexión establecida correctamente");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Error: No se pudo instanciar el driver");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Error: Acceso ilegal al driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Conexión a la base de datos fallida");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error desconocido");
            e.printStackTrace();
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}


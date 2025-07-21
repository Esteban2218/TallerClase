package app.modelo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    public Connection conectar() {
        String ruta = System.getProperty("user.home") + "/Desktop/prod.db";
        String url = "jdbc:sqlite:" + new File(ruta).getAbsolutePath();
        Connection conn = null;
        
        try{
            conn = DriverManager.getConnection(url);
            System.out.println("Conexion Exitosa");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /*
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        conexion.conectar();
    }
     */
}

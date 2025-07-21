package app.modelo;

import java.sql.Connection;

public class ImplCrud {
    private Connection conn = null;
    private Connection conectar() {
        Conexion conexion = new Conexion();
        conn = conexion. conectar();
        return conn;
}
}

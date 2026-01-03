package poolconexiones;

import java.sql.Connection;

public class Conexion implements IConexion {

    private Connection con;

    public Conexion (Connection conn){
        con = conn;
    }

    public Connection getConnection() {
        return con;
    }
}

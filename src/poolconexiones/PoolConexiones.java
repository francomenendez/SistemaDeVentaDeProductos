package poolconexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import logica.excepciones.PersistenciaException;
import java.lang.InterruptedException;
import java.sql.*;

public class PoolConexiones implements IPoolConexiones{

    private String driver, url, user, password;
    private int nivelTransaccionalidad;
    private Conexion[] conexiones;
    private int tamanio, creadas, tope;

    public PoolConexiones() throws IOException, FileNotFoundException, ClassNotFoundException {

        //carga del driver
        Properties prop = new Properties();
        String nombre = "resources/config.properties";
        prop.load(new FileInputStream(nombre));
        driver = prop.getProperty("driver");
        Class.forName(driver);
        
        url = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");

        //inicializar variables
        tamanio=5;
        creadas=0;
        tope=0;

        //solicita memoria para el arreglo con tope
        conexiones = new Conexion[tamanio];

        //inicializar transaccionalidad
        nivelTransaccionalidad = Connection.TRANSACTION_SERIALIZABLE;
    }


    public synchronized IConexion obtenerConexion(boolean modifica) throws PersistenciaException  {
       try{
            Conexion cx;
            Connection con;
            
            //si hay conexiones disponibles (ya creadas)
            if(tope > 0) {
                tope--;
                cx = conexiones[tope];
                con  = cx.getConnection();
            }
            //si hay lugar para crear una conexion nueva
            else {
                if (creadas < tamanio){
                    con = DriverManager.getConnection(url, user, password);
                    con.setTransactionIsolation(nivelTransaccionalidad);
                    creadas++;
                }
                //mientras ya estan creadas y ocupadas todas las conexiones
                else {
                    while(tope == 0 ){
                        wait();
                    }
                    tope--;
                    cx = conexiones[tope];
                    con  = cx.getConnection();
                }
            }

            con.setAutoCommit(!modifica);

            IConexion icon = new Conexion(con);
            
            return icon;
        }catch(SQLException | InterruptedException i1){
            throw new PersistenciaException("Error al obtener la conexión", i1);
        }
    }

    public synchronized void liberarConexion(IConexion icon, boolean ok) throws PersistenciaException {

        Conexion cx = (Conexion) icon;
        Connection con = cx.getConnection();
        
        try{
            //Si la conecction tiene el AutoCommit = FALSE (operaciones de modificar), entonces chequeamos
            if (!con.getAutoCommit()) {
                if(ok) {
                    con.commit();
                }
                else {
                    con.rollback();
                }
            }      
            conexiones[tope] = cx;
            tope++;
            notifyAll();     
        }catch(SQLException e1){
            throw new PersistenciaException("Error al liberar la conexión", e1);
        }
    }
}

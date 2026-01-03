package grafica.controladores;
import java.awt.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;

import grafica.ventanas.VentanaListadoProductos;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoNoExisteException;
import logica.valueobjects.VOProducto;

public class ControladorListadoProductos {
    private IFachada fachada;
    private VentanaListadoProductos ventana;

    String Exito = "PRODUCTOS LISTADOS CORRECTAMENTE";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";


    public ControladorListadoProductos(VentanaListadoProductos v){
        this.ventana = v;
        try {

            Properties p  = new Properties();
            String rutaConfig= "resources/config.properties";

            p.load(new FileInputStream(rutaConfig));

            String servidor = p.getProperty("ipServidor");
            String puerto = p.getProperty("puertoServidor");

            String ruta = "//" + servidor + ":" + puerto + "/cl";
        
            fachada = (IFachada)Naming.lookup(ruta);      

            ventana.setVisible(true);

        } catch (IOException  | NotBoundException e) {
            ventana.mensajeError(errorConexion, false);
        }
    }

    public LinkedList<VOProducto> ListarProductos () {
        LinkedList<VOProducto> list = new LinkedList<>();
        try {
                list = (LinkedList<VOProducto>) fachada.listadoProductos();
            }catch(ProductoNoExisteException|PersistenciaException e) {
			    ventana.mensajeError(e.getMessage(), false);
		}catch(RemoteException e) {
		    e.printStackTrace();
		}
        finally{
            return list;
        }
    }
}

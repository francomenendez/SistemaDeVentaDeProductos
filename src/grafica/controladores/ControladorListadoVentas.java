package grafica.controladores;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Properties;

import grafica.ventanas.VentanaListadoVentas;

import java.io.FileInputStream;
import java.io.IOException;

import logica.IFachada;
import logica.excepciones.*;
import logica.valueobjects.VOVentaTotal;

public class ControladorListadoVentas {
    private IFachada fachada;
    private VentanaListadoVentas ventana;

    String ErrorCodigoVacio = "ERROR: Codigo no ingresado";
    String Exito = "VENTAS LISTADAS CORRECTAMENTE";
   String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorListadoVentas (VentanaListadoVentas v) {
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

    public LinkedList<VOVentaTotal> ListadoVentas (String CodP) {
        LinkedList<VOVentaTotal> list = new LinkedList<>();
        try {
            if(CodP == null | CodP.isEmpty())
                ventana.mensajeError(ErrorCodigoVacio, false);
            else{
                list = (LinkedList<VOVentaTotal>) fachada.listadoVentas(CodP);
            }
        }catch(PersistenciaException | VentaNoExisteException | ProductoNoExisteException e) {
			ventana.mensajeError(e.getMessage(), false);
		}catch(RemoteException e1) {
		    e1.printStackTrace();
		}
        finally{
            return list;
        }
    }

}

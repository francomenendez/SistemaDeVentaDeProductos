package grafica.controladores;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.VentanaDatosVenta;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoExisteException;
import logica.excepciones.ProductoNoExisteException;
import logica.excepciones.VentaNoExisteException;
import logica.valueobjects.VOVenta;
import java.io.IOException;
import java.io.FileInputStream;

public class ControladorDatosVenta {
    private IFachada fachada;
    private VentanaDatosVenta ventana;
    
    String CodigoProductoVacio = "ERROR : Codigo no ingresado";
    String NumeroVentaVacio = "ERROR : Numero de Venta no ingresado";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorDatosVenta (VentanaDatosVenta v) {
        this.ventana = v;
        try {

            Properties p  = new Properties();
            String rutaConfig= "resources/config.properties";

            p.load(new FileInputStream(rutaConfig));

            String servidor = p.getProperty("ipServidor");
            String puerto = p.getProperty("puertoServidor");
            String nombre = p.getProperty("nomArch");

            String ruta = "//" + servidor + ":" + puerto + "/cl";
        
            fachada = (IFachada)Naming.lookup(ruta);      

            ventana.setVisible(true);

        } catch (IOException  | NotBoundException e) {
            ventana.mensajeError(errorConexion, false);
        }
        
    }

    public VOVenta DatosVenta (String CodP,int numV) {
        VOVenta v = null;
        try {
            if(CodP.isEmpty()) {
                ventana.mensajeError(CodigoProductoVacio, false);
            }
            else if(numV == 0) {
                ventana.mensajeError(NumeroVentaVacio, false);
            }
            else {
                v = fachada.datosVenta(CodP,numV);
            }

		} catch( ProductoNoExisteException|PersistenciaException | VentaNoExisteException e) {
			ventana.mensajeError(e.getMessage(), false);
		} catch(RemoteException e) {
			e.printStackTrace();
		} finally {
            return v;
        }
    }

}





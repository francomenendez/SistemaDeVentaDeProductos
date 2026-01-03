package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.VentanaTotalRecaudadoPorVentas;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoNoExisteException;

public class ControladorTotalRecaudadoPorVentas {

    private IFachada fachada;
    private VentanaTotalRecaudadoPorVentas ventana;

    String ErrorCodigoVacio = "ERROR : Codigo no ingresado";
    String Exito = "TOTAL RECAUDADO DADO CORRECTAMENTE";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorTotalRecaudadoPorVentas(VentanaTotalRecaudadoPorVentas v){
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

    public double totalRecaudadoPorVentas(String CodP){
        double total = 0.0;
        try{
            if(CodP.isEmpty())
                ventana.mensajeError(ErrorCodigoVacio, false);
            else{
                total = fachada.totalRecaudadoPorVentas(CodP);
            }
        
        } catch (ProductoNoExisteException | PersistenciaException e) {
                ventana.mensajeError(e.getMessage(), false);
        }catch(RemoteException e) {
			e.printStackTrace();
         }
        finally{
            return total;
        }
    }
}



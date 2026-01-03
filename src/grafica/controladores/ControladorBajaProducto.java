package grafica.controladores;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;


import grafica.ventanas.VentanaBajaProducto;
import logica.IFachada;
import logica.excepciones.ProductoExisteException;
import logica.excepciones.ProductoNoExisteException;
import logica.excepciones.PersistenciaException;

public class ControladorBajaProducto {
    private IFachada fachada;
    private VentanaBajaProducto ventana;

    String ErrorCodigoProducto = "ERROR: Codigo de producto no encontrado";
    String Exito = "PRODUCTO DADO DE BAJA CORRECTAMENTE";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorBajaProducto (VentanaBajaProducto v) {
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

    public void BajaProducto (String cod){
        try{
            if(cod == null | cod.isEmpty())
                ventana.mensajeError(ErrorCodigoProducto, false);
            else{
                fachada.bajaProducto(cod);
                ventana.mensajeError(Exito,true);
            }
        } catch( ProductoNoExisteException | PersistenciaException e) {
			ventana.mensajeError(e.getMessage(), false);
		}catch(RemoteException e) {
			e.printStackTrace();
         }
    }
}

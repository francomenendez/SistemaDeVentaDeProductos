package grafica.controladores;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.VentanaAltaProducto;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoExisteException;
import logica.valueobjects.VOProducto;
import java.io.IOException;
import java.io.FileInputStream;


public class ControladorAltaProducto {
    private IFachada fachada;
    private VentanaAltaProducto ventana;

    String ErrorCodigoVacio = "ERROR : Codigo no ingresado";
    String ErrorNombreVacio = "ERROR: Nombre no ingresado";
    String ErrorPrecioVacio = "ERROR: Precio no ingresado";
    String Exito = "PRODUCTO INGRESADO CON EXITO";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorAltaProducto (VentanaAltaProducto v) {
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

    public void AltaProducto (String cod, String nom, int pre) {
        try {
            if(cod.isEmpty()) {
                ventana.mensajeError(ErrorCodigoVacio, false);
            }
            else if(nom.isEmpty()) {
                ventana.mensajeError(ErrorNombreVacio, false);
            }
            else if(pre == 0) {
                ventana.mensajeError(ErrorPrecioVacio, false);
            }
            else {
                VOProducto vop = new VOProducto(cod, nom, pre);
                fachada.altaProducto(vop);
                ventana.mensajeError(Exito, true);
            }

		} catch( ProductoExisteException | PersistenciaException e) {
			ventana.mensajeError(e.getMessage(), false);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
    }

}

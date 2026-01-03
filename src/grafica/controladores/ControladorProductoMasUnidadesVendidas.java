package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.VentanaProductoMasUnidadesVendidas;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoNoExisteException;
import logica.valueobjects.VOProdVentas;

public class ControladorProductoMasUnidadesVendidas {
    private IFachada fachada;
    private VentanaProductoMasUnidadesVendidas ventana;

    String Exito = "PRODUCTO CON MAS UNIDADES VENDIDAS DADO CORRECTAMENTE";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";

    public ControladorProductoMasUnidadesVendidas(VentanaProductoMasUnidadesVendidas v){
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

    public VOProdVentas ProductoMasUnidadesVendidas () {
        VOProdVentas vop = null;
        try {
            vop = fachada.productoMasUnidadesVendidas();
            
        }catch(ProductoNoExisteException|PersistenciaException | NullPointerException e) {
			    ventana.mensajeError(e.getMessage(), false);
		}catch(RemoteException e) {
		    e.printStackTrace();
		}
        finally{
            return vop;
        }
    }
}


package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.VentanaRegistroVenta;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoNoExisteException;
import logica.valueobjects.VOVenta;

public class ControladorRegistroVenta {
    private IFachada fachada;
    private VentanaRegistroVenta ventana;

    String ErrorCodigoVacio = "ERROR : Codigo no ingresado";
    String ErrorClienteVacio = "ERROR: Cliente no ingresado";
    String ErrorUnidadesVacio = "ERROR: Unidades no ingresadas";
    String Exito = "VENTA REGISTRADA CON EXITO";
    String errorConexion = "Se perdio la conexion con el Servidor, intente nuevamente";
    

    public ControladorRegistroVenta (VentanaRegistroVenta v) {
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
            System.out.println("Ex: " + e.getMessage());
            ventana.mensajeError(errorConexion, false);
        }
    }

    public void RegistroVenta (String codP, String cliente, int uni) {
        try {
            if(codP == null | codP.isEmpty()) {
                ventana.mensajeError(ErrorCodigoVacio, false);
            }
            else if(cliente.isEmpty()) {
                ventana.mensajeError(ErrorClienteVacio, false);
            }
            else if(uni == 0) {
                ventana.mensajeError(ErrorUnidadesVacio, false);
            }
            else {
                VOVenta vov = new VOVenta(uni,cliente);
                fachada.registroVenta(codP, vov);
                ventana.mensajeError(Exito, true);
            }

		} catch( ProductoNoExisteException | PersistenciaException e) {
			ventana.mensajeError(e.getMessage(), false);
		} catch(RemoteException e) {
		 	ventana.mensajeError(e.getMessage(), false);
		}
    }
}

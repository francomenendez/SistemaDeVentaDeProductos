package RMI;

import logica.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main (String [] args){
        try {
            LocateRegistry.createRegistry(1099);
            Fachada f = Fachada.getInstancia();
            String ruta = "//localhost:1099/cl"; 
            System.out.println ("Antes de publicarlo");
            Naming.rebind(ruta, f);
            System.out.println ("Luego de publicarlo");
            }
        catch (RemoteException e)
            { e.printStackTrace(); }
        catch (MalformedURLException e)
            { e.printStackTrace(); }
        catch(IOException e)
            { e.printStackTrace(); }
        catch (InstantiationException e)
            { e.printStackTrace();}
        }
}

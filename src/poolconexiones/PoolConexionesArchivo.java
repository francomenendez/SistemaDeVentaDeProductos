package poolconexiones;

import java.io.File;
import java.io.IOException;

import logica.excepciones.PersistenciaException;

public class PoolConexionesArchivo implements IPoolConexiones{
    private int cantLectores;
    private boolean hayEscritor;
    
    public PoolConexionesArchivo() {
        cantLectores = 0;
        hayEscritor = false;
    }
        
    public synchronized IConexion obtenerConexion(boolean modifica) throws PersistenciaException{
        IConexion icon = null;
        try{
            if(modifica){
                while (hayEscritor || cantLectores > 0){
                    wait();
                }
                hayEscritor = true;
            }
            else{
                while (hayEscritor){
                    wait();
                }
                cantLectores ++;
            }
        
        icon = new ConexionArchivo(modifica,true,"");

        }catch(InterruptedException e){
            throw new PersistenciaException("Error al conseguir conexion", e);
        }
       
        return icon;
    }

    public synchronized void liberarConexion(IConexion icon, boolean ok){
        ConexionArchivo con = (ConexionArchivo) icon;
        
        //Si nuestra conexion modifica algo y no se termino la ejecuccion correctamente
        if (!ok && con.getEsEscritor() ){
            // Hacer "rollback": borrar el archivo si la operación falló
            File archivo = new File(con.getNomarch());
            if (archivo.exists()){
                if (archivo.delete()){
                    System.out.println("[ROLLBACK] Archivo " + con.getNomarch() + " eliminado");
                } else {
                    System.out.println("[ERROR] No se pudo eliminar el archivo: " + con.getNomarch());
                }
            }
        }
        
        if (con.getEsEscritor()){
            hayEscritor = false;
        } else {
            cantLectores --;
            //notifyall para escritores (los cuales solo pueden modificar cuando no hay lectores)
            if (cantLectores == 0){
              notifyAll();
            }
        }
    }
    
}
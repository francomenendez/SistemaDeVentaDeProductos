//ARREGLAR LAS EXCEPTIONS REMOTE

package logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import logica.excepciones.*;
import logica.valueobjects.*;
import poolconexiones.IConexion;
import poolconexiones.IPoolConexiones;
import persistencia.daos.*;
import persistencia.fabrica.FabricaAbstracta;

public class Fachada extends UnicastRemoteObject implements IFachada{
    

    private IPoolConexiones ipool;
    private static Fachada instancia;
    private IDAOProductos daop;
    FabricaAbstracta fab;

    public Fachada() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        Properties prop = new Properties();
	    String nombre = "resources/config.properties";
	    prop.load(new FileInputStream(nombre));
	    String poolConcreto = prop.getProperty("PoolConexiones"); 
        ipool = (IPoolConexiones) Class.forName(poolConcreto).newInstance();
        
        String fabricaConcreta = prop.getProperty("Fabrica");
        fab = (FabricaAbstracta) Class.forName(fabricaConcreta).newInstance();
        daop = fab.crearDAOProductos();
    }

    //ingresa un nuevo producto, chequeando que el código no exista
    public void altaProducto(VOProducto voP) throws ProductoExisteException, RemoteException, PersistenciaException{
       IConexion icon = null;
       boolean exito = true;
       try{
            icon = ipool.obtenerConexion(true);
            Producto prod = new Producto(voP.getCodigo(),voP.getNombre(), voP.getPrecio(), fab.crearDAOVentas(voP.getCodigo()));
            if(!daop.member(voP.getCodigo(), icon)){
                daop.insert(prod, icon);
            }
            else
                throw new ProductoExisteException("El producto con codigo : " + voP.getCodigo() + " ya existe");       
        }catch(PersistenciaException e){
            exito = false;
            throw new RemoteException("Error acceso BD", e);
        }
        finally{
            try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD", p1);
            }
        }
    }

    //borra el producto con el código ingresado, chequeando que exista, y también borra todas sus ventas. 
    public void bajaProducto(String CodP) throws PersistenciaException, ProductoNoExisteException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        try {
            icon = ipool.obtenerConexion(true); 
            
            if(daop.member(CodP, icon)){
                Producto prod = daop.find(CodP, icon);
                prod.borrarVentas(icon);
                daop.delete(CodP, icon);
            }
            else{
                throw new ProductoNoExisteException("El Producto con codigo: "+ CodP +" no Existe");
            }
            
        } catch (PersistenciaException e) {
            exito = false;
            throw new RemoteException("Error acceso BD", e);
        }

        finally{
           try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD", p1);
            }
        }
        
    }
    
    //registra una nueva venta, chequeando que el código del producto correspondiente exista
    public void registroVenta(String CodP, VOVenta voV) throws PersistenciaException, ProductoNoExisteException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        try{
            icon = ipool.obtenerConexion(true);
            
            if(daop.member(CodP, icon)){
                Producto prod = daop.find(CodP, icon);
                
                int num = prod.cantidadVentas(icon) + 1;
                Venta v = new Venta(num,voV.getUnidades(), voV.getCliente());
                prod.agregarVenta(v,icon);
                
            } else {
                throw new ProductoNoExisteException("El Producto con codigo: "+ CodP +" no Existe");
            }
            
        }catch(PersistenciaException e){
            exito = false;
            throw new RemoteException("Error acceso BD al registrar la venta", e);
        }
        finally{
           try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD al liberar la conexion", p1);
            }
        }
        
    }

    //devuelve la cantidad de unidades y el cliente correspondiente a una venta, dados su número y el código del producto que le corresponde
    public VOVenta datosVenta(String codP, int numV) throws ProductoNoExisteException, VentaNoExisteException, PersistenciaException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        try{
            icon = ipool.obtenerConexion(false);
            
            VOVenta vov = null;
            if (daop.member(codP,icon)){
                Producto prod = daop.find(codP, icon);

                if (numV >= 1 && numV <= prod.cantidadVentas(icon)){
                    Venta v = prod.obtenerVenta(numV, icon);
                    if(v != null){
                        vov = new VOVenta(v.getUnidades(), v.getCliente());
                    } else {
                        throw new VentaNoExisteException("No existe la venta solicitada para el producto: " + codP + " con el nro. " + numV);
                    }
                }
                else
                    throw new VentaNoExisteException("No existe una venta para el producto: " + codP + " con el nro. " + numV);
            }
            else
                throw new ProductoNoExisteException("El producto ingresado no existe");

            icon = null;
            return vov;
        
        //manejo de errores para liberar la conexion (consultar si esta bien liberada en todos los casos posibles)
        }catch(PersistenciaException p1){
            exito = false;
            throw new RemoteException("Error acceso BD", p1);
        }
        finally{ 
            try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD", p1);
            }
        }
    }
    
    //devuelve un listado de todos los productos, ordenado por código.
    public List<VOProducto> listadoProductos() throws PersistenciaException, ProductoNoExisteException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        List<VOProducto> lvop = new LinkedList<>();
        try{
            icon = ipool.obtenerConexion(false);
            
            if (!daop.esVacio(icon)){
                lvop = daop.listarProductos(icon);
            }
            else
                throw new ProductoNoExisteException("No hay productos registrados");

            return lvop;
            
        }catch(PersistenciaException p1){
            exito = false;
            throw new RemoteException("Error acceso BD", p1);
        }
        finally{
            try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD", p1);
            }
        }
    }
    
    //devuelve un listado de todas las ventas de un producto determinado, (chequeando que el código correspondiente exista) ordenado por número de venta
    public List<VOVentaTotal> listadoVentas(String codP) throws VentaNoExisteException, ProductoNoExisteException, PersistenciaException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        List<VOVentaTotal> lvovt = new LinkedList<>();
        try{
            icon = ipool.obtenerConexion(false);
            
            if (daop.member(codP, icon)){
                Producto prod = daop.find(codP, icon);

                if (prod.cantidadVentas(icon) > 0){
                    lvovt = prod.listarVentas(icon);
                }
                else
                    throw new VentaNoExisteException("No hay ventas para el producto " + codP);
            }
            else
                throw new ProductoNoExisteException("No existe el producto " + codP);

            return lvovt;
            
        }catch(PersistenciaException p1){
            exito = false;
            throw new RemoteException("Error acceso BD", p1);
        }
        finally{
            try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p1){
                throw new RemoteException("Error acceso BD", p1);
            }
        }
    }

    //devuelve los datos del producto con la mayor cantidad de unidades vendidas, chequeando que exista al menos un producto
    public VOProdVentas productoMasUnidadesVendidas() throws PersistenciaException, ProductoNoExisteException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        VOProdVentas vopv = null;
        try{
            icon = ipool.obtenerConexion(false);
            if (!daop.esVacio(icon)){
                vopv = daop.ProductoMasVendido(icon);
            }
            else{
                throw new ProductoNoExisteException("No hay productos registrados");
            }

            return vopv;
        }catch(PersistenciaException p1){
            exito = false;
            throw new RemoteException("Error acceso BD", p1);
        }
        finally{
            try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p2){
                throw new RemoteException("Error acceso BD", p2);
            }
        }
    }
    
    //devuelve el monto total recaudado por las ventas de un producto determinado, chequeando que el código correspondiente exista
    public double totalRecaudadoPorVentas(String codP)throws ProductoNoExisteException,PersistenciaException, RemoteException{
        IConexion icon = null;
        boolean exito = true;
        double total_recaudado = 0.0;
        try {
        
            icon = ipool.obtenerConexion(false);

            if (daop.member(codP, icon)) {
                Producto prod = daop.find(codP, icon);
                total_recaudado = prod.totalRecaudado(icon);
            } else {
                throw new ProductoNoExisteException("El producto con codigo: " + codP + " no Existe");
            }

            return total_recaudado;

        } catch (PersistenciaException p1) {
            exito = false;
            throw new RemoteException("Error acceso BD", p1);
        }
        finally{
             try{
                if(icon != null)
                ipool.liberarConexion(icon, exito);
                
            }catch(PersistenciaException p2){
                throw new RemoteException("Error acceso BD", p2);
            }
        }

    }

     public static Fachada getInstancia() throws RemoteException, IOException, InstantiationException {
	    try{
            if (instancia == null) {
	    	instancia = new Fachada();
	        }
	    return instancia;
        }catch(InstantiationException |IOException | IllegalAccessException | ClassNotFoundException e1){
            throw new RemoteException("Error al obtener una instancia", e1);
        }
    }

}
package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.excepciones.ProductoExisteException;
import logica.excepciones.ProductoNoExisteException;
import logica.excepciones.VentaNoExisteException;
import logica.valueobjects.VOProdVentas;
import logica.valueobjects.VOProducto;
import logica.valueobjects.VOVenta;
import logica.valueobjects.VOVentaTotal;

public interface IFachada extends Remote{

    public void altaProducto(VOProducto voP) throws RemoteException, ProductoExisteException, PersistenciaException;
    public void bajaProducto(String CodP) throws RemoteException, PersistenciaException, ProductoNoExisteException;
    public void registroVenta(String CodP, VOVenta voV) throws RemoteException, PersistenciaException, ProductoNoExisteException;
    public VOVenta datosVenta(String codP, int numV) throws RemoteException, ProductoNoExisteException, VentaNoExisteException, PersistenciaException;
    public List<VOProducto> listadoProductos() throws RemoteException, PersistenciaException, ProductoNoExisteException;
    public List<VOVentaTotal> listadoVentas(String codP) throws RemoteException, VentaNoExisteException, ProductoNoExisteException, PersistenciaException;
    public VOProdVentas productoMasUnidadesVendidas() throws RemoteException, PersistenciaException, ProductoNoExisteException;
    public double totalRecaudadoPorVentas(String codP)throws RemoteException, ProductoNoExisteException, PersistenciaException;

}

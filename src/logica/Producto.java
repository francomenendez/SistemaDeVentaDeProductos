package logica;
import logica.excepciones.PersistenciaException;
import logica.valueobjects.VOVentaTotal;
import persistencia.daos.IDAOVentas;
import poolconexiones.IConexion;

import java.util.List;

public class Producto {

    private String codigo, nombre;
    private int precio;
    private IDAOVentas secuencia;

    public Producto(String cod, String nom, int p, IDAOVentas idao) {
        codigo = cod;
        nombre = nom;
        precio = p;
        secuencia = idao;
    }

    public String getCodigo(){
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }
    
    public int getPrecio() {
        return precio;
    }

    public int cantidadVentas(IConexion icon) throws PersistenciaException{
        return secuencia.largo(icon);
    }

    public void agregarVenta(Venta ven, IConexion icon) throws PersistenciaException{
        secuencia.insback(ven,icon);
    }

    public Venta obtenerVenta(int numV, IConexion icon) throws PersistenciaException{
        return secuencia.k_esimo(numV,icon);
    }

    public void borrarVentas(IConexion icon) throws PersistenciaException{
        secuencia.borrarVentas(icon);
    }

    public List<VOVentaTotal> listarVentas(IConexion icon) throws PersistenciaException{
        return secuencia.listarVentas(icon);
    }

    public double totalRecaudado(IConexion icon) throws PersistenciaException{
        return secuencia.totalRecaudado(icon);
    }
}

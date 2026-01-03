package persistencia.daos;
import persistencia.consultas.*;
import logica.Venta;
import logica.excepciones.PersistenciaException;

import java.sql.*;
import poolconexiones.*;

import java.util.LinkedList;
import java.util.List;
import logica.valueobjects.VOVentaTotal;


public interface IDAOVentas {

    public void insback(Venta ven, IConexion icon) throws PersistenciaException;

    public int largo(IConexion icon) throws PersistenciaException;

    public Venta k_esimo(int numV, IConexion icon) throws PersistenciaException;

    public List<VOVentaTotal> listarVentas(IConexion icon) throws PersistenciaException;

    public double totalRecaudado(IConexion icon) throws PersistenciaException;

    public void borrarVentas(IConexion icon) throws PersistenciaException;
}

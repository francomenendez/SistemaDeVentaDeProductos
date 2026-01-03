package persistencia.daos;
import persistencia.consultas.*;
import logica.valueobjects.*;
import logica.Producto;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import logica.excepciones.PersistenciaException;
import poolconexiones.*;

public interface IDAOProductos {

    boolean member (String codP, IConexion  icon) throws PersistenciaException;

    void insert (Producto prod, IConexion icon) throws PersistenciaException;

    Producto find (String codP, IConexion icon) throws PersistenciaException;

    void delete (String codP, IConexion icon) throws PersistenciaException;

    boolean esVacio (IConexion icon) throws PersistenciaException;

   List<VOProducto> listarProductos (IConexion icon) throws PersistenciaException;

    VOProdVentas ProductoMasVendido(IConexion icon) throws PersistenciaException;
}
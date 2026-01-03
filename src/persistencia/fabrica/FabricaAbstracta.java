package persistencia.fabrica;

import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;

public interface FabricaAbstracta {

    IDAOProductos crearDAOProductos ();
    IDAOVentas crearDAOVentas (String codP);
}

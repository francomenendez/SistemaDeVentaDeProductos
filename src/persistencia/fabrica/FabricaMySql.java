package persistencia.fabrica;

import persistencia.daos.DAOProductos;
import persistencia.daos.DAOVentas;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;

public class FabricaMySql implements FabricaAbstracta{

    @Override
    public IDAOProductos crearDAOProductos() {
        return new DAOProductos();
    }

    @Override
    public IDAOVentas crearDAOVentas(String codP) {
        return new DAOVentas(codP);
    }
}

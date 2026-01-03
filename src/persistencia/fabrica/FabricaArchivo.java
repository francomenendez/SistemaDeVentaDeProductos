package persistencia.fabrica;

import persistencia.daos.DAOProductosArchivo;
import persistencia.daos.DAOVentasArchivo;
import persistencia.daos.IDAOProductos;
import persistencia.daos.IDAOVentas;

public class FabricaArchivo implements FabricaAbstracta{
 @Override
    public IDAOProductos crearDAOProductos() {
        return new DAOProductosArchivo();
    }

    @Override
    public IDAOVentas crearDAOVentas(String codP) {
        return new DAOVentasArchivo(codP);
    }
}

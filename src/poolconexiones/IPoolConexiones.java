package poolconexiones;

import logica.excepciones.PersistenciaException;

public interface IPoolConexiones {
    public IConexion obtenerConexion(boolean modifica) throws PersistenciaException;
    public void liberarConexion(IConexion icon, boolean ok) throws PersistenciaException;
}

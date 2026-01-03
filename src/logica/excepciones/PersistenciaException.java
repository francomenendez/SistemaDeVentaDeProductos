package logica.excepciones;

public class PersistenciaException extends Exception{

    public PersistenciaException(String msg, Exception ex) {
        super(msg, ex);
    } 
    
}
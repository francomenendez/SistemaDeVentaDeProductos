package logica.excepciones;

public class ProductoNoExisteException extends Exception{

    public ProductoNoExisteException(String msg) {
        super(msg);
    }
    
}
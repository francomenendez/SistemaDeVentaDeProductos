package logica.valueobjects;

import java.io.Serializable;

public class VOProdVentas extends VOProducto{
    private int unidadesVendidas;
    
    public VOProdVentas(String cod, String nom, int pr, int uv) {
    	super(cod, nom, pr);
        unidadesVendidas = uv;
    }
    
    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }
}
package logica.valueobjects;

import java.io.Serializable;

public class VOVentaTotal extends VOVenta{
    private int numero;
    private String codPro;
    
    public VOVentaTotal(int un, String cl,int num, String cp) {
    	super(un,cl);
        numero = num;
        codPro = cp;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String codPro() {
        return codPro;
    }
}
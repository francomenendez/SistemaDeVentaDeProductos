package logica.valueobjects;

import java.io.Serializable;

public class VOVenta implements Serializable {
    private static final long serialVersionUID = 1L; // Se recomienda incluirlo
    private int unidades;
    private String cliente;
    
    public VOVenta(int un, String cl) {
    	unidades = un;
        cliente = cl;
    }

    public String getCliente() {
        return cliente;
    }

    public int getUnidades() {
        return unidades;
    }

    
}
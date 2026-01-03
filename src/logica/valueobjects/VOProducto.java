package logica.valueobjects;

import java.io.Serializable;

public class VOProducto implements Serializable {
    private static final long serialVersionUID = 1L; // Se recomienda incluirlo
    private String codigo;
    private String nombre;
    private int precio;
    

    public VOProducto(String cod, String nom, int pr) {
        codigo = cod;
        nombre = nom;
        precio = pr;

    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }


}
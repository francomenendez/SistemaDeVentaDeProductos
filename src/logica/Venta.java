package logica;

public class Venta {

    private int numero, unidades;
    private String cliente;


    public Venta(int num, int uni, String cli){
        numero = num;
        unidades = uni;
        cliente = cli;
    }

    public int getNumero(){
        return numero;
    }

    public int getUnidades(){
        return unidades;
    }

    public String getCliente(){
        return cliente;
    }
}

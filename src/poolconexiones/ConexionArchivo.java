package poolconexiones;

public class ConexionArchivo implements IConexion{
    private boolean esEscritor;
    private boolean habilita;
    private String nomArch;

    public ConexionArchivo(boolean esEsc, boolean hab, String arch){
       this.esEscritor = esEsc;
       this.habilita = hab;
       this.nomArch = arch;
    }

    public boolean getEsEscritor(){
        return esEscritor;
    }

    public boolean getHabilita(){
        return habilita;
    }

    public String getNomarch(){
        return nomArch;
    }

    public void setNomArch(String nom){
        this.nomArch = nom;
    }
}


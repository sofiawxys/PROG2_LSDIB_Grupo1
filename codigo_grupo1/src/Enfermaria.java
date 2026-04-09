import java.util.ArrayList;
import java.util.List;

public abstract class Enfermaria {

    private String idEnfermaria;
    private int numCamas;
    private List<Episodio> episodios;

    public Enfermaria(int idEnfermaria, int numCamas, int episodios) {
        this.idEnfermaria = idEnfermaria;
        this.numCamas = numCamas;
        this.episodios = new ArrayList<>();
    }

    public String getIdEnfermaria() {
        return idEnfermaria;
    }

    public void setIdEnfermaria(int idEnfermaria) {
        this.idEnfermaria = idEnfermaria;
    }

    public int getNumCamas (){
        return numCamas;
    }

    public void setNumCamas(int numCamas){
        this.numCamas = idEnfermaria;
    }

    public int getEpisodios (){
        return episodios;
    }

    public void setEpisodios(int idEnfermaria){
        this.episodios = episodios;
    }




}


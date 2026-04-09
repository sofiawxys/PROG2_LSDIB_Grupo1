import java.util.ArrayList;
import java.util.List;

public abstract class Enfermaria extends Data {

    private String idEnfermaria;
    private int numCamas;
    private List<Episodio> episodios;

    public Enfermaria() {
        this.idEnfermaria = idEnfermaria;
        this.numCamas = numCamas;
        this.episodios = new ArrayList<>();
    }

    public String getIdEnfermaria() {
        return idEnfermaria;
    }

    public void setIdEnfermaria(String idEnfermaria) {
        this.idEnfermaria = idEnfermaria;
    }

    public int getNumCamas (){
        return numCamas;
    }

    public void setNumCamas(int numCamas){
        this.numCamas = numCamas;
    }

    public List<Episodio> getEpisodios (){
        return episodios;
    }

    public void setEpisodios(int idEnfermaria){
        this.episodios = episodios;
    }

    public int calcularOcupacao(Data datareferencia){
        int ocupacao = 0;
        for(Episodio episodio : episodios){
            if(episodio.isAtivo(datareferencia)){
                ocupacao++;
            }
        }
        return ocupacao;
    }

    public double calcularTaxaOcupacao(Data datareferencia){
        double taxaOcupacao = (calcularOcupacao(datareferencia)*100)/numCamas;
        if(numCamas == 0){
            return 0;
        }else{
            return taxaOcupacao;
        }
    }

    public boolean EmPressao(Data datareferencia, double taxaOcupacao){
        if(taxaOcupacao > 85) {
            return true;
        }else{
            return false;
        }
    }




}


import java.util.ArrayList;
import java.util.List;

public abstract class Enfermaria{

    private String idEnfermaria;
    private int numCamas;
    private List<Episodio> episodios;

    public Enfermaria(String idEnfermaria, int numCamas) {
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

    public void adicionarEpisodio(Episodio episodio){
        this.episodios.add(episodio);
    }

    public void setEpisodios(List<Episodio> episodios) {
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
        if(numCamas == 0){
            return 0;
        }else{
            double taxaOcupacao = (calcularOcupacao(datareferencia)*100)/numCamas;
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

    // Identificar episódios com data de alta e criar uma lista só com esses episódios
    private List<Episodio> getEpisodiosComAlta() {
        List<Episodio> comAlta = new ArrayList<>();
        for (Episodio episodio : episodios) {
            if (episodio.isFlagAlta()) {
                comAlta.add(episodio);
            }
        }
        return comAlta;
    }

    //Medidas de sumário do LoS
    //Calcular a Média
    public double calcularMediaLoS(){
        double soma = 0;
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()){
            return 0;
        }

        for (Episodio episodio : comAlta) {
            soma += episodio.calcularLoS();
        }
        return soma / comAlta.size();
    }

    //Calcular Desvio Padrão
    public double calcularDesvioPadraoLos(){
        double desvioPadraoLos = 0;
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()){
            return 0;
        }

        double mediaLoS = calcularMediaLoS();
        double somaDiferencasQuadrado = 0;

        for (Episodio episodio : comAlta) {
            double diferenca = episodio.calcularLoS() - mediaLoS;
            somaDiferencasQuadrado += diferenca * diferenca;

        }
        return Math.sqrt(somaDiferencasQuadrado/comAlta.size());

    }

    //Calcular Mínimo de LoS
    public int calcularMinLoS(){
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()){
            return 0;
        }
        int minLoS = comAlta.get(0).calcularLoS();
        for (Episodio episodio : comAlta) {
            if (episodio.calcularLoS() < minLoS){
                minLoS = episodio.calcularLoS();
            }
        }
        return minLoS;
    }

    //Calcular Máximo de loS
    public int calcularMaxLoS(){
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()){
            return 0;
        }
        int maxLoS = comAlta.get(0).calcularLoS();
        for (Episodio episodio : comAlta) {
            if(episodio.calcularLoS() > maxLoS){
                maxLoS = episodio.calcularLoS();
            }
        }
        return maxLoS;
    }



}


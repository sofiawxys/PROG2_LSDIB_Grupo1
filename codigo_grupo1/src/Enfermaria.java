import java.util.ArrayList;
import java.util.List;


public abstract class Enfermaria implements GestaoOcupacao {

    private static int LIMITE_PRESSAO = 85;
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

    public int getNumCamas() {
        return numCamas;
    }

    public void setNumCamas(int numCamas) {
        this.numCamas = numCamas;
    }

    public List<Episodio> getEpisodios() {
        return new ArrayList<>(episodios); // devolve uma cópia defensiva
    }

    //Não colocamos um setEpisodios propositadamente por segurança. Isto protege a integridade dos dados, esta classe tem controlo total sobre o que entra e sai da lista
    //Impedindo que a lista inteira seja acidentalmente substituida ou apagada do exterior.

    public void adicionarEpisodio(Episodio episodio) {
        this.episodios.add(episodio);
    }

@Override
    public int calcularOcupacao(Data dataReferencia) {
        int ocupacao = 0;
        for (Episodio episodio : episodios) {
            if (episodio.isAtivo(dataReferencia)) {
                ocupacao++;
            }
        }
        return ocupacao;
    }

@Override
    public double calcularTaxaOcupacao(Data dataReferencia) {
        if (numCamas == 0) {
            return 0;
        } else {
            double taxaOcupacao = (calcularOcupacao(dataReferencia) * 100.0) / numCamas;
            return taxaOcupacao;
        }
    }

    @Override
    public boolean isEmPressao(Data datareferencia) {
        if (calcularTaxaOcupacao(datareferencia) > LIMITE_PRESSAO) {
            return true;
        } else {
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
    public double calcularMediaLoS() {
        double soma = 0;
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()) {
            return 0;
        }

        for (Episodio episodio : comAlta) {
            soma += episodio.calcularLoS();
        }
        return soma / comAlta.size();
    }

    //Calcular Desvio Padrão
    public double calcularDesvioPadraoLos() {
        double desvioPadraoLos = 0;
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()) {
            return 0;
        }

        double mediaLoS = calcularMediaLoS();
        double somaDiferencasQuadrado = 0;

        for (Episodio episodio : comAlta) {
            double diferenca = episodio.calcularLoS() - mediaLoS;
            somaDiferencasQuadrado += diferenca * diferenca;

        }
        return Math.sqrt(somaDiferencasQuadrado / comAlta.size());

    }

    //Calcular Mínimo de LoS
    public int calcularMinLoS() {
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()) {
            return 0;
        }
        int minLoS = comAlta.get(0).calcularLoS();
        for (Episodio episodio : comAlta) {
            if (episodio.calcularLoS() < minLoS) {
                minLoS = episodio.calcularLoS();
            }
        }
        return minLoS;
    }

    //Calcular Máximo de loS
    public int calcularMaxLoS() {
        List<Episodio> comAlta = getEpisodiosComAlta();
        if (comAlta.isEmpty()) {
            return 0;
        }
        int maxLoS = comAlta.get(0).calcularLoS();
        for (Episodio episodio : comAlta) {
            if (episodio.calcularLoS() > maxLoS) {
                maxLoS = episodio.calcularLoS();
            }
        }
        return maxLoS;
    }


}


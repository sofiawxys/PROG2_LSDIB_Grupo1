public abstract class Enfermaria extends Data {

    private String idEnfermaria;
    private int numCamas;
    private int episodios;

    public Enfermaria(String idEnfermaria, int numCamas, int episodios) {
        this.idEnfermaria = idEnfermaria;
        this.numCamas = numCamas;
        this.episodios = episodios;
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

    public int getEpisodios (){
        return episodios;
    }

    public void setEpisodios(int idEnfermaria){
        this.episodios = episodios;
    }

    public void calcularOcupacao(Data datareferencia){
        int ocupacao = episodios
    }




}


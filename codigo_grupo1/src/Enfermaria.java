public abstract class Enfermaria {

    private int idEnfermaria;
    private int numCamas;
    private int episodios;

    public Enfermaria(int idEnfermaria, int numCamas, int episodios) {
        this.idEnfermaria = idEnfermaria;
        this.numCamas = numCamas;
        this.episodios = episodios;
    }

    public int getIdEnfermaria() {
        return idEnfermaria;
    }

    public void stIdEnfermaria(int idEnfermaria) {
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


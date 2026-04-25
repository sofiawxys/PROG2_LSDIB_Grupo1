public class Episodio{
    private int idCama;
    private Data dataAdmissao;
    private Data dataAlta;
    private int los; // Length of Stay em dias
    private boolean flagAlta; // true se o episódio já tem alta

    public Episodio(int idCama, Data dataAdmissao, Data dataAlta) {
        this.idCama = idCama;
        //Encapsulamento: guardar uma cópia da data
        this.dataAdmissao = new Data(dataAdmissao);
        if  (dataAlta != null) {
            if(dataAdmissao.isMaior(dataAlta)) {
                System.out.println("Data inválida. Episódio considerado como ativo, se quiser mude a data de alta.");
                this.dataAlta = null;
                this.flagAlta = false;
            }
            this.dataAlta = new Data(dataAlta); //cópia da data de alta
            this.flagAlta = true;
        } else{
            this.dataAlta = null;
            this.flagAlta = false;
        }

        calcularLoS();
    }

    // cálculo do LoS
    public int calcularLoS() {
        if (this.flagAlta==false || this.dataAlta==null) {
            this.los = -1; // não aplicável
        }
        this.los = dataAdmissao.calcularDiferenca(dataAlta);
        return this.los;
    }

    //Getters
    public int getIdCama() {
        return idCama;
    }

    public Data getDataAdmissao() {
        return new Data(dataAdmissao); //devolve cópia defensiva
    }

    public Data getDataAlta() {
        return new Data (dataAlta); //devolve cópia defensiva
    }

    public int getLoS() {
        return los;
    }

    public boolean isFlagAlta() {
        return flagAlta;
    }

    //Setters
    public void setIdCama(int idCama) {
        this.idCama = idCama;
    }

    public void setDataAdmissao(Data d) {
        this.dataAdmissao = new Data(d); //guarda cópia defensiva
        calcularLoS();
    }

    public void setDataAlta(Data dataAlta) {
        this.dataAlta = new Data (dataAlta); // guarda cópia defensiva
        this.flagAlta = (dataAlta != null);
        calcularLoS(); //recalcular
    }

    //ver se o episódio está ativo
    public boolean isAtivo(Data dataReferencia) {
        // Episódio ativo se foi admitido até à data de referência
        // e ainda não teve alta (ou a alta é após a data de referência)
        boolean admitido = !dataAdmissao.isMaior(dataReferencia);
        boolean semAlta = !flagAlta || dataAlta.isMaior(dataReferencia);
        return admitido && semAlta;
    }

    @Override
    public String toString() {

        String textoDataAlta;
        String textoLos;

        if (dataAlta == null) {
            textoDataAlta = "—"; // (Ainda internado)
            textoLos= "N/A";
        } else {
            textoDataAlta = dataAlta.toString();
            textoLos= los + " dias";
        }

        return String.format("ID: %d, Data Admissão: %s, Data Alta: %s, LoS: %s",
                idCama, dataAdmissao, textoDataAlta, textoLos);
    }
}
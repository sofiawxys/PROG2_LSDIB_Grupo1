public class Episodio{
    private int idCama;
    private Data dataAdmissao;
    private Data dataAlta;
    private int los; // Length of Stay em dias
    private boolean flagAlta; // true se o episódio já tem alta

    public Episodio(int idCama, Data dataAdmissao, Data dataAlta) {
        this.idCama = idCama;
        this.dataAdmissao = dataAdmissao;
        if  (dataAlta != null) {
            if(dataAlta.isMaior(dataAdmissao)) {
                System.out.println("Data inválida. Episódio considerado como ativo, se quiser mude a data de alta.");
                this.dataAlta = null;
                this.flagAlta = false;
            }
            this.dataAlta = dataAlta;
            this.flagAlta = true;
        } else{
            this.dataAlta = null;
            this.flagAlta = false;
        }
    }

    // cálculo do LoS
    public int calcularLoS() {
        if (this.flagAlta==false || this.dataAlta==null) {
            return -1; // não aplicável
        }
        this.los = dataAdmissao.calcularDiferenca(dataAlta);
        return this.los;
    }

    //Getters
    public int getIdCama() {
        return idCama;
    }

    public Data getDataAdmissao() {
        return dataAdmissao;
    }

    public Data getDataAlta() {
        return dataAlta;
    }

    public long getLoS() {
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
        this.dataAdmissao = d;
        calcularLoS();
    }

    public void setDatAlta(Data dataAlta) {
        this.dataAlta = dataAlta;
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
        // 1. Criamos uma variável para guardar o texto da data de alta
        String textoDataAlta;

        // 2. Usamos um if/else normal para preencher essa variável
        if (dataAlta == null) {
            textoDataAlta = "—"; // (ou "Ainda internado")
        } else {
            textoDataAlta = dataAlta.toString();
        }

        // 3. Imprimimos tudo usando a nossa nova variável
        return String.format("ID: %d, Data Admissão: %s, Data Alta: %s, LoS: %d dias",
                idCama, dataAdmissao, textoDataAlta, los);
    }
}
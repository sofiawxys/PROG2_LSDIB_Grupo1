public class EnfermariaCuidadosIntensivos extends Enfermaria {
    private String horarioVisitas;
    private double pressaoAtmosferica;
    private double pressaoReferencia;

    public EnfermariaCuidadosIntensivos(String idEnfermaria, int numCamas, String horarioVisitas, double pressaoAtmosferica, double pressaoReferencia) {
        super(idEnfermaria, numCamas);
        this.horarioVisitas = horarioVisitas;
        this.pressaoAtmosferica = pressaoAtmosferica;
        this.pressaoReferencia = pressaoReferencia;
    }

    //Getters e Setters
    public String getHorarioVisitas() {
        return horarioVisitas;
    }
    public void setHorarioVisitas(String horarioVisitas) {
        this.horarioVisitas = horarioVisitas;
    }

    public double getPressaoAtmosferica() {
        return pressaoAtmosferica;
    }
    public void setPressaoAtmosferica(double pressaoAtmosferica) {
        this.pressaoAtmosferica = pressaoAtmosferica;
    }

    public double getPressaoReferencia() {
        return pressaoReferencia;
    }
    public void setPressaoReferencia(double pressaoReferencia) {
        this.pressaoReferencia = pressaoReferencia;
    }

    @Override
    public String toString() {
        return String.format("Cuidados Intensivos [%s], Camas: %d, Visitas: %s, Pressão atual: %.2f, Pressão referência: %.2f",
                getIdEnfermaria(),getNumCamas(),this.horarioVisitas,this.pressaoAtmosferica,this.pressaoReferencia);
    }
}

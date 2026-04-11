public class EnfermariaPsiquiatrica extends Enfermaria {

    private String horarioVisitas;
    private String nivelSeguranca;

    public EnfermariaPsiquiatrica(String idEnfermaria, int numCamas,String horarioVisitas, String nivelSeguranca){
        super (idEnfermaria,numCamas);
        this.horarioVisitas = horarioVisitas;
        this.nivelSeguranca = nivelSeguranca;
    }

    //Getters e Setters
    public String getHorarioVisitas() {
        return horarioVisitas;
    }
    public void setHorarioVisitas(String horarioVisitas) {
        this.horarioVisitas = horarioVisitas;
    }
    public String getNivelSeguranca() {
        return nivelSeguranca;
    }
    public void setNivelSeguranca(String nivelSeguranca) {
        this.nivelSeguranca = nivelSeguranca;
    }

    @Override
    public String toString() {
        return String.format("Enfermaria Psiquiátrica [%s], Camas: %d, Visitas: %s, Segurança: %s",
                getIdEnfermaria(),getNumCamas(),this.horarioVisitas,this.nivelSeguranca);
    }
}

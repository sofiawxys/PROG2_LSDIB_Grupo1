public class EnfermariaPsiquiatrica {

    private int horarioVisitas;
    private String nivelSeguranca;

    public EnfermariaPsiquiatrica(int horarioVisitas, String nivelSeguranca){
        this.horarioVisitas = horarioVisitas;
        this.nivelSeguranca = nivelSeguranca;
    }
    public int getHorarioVisitas() {
        return horarioVisitas;
    }
    public void setHorarioVisitas(int horarioVisitas) {
        this.horarioVisitas = horarioVisitas;
    }
    public String getNivelSeguranca() {
        return nivelSeguranca;
    }
    public void setNivelSeguranca(String nivelSeguranca) {
        this.nivelSeguranca = nivelSeguranca;
    }
}

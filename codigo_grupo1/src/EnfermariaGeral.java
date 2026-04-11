import java.util.ArrayList;
import java.util.List;

public class EnfermariaGeral extends Enfermaria {
    private int numAcompanhantes;
    private List<String> recursos;

    public EnfermariaGeral(String idEnfermaria, int numCamas, int numAcompanhantes) {
        super(idEnfermaria,numCamas);
        this.numAcompanhantes = numAcompanhantes;
        this.recursos = new ArrayList<String>(); //iniciar a lista como lista vazia
    }

    //Getters e Setters
    public int getNumAcompanhantes() {
        return numAcompanhantes;
    }

    public void setNumAcompanhantes(int numAcompanhantes) {
        this.numAcompanhantes = numAcompanhantes;
    }

    public List<String> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<String> recursos) {
        this.recursos = recursos;
    }

    //nao criamos setRecursos e assim a lista é gerida pelos seguintes métodos
    //isto protege a integridade dos dados, esta classe tem controlo total sobre o que ntra e sai da lista
    //impedindo que a lista inteira seja acidentalmente substituida ou apagada do exterior
    public void adicionarRecurso (String recurso){
        this.recursos.add(recurso);
    }
    public void removerRecurso (String recurso){
        this.recursos.remove(recurso);
    }

    @Override
    public String toString() {
        return String.format("Enfermaria Geral [%s] | Camas: %d | Acompanhantes: %d | Recursos: %s",
                getIdEnfermaria(), // vem da classe mãe
                getNumCamas(),   // vem da classe mãe
                this.numAcompanhantes,
                this.recursos.toString());
    }
}

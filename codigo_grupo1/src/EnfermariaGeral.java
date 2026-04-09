import java.util.ArrayList;
import java.util.List;

public class EnfermariaGeral extends Enfermaria {
    private int numAcompanhantes;
    private List<String> recursos;

    public EnfermariaGeral(String idEnfermaria, int numCamas, int numAcompanhantes) {
        super();
        this.numAcompanhantes = numAcompanhantes;
        this.recursos = new ArrayList<String>();
    }

}

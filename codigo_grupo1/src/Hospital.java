import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hospital {
    private String nome;
    private List<Enfermaria> enfermarias;
    private List<String> regsitoErros;

    public Hospital(String nome) {
        this.nome = nome;
        this.enfermarias = new ArrayList<Enfermaria>();
        this.regsitoErros = new ArrayList<String>();
    }

    //Getters

    public String getNome() {
        return nome;
    }

    public List<String> getRegistoErros() {
        return regsitoErros;
    }

    public Enfermaria procurarEnfermaria(String id) {
        for (Enfermaria enfermaria : this.enfermarias) {
            if (enfermaria.getIdEnfermaria().equals(id)) {
                return enfermaria;
            }
        }
        return null;
    }

    public void carregarEpisodios(String nomeFicheiroCSV) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroCSV);

        Scanner lerFicheiro = new Scanner(ficheiro);

        if(lerFicheiro.hasNextLine()) {
            lerFicheiro.nextLine();
        }

        while (lerFicheiro.hasNextLine()) {
            String linha = lerFicheiro.nextLine();

        }

        lerFicheiro.close();
    }

    private void processarLinhaEpisodio(String linha){
        String [] partes = linha.split(";");
        if(partes.length <3){

        }
    }
}

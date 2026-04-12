import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Hospital {
    private String nome;
    private List<Enfermaria> enfermarias;
    private List<String> registoErros;

    public Hospital(String nome) {
        this.nome = nome;
        this.enfermarias = new ArrayList<Enfermaria>();
        this.registoErros = new ArrayList<String>();
    }

    //Getters

    public String getNome() {
        return nome;
    }

    public List<String> getRegistoErros() {
        return registoErros;
    }

    public void adicionarEnfermaria(Enfermaria enfermaria) {
        this.enfermarias.add(enfermaria);
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

        if (lerFicheiro.hasNextLine()) {
            lerFicheiro.nextLine(); // ignora o cabeçalho
        }

        while (lerFicheiro.hasNextLine()) {
            String linha = lerFicheiro.nextLine();
            processarLinhaEpisodio(linha);
        }

        lerFicheiro.close();
    }

    private void processarLinhaEpisodio(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 3) {
            registoErros.add("Episódio inválido por falta de dados: " + linha);
            return;
        }

        String idEnfermaria = partes[0];
        String idCamaStr = partes[1];
        String dataAdmStr = partes[2];

        Enfermaria enfermaria = procurarEnfermaria(idEnfermaria);
        if (enfermaria == null) {
            registoErros.add("Enfermaria não encontrada: " + linha);
            return;
        }

        if (!isNumero(idCamaStr)) {
            registoErros.add("ID de cama não é um numero: " + linha);
            return;
        }
        int idCama = Integer.parseInt(idCamaStr);

        Data dataAdmissao = extrairData(dataAdmStr);
        if (dataAdmissao == null) {
            registoErros.add("Data de admissão com formato incorreto: " + linha);
            return;
        }

        Data dataAlta = null;
        if (partes.length >= 4) {
            String dataAltaStr = partes[3].trim();
            if (!dataAltaStr.isEmpty()) {
                dataAlta = extrairData(dataAltaStr);
                if (dataAlta == null) {
                    registoErros.add("Data de alta com formato incorreto: " + linha);
                    return;
                }
            }
        }
        Episodio ep = new Episodio(idCama, dataAdmissao, dataAlta);
        enfermaria.adicionarEpisodio(ep);
    }

    private boolean isNumero(String texto) {
        if (texto == null) {
            return false;
        }
        String textoLimpo = texto.trim();
        if (textoLimpo.isEmpty()) {
            return false;
        }

        for (int i = 0; i < textoLimpo.length(); i++) {
            char c = textoLimpo.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    private Data extrairData(String dataStr) {
        String[] partesData = dataStr.split("-"); // assumindo que as datas estão separadas por "-"

        if (partesData.length != 3) {
            return null;
        }

        if (!isNumero(partesData[0]) || !isNumero(partesData[1]) || !isNumero(partesData[2])) {
            return null;
        }

        int ano = Integer.parseInt(partesData[0]);
        int mes = Integer.parseInt(partesData[1]);
        int dia = Integer.parseInt(partesData[2]);

        return new Data(ano, mes, dia);
    }

    public List<Enfermaria> listarEnfermariasOrdenadasPorOcupacao(Data dataReferencia) {
        List<Enfermaria> enfermariasOrdenadasOcupacao = new ArrayList<Enfermaria>(enfermarias);

        enfermariasOrdenadasOcupacao.sort(new Comparator<Enfermaria>() {
            @Override
            public int compare(Enfermaria e1, Enfermaria e2) {
                double taxa1 = e1.calcularTaxaOcupacao(dataReferencia);
                double taxa2 = e2.calcularTaxaOcupacao(dataReferencia);
                return Double.compare(taxa2, taxa1); //ordem decrescente
            }
        });
        return enfermariasOrdenadasOcupacao;
    }
}



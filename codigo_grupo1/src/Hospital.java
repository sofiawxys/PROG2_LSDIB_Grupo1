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

    public void carregarEnfermarias(String nomeFicheiroCSV) throws FileNotFoundException {
        File ficheiro = new File(nomeFicheiroCSV);
        Scanner lerFicheiro = new Scanner(ficheiro);

        // Ignorar o cabeçalho
        if (lerFicheiro.hasNextLine()) {
            lerFicheiro.nextLine();
        }

        while (lerFicheiro.hasNextLine()) {
            String linha = lerFicheiro.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }
            processarLinhaEnfermaria(linha);
        }
        lerFicheiro.close();
    }

    private void processarLinhaEnfermaria(String linha) {
        String[] partes = linha.split(";", -1);

        if (partes.length < 3) {
            registoErros.add("Enfermaria inválida por falta de dados: " + linha);
            return;
        }

        String id          = partes[0].trim();
        String numCamasStr = partes[1].trim();
        String tipo        = partes[2].trim();

        if (id.isEmpty()) {
            registoErros.add("ID de enfermaria vazio: " + linha);
            return;
        }

        if (!isNumero(numCamasStr)) {
            registoErros.add("Número de camas inválido: " + linha);
            return;
        }
        int numCamas = Integer.parseInt(numCamasStr);

        if (tipo.equals("GERAL")) {
            if (partes.length < 4 || !isNumero(partes[3].trim())) {
                registoErros.add("Número de acompanhantes inválido: " + linha);
                return;
            }
            int numAcompanhantes = Integer.parseInt(partes[3].trim());
            EnfermariaGeral eg = new EnfermariaGeral(id, numCamas, numAcompanhantes);

            // Recursos são opcionais
            if (partes.length >= 5 && !partes[4].trim().isEmpty()) {
                String[] recursos = partes[4].split(",");
                for (String recurso : recursos) {
                    eg.adicionarRecurso(recurso.trim());
                }
            }
            this.enfermarias.add(eg);

        } else if (tipo.equals("PSIQUIATRICA")) {
            if (partes.length < 5 || partes[3].trim().isEmpty() || partes[4].trim().isEmpty()) {
                registoErros.add("Dados insuficientes para Enfermaria Psiquiátrica: " + linha);
                return;
            }
            String horario        = partes[3].trim();
            String nivelSeguranca = partes[4].trim();
            this.enfermarias.add(new EnfermariaPsiquiatrica(id, numCamas, horario, nivelSeguranca));

        } else if (tipo.equals("UCI")) {
            if (partes.length < 6 || partes[3].trim().isEmpty()) {
                registoErros.add("Dados insuficientes para Enfermaria UCI: " + linha);
                return;
            }
            String horario = partes[3].trim();
            double pressaoAtual;
            double pressaoRef;
            try {
                pressaoAtual = Double.parseDouble(partes[4].trim());
                pressaoRef   = Double.parseDouble(partes[5].trim());
            } catch (NumberFormatException e) {
                registoErros.add("Valores de pressão inválidos: " + linha);
                return;
            }
            this.enfermarias.add(
                new EnfermariaCuidadosIntensivos(id, numCamas, horario, pressaoAtual, pressaoRef));

        } else {
            registoErros.add("Tipo de enfermaria desconhecido (" + tipo + "): " + linha);
        }
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



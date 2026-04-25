import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int LIMITE_INF_OPCAO = 1;
    private static int LIMITE_SUP_OPCAO = 6;
    private static int LIMITE_SUP_SUBOPCAO = 2;

    public static void main(String[] args) throws FileNotFoundException {
        Hospital hospital = new Hospital("Hospital S.João");
        Scanner scanner = new Scanner(System.in);
        int opcao =0;
        while (opcao != 6) {
            mostrarMenu();
            opcao= lerOpcao(scanner, LIMITE_INF_OPCAO, LIMITE_SUP_OPCAO);

            switch (opcao) {
                case 1:
                    criarDadosAutomaticamente(hospital);
                    break;
                case 2:
                    carregarDadosFicheiro(hospital);
                    break;

               /* Enfermaria eg1 = hospital.procurarEnfermaria("eg1");
                Enfermaria ep1 = hospital.procurarEnfermaria("ep1");
                Enfermaria ec1 = hospital.procurarEnfermaria("eci1");

                if (eg1 == null || ep1 == null || ec1 == null) {
                    System.out.println("Erro: enfermarias não encontradas. Verifica o ficheiro CSV.");
                    return;
                } */

                case 3:
                    mostrarIndicadoresOcupacao(hospital, scanner);
                    break;

                case 4:
                    mostrarEstadoPressao(hospital, scanner);
                    break;
                case 5:
                    mostrarListagens(hospital, scanner);
                    break;
                case 6:
                    System.out.println("A fechar o sistema...");
            }
        }

        scanner.close();
    }

    //MÉTODOS PRINCIPAIS

    private static void mostrarMenu() {
        System.out.println("\n===MENU===");
        System.out.println("1. Criar dados automaticamente");
        System.out.println("2. Carregar dados do ficheiro");
        System.out.println("3. Mostrar cálculo de indicadores de ocupação");
        System.out.println("4. Mostrar estado e indicadores de pressão");
        System.out.println("5. Apresentar listagens ordenadas");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarDadosAutomaticamente(Hospital hospital) {
        EnfermariaGeral eg1 = new EnfermariaGeral("eg1", 4, 2);
        eg1.adicionarRecurso("Cadeira de rodas");
        eg1.adicionarRecurso("Ventilador");

        EnfermariaGeral eg2 = new EnfermariaGeral("eg2", 6, 1);
        eg2.adicionarRecurso("Monitor cardíaco");

        EnfermariaPsiquiatrica ep1 = new EnfermariaPsiquiatrica("ep1", 5, "9:00-11:00", "Alto");

        EnfermariaCuidadosIntensivos eci1 = new EnfermariaCuidadosIntensivos("eci1", 9, "15:00-19:00", 730, 760);

        // Adicionar enfermarias ao hospital
        hospital.adicionarEnfermaria(eg1);
        hospital.adicionarEnfermaria(eg2);
        hospital.adicionarEnfermaria(ep1);
        hospital.adicionarEnfermaria(eci1);

        // Criar episódios — eg1
        eg1.adicionarEpisodio(new Episodio(1, new Data(2025, 3, 1), new Data(2025, 3, 7)));
        eg1.adicionarEpisodio(new Episodio(2, new Data(2025, 3, 3), new Data(2025, 3, 10)));
        eg1.adicionarEpisodio(new Episodio(3, new Data(2025, 3, 5), new Data(2025, 3, 20)));
        eg1.adicionarEpisodio(new Episodio(4, new Data(2025, 3, 18), null));

        // Criar episódios — ep1 (100% ocupação)
        ep1.adicionarEpisodio(new Episodio(1, new Data(2025, 3, 10), null));
        ep1.adicionarEpisodio(new Episodio(2, new Data(2025, 3, 10), null));
        ep1.adicionarEpisodio(new Episodio(3, new Data(2025, 3, 10), null));
        ep1.adicionarEpisodio(new Episodio(4, new Data(2025, 3, 10), null));
        ep1.adicionarEpisodio(new Episodio(5, new Data(2025, 3, 10), null));

        // Criar episódios — eci1
        eci1.adicionarEpisodio(new Episodio(1, new Data(2025, 3, 15), new Data(2025, 3, 18)));
        eci1.adicionarEpisodio(new Episodio(2, new Data(2025, 3, 16), null));
        eci1.adicionarEpisodio(new Episodio(3, new Data(2025, 3, 17), null));

        System.out.println("Dados criados com sucesso!");
    }

    private static void mostrarIndicadoresOcupacao(Hospital hospital, Scanner scanner) {
        System.out.print("Introduza o ID da enfermaria: ");
        String idEnfermaria = scanner.nextLine();

        Enfermaria enfermaria = hospital.procurarEnfermaria(idEnfermaria);

        if (enfermaria == null) {
            System.out.println("Enfermaria não encontrada.");
            return;
        }
        System.out.println("Enfermaria encontrada: " + enfermaria.getIdEnfermaria());
        System.out.println(enfermaria.toString());

        //Pedir ao utilizador para introduzir a data de referência
        System.out.println("\nINDICADORES DE OCUPAÇÃO");
        System.out.println("Introduza a data de referência (AAAA/MM/DD): ");
        String dataReferenciaStr = scanner.nextLine();
        Data dataReferencia = Data.parseData(dataReferenciaStr);


        int ocupacao = enfermaria.calcularOcupacao(dataReferencia);
        double taxaOcupacao = enfermaria.calcularTaxaOcupacao(dataReferencia);
        boolean emPressao = enfermaria.isEmPressao(dataReferencia);
        System.out.println("Ocupação: " + ocupacao);
        System.out.println("Taxa de Ocupação: " + taxaOcupacao + "%");
        if (emPressao) {
            System.out.println("Em Pressão");
        } else {
            System.out.println("Estado Normal");
        }

        System.out.println("\nMEDIDAS DE SUMÁRIO LoS");
        System.out.println("Media LoS: " + enfermaria.calcularMediaLoS());
        System.out.println("Desvio Padrão LoS: " + enfermaria.calcularDesvioPadraoLos());
        System.out.println("Mínimo LoS: " + enfermaria.calcularMinLoS());
        System.out.println("Máximo LoS: " + enfermaria.calcularMaxLoS());

    }

    private static void carregarDadosFicheiro(Hospital hospital) throws FileNotFoundException {
        // Carregar episódios e enfermarias de ficheiros csv
        hospital.carregarEnfermarias("enfermarias.csv");
        hospital.carregarEpisodios("episodios.csv");

        System.out.println("Dados carregados com sucesso!");

        // Mostrar erros registados no log
        if (!hospital.getRegistoErros().isEmpty()) {
            System.out.println("\n--- Erros encontrados no carregamento ---");
            for (String erro : hospital.getRegistoErros()) {
                System.out.println("[LOG] " + erro);
            }
        }
    }

    private static void mostrarEstadoPressao(Hospital hospital, Scanner scanner) {
        System.out.print("Introduza o ID da enfermaria: ");
        String idEnfermaria = scanner.nextLine();

        Enfermaria enfermaria = hospital.procurarEnfermaria(idEnfermaria);

        if (enfermaria == null) {
            System.out.println("Enfermaria não encontrada.");
            return;
        }
        System.out.println("Enfermaria encontrada!");

        //Pedir ao utilizador para introduzir as datas
        System.out.println("Introduza a data de ínicio (AAAA/MM/DD): ");
        String dataInicioStr = scanner.nextLine();
        System.out.println("Introduza data de fim (AAAA/MM/DD): ");
        String dataFimStr = scanner.nextLine();
        Data dataInicio = Data.parseData(dataInicioStr);
        Data dataFim = Data.parseData(dataFimStr);

        if (!dataFim.isMaior(dataInicio) && !dataFim.equals(dataInicio)) {
            System.out.println("Erro: a data de fim tem de ser posterior à data de início.");
            return;
        }

        int totalDias = dataFim.calcularDiferenca(dataInicio) + 1;
        Data dataAtual = new Data(dataInicio); //cópia da data inicial
        int diasEmPressao = 0;

        System.out.println("\n --- Histórico de Ocupação---");
        for (int i = 0; i < totalDias; i++) {
            double taxaOcupacao = enfermaria.calcularTaxaOcupacao(dataAtual);
            boolean emPressao = enfermaria.isEmPressao(dataAtual);
            System.out.println(dataAtual.toString() + " -> ");
            if (emPressao) {
                diasEmPressao++;
                System.out.println("Em Pressão (Taxa: " + String.format("%.2f", taxaOcupacao) + " %)");
            } else {
                System.out.println("Estado Normal (Taxa : " + String.format("%.2f", taxaOcupacao) + " %)");
            }
            dataAtual.avancarUmDia();
        }
        double percentagemDiasPressao = ((double) diasEmPressao / totalDias) * 100;
        System.out.println("\nPercenatgem de dias em pressão: " + String.format("%.2f", percentagemDiasPressao) + " %");
    }

    private static void mostrarListagens(Hospital hospital, Scanner scanner) {
        System.out.println("\n1. Listar Enfermarias por taxa de ocupação");
        System.out.println("\n2. Listar Episódios de uma Enfermaria por data de admissão");
        System.out.println("Escolha a opção: ");
        int subopcao = lerOpcao(scanner, LIMITE_INF_OPCAO, LIMITE_SUP_SUBOPCAO);
        switch (subopcao) {
            case 1:
                System.out.println("Introduza a data de referência (AAAA/MM/DD): ");
                String dataReferenciaStr = scanner.nextLine();
                Data dataReferencia = Data.parseData(dataReferenciaStr);

                List<Enfermaria> ordenadas = hospital.listarEnfermariasOrdenadasPorOcupacao(dataReferencia);
                System.out.println("\n--- Enfermarias Ordenadas (Ocupação Decrescente) ---");
                for (Enfermaria enfermaria : ordenadas) {
                    double taxa = enfermaria.calcularTaxaOcupacao(dataReferencia);
                    System.out.println(enfermaria.toString());
                    String estadoEnfermaria;
                    if (enfermaria.isEmPressao(dataReferencia)) {
                        estadoEnfermaria = "Em pressão";
                    } else {
                        estadoEnfermaria = "Normal";
                    }

                    System.out.printf("Ocupação: %d/%d camas | Taxa: %.2f%% | Estado: %s\n", enfermaria.calcularOcupacao(dataReferencia), enfermaria.getNumCamas(), taxa, estadoEnfermaria);
                    System.out.println("-");
                }
                break;
            case 2:
                System.out.println("Introduza o ID da Enfermaria: ");
                Enfermaria enfermaria = hospital.procurarEnfermaria(scanner.nextLine());
                if (enfermaria != null) {
                    List<Episodio> episodios = new ArrayList<>(enfermaria.getEpisodios());
                    episodios.sort(new Comparator<Episodio>() {
                        @Override
                        public int compare(Episodio e1, Episodio e2) {
                            return e1.getDataAdmissao().compareTo(e2.getDataAdmissao());
                        }
                    });
                    System.out.println("\n--- Episódios Ordenados (Admissão) ---");
                    for (Episodio ep : episodios) {
                        System.out.println(ep.toString());
                    }
                } else {
                    System.out.println("Erro: Enfermaria não encontrada!");
                }
                break;
        }
    }


//MÉTODOS AUXILIARES

    public static int lerOpcao(Scanner scanner, int limiteInf, int limiteSup) {
        int opcao = Integer.parseInt(scanner.nextLine());
        while (opcao < limiteInf || opcao > limiteSup) {
            System.out.println("Opção inválida. Tente novamente.");
            opcao = Integer.parseInt(scanner.nextLine());
        }
        return opcao;
    }

}



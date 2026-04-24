import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class  Main {
    public static void main(String[] args) throws FileNotFoundException {
        Hospital hospital = new Hospital ("Hospital S.João");
        Scanner scanner = new Scanner (System.in);
        int opcao = 0;
        while(opcao!=6) {
            System.out.println("===MENU===");
            System.out.println("1. Criar dados automaticamente");
            System.out.println("2. Carregar dados do ficheiro");
            System.out.println("3. Mostrar cálculo de indicadores de ocupação");
            System.out.println("4. Mostrar estado e indicadores de pressão");
            System.out.println("5. Apresentar listagens ordenadas");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            if (opcao == 1) {
                // Criar enfermarias
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

            } else if (opcao == 2) {
                // Carregar episódios e enfermarias de ficheiros csv
                hospital.carregarEnfermarias("enfermarias.csv");
                hospital.carregarEpisodios("episodios.csv");

                // Mostrar erros registados no log
                if (!hospital.getRegistoErros().isEmpty()) {
                    System.out.println("\n--- Erros encontrados no carregamento ---");
                    for (String erro : hospital.getRegistoErros()) {
                        System.out.println("[LOG] " + erro);
                    }
                }


                Enfermaria eg1 = hospital.procurarEnfermaria("eg1");
                Enfermaria ep1 = hospital.procurarEnfermaria("ep1");
                Enfermaria ec1 = hospital.procurarEnfermaria("eci1");

                if (eg1 == null || ep1 == null || ec1 == null) {
                    System.out.println("Erro: enfermarias não encontradas. Verifica o ficheiro CSV.");
                    return;
                }

            }else if (opcao == 3) {
                //Indicadores de ocupação
                //Pedir ao utilizador para introduzir a enfermaria

                System.out.print("Introduza o ID da enfermaria: ");
                String idEnfermaria = scanner.nextLine();

                Enfermaria enfermaria = hospital.procurarEnfermaria(idEnfermaria);

                if (enfermaria == null) {
                    System.out.println("Enfermaria não encontrada.");
                } else {
                    System.out.println("Enfermaria encontrada: " + enfermaria.getIdEnfermaria());
                }
                System.out.println(enfermaria.toString());

                //Pedir ao utilizador para introduzir a data de referência
                System.out.println("INDICADORES DE OCUPAÇÃO");
                System.out.println("Introduza a data de referência: ");
                String dataReferenciaStr = scanner.nextLine();
                Data dataReferencia= converterTextoData(dataReferenciaStr);


                int ocupacao = enfermaria.calcularOcupacao(dataReferencia);
                double taxaOcupacao = enfermaria.calcularTaxaOcupacao(dataReferencia);
                boolean emPressao = enfermaria.EmPressao(dataReferencia, taxaOcupacao);

                System.out.println("Ocupação: " + ocupacao);
                System.out.println("Taxa de Ocupação: " + taxaOcupacao);
                if (emPressao) {
                    System.out.println("Em Pressão");
                }else{
                    System.out.println("Estado Normal");
                }

                System.out.println("MEDIDAS DE SUMÁRIO LoS");
                double mediaLoS = enfermaria.calcularMediaLoS();
                double desvioPadraoLoS = enfermaria.calcularDesvioPadraoLos();
                int minLoS = enfermaria.calcularMinLoS();
                int maxLoS = enfermaria.calcularMaxLoS();

                System.out.println("Media LoS: " + mediaLoS);
                System.out.println("Desvio Padrão LoS: " + desvioPadraoLoS);
                System.out.println("Mínimo LoS: " + minLoS);
                System.out.println("Máximo LoS: " + maxLoS);


            } else if(opcao == 4) {
                //Pedir ao utilizador para introduzir a enfermaria

                System.out.print("Introduza o ID da enfermaria: ");
                String idEnfermaria = scanner.nextLine();

                Enfermaria enfermaria = hospital.procurarEnfermaria(idEnfermaria);

                if (enfermaria == null) {
                    System.out.println("Enfermaria não encontrada.");
                } else {
                    System.out.println("Enfermaria encontrada: " + enfermaria.getIdEnfermaria());
                }
                //Pedir ao utilizador para introduzir as datas
                System.out.println("Introduza a data de ínicio: ");
                String dataInicioStr = scanner.nextLine();
                System.out.println("Introduza data de fim: ");
                String dataFimStr = scanner.nextLine();
                Data dataInicio= converterTextoData(dataInicioStr);
                Data dataFim = converterTextoData(dataFimStr);

                if(!dataFim.isMaior(dataInicio) && !dataFim.equals(dataInicio)) {
                    System.out.println("Erro: a data de fim tem de ser posterior à data de início.");
                    return;
                }

                int totalDias = dataFim.calcularDiferenca(dataInicio) + 1;

                Data dataAtual = dataInicio;
                int diasEmPressao = 0;
                for(int i = 0; i < totalDias; i++){
                    double taxaOcupacao = enfermaria.calcularTaxaOcupacao(dataAtual);
                    boolean emPressao = enfermaria.EmPressao(dataAtual, taxaOcupacao);
                    if(emPressao){
                        diasEmPressao++;
                        System.out.println("Em Pressão");
                    }else {
                        System.out.println("Estado Normal");
                        dataAtual.avancarUmDia();
                    }
                    double percentagemDiasPressao = enfermaria.calcularPercentagemDiasEmPressao(dataInicio, dataFim);

                    System.out.println(enfermaria.toString());




                }

            }else if(opcao == 5) {
                System.out.println("Enfermarias ordenadas por ordem decrescente de taxa de ocupação ");
                System.out.println("Introduza a data de referência: ");
                String dataReferenciaStr = scanner.nextLine();
                Data dataReferencia= converterTextoData(dataReferenciaStr);

                List<Enfermaria> ordenadas = hospital.listarEnfermariasOrdenadasPorOcupacao(dataReferencia);
                for(Enfermaria enfermaria : ordenadas){
                    System.out.println(enfermaria.toString());
                    System.out.println("Ocupação: " + enfermaria.calcularOcupacao(dataReferencia) + " / " + enfermaria.getNumCamas() + " camas");
                    System.out.printf("Taxa de ocupação: " +  enfermaria.calcularTaxaOcupacao(dataReferencia));
                    System.out.println("Estado: " + (enfermaria.EmPressao(dataReferencia, enfermaria.calcularTaxaOcupacao(dataReferencia)) ? "Em pressão" : "Estado normal"));
                }


            }else if(opcao != 6) {
                System.out.println("Opção Inválida! Tente novamente");

            }else if(opcao == 6){
                scanner.close();
            }

        }




//
        //Medidas de sumário do LoS??? é suposto por?

        // Estado num intervalo de datas







    }
    public static Data converterTextoData(String data){

        String[] partes = data.split("/");
        int ano  = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia =  Integer.parseInt(partes[2]);
        return new Data(ano,mes,dia);

    }



}

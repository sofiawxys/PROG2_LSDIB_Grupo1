import java.io.FileNotFoundException;
import java.util.Scanner;

public class  Main {
    public static void main(String[] args) throws FileNotFoundException {
        Hospital hospital = new Hospital ("Hospital S.João");

//        EnfermariaGeral eg1 = new EnfermariaGeral("E001", 4, 2);
//        eg1.adicionarRecurso("Cadeira de rodas");
//        eg1.adicionarRecurso("Ventilador");
//
//        EnfermariaGeral eg2 = new EnfermariaGeral("E002", 6, 1);
//        eg2.adicionarRecurso("Monitor cardíaco");
//
//        EnfermariaPsiquiatrica ep1 = new EnfermariaPsiquiatrica("E101", 5, "9:00-11:00", "Alto");
//
//        EnfermariaCuidadosIntensivos ec1 = new EnfermariaCuidadosIntensivos("E201", 9, "15:00-19:00", 730, 760);

        // Carregar episódios e enfermarias de ficheiros csv
        hospital.carregarEpisodios("episodios.csv");
        hospital.carregarEnfermarias("enfermarias.csv");

        // Mostrar erros registados no log
        if (!hospital.getRegistoErros().isEmpty()) {
            System.out.println("\n--- Erros encontrados no carregamento ---");
            for (String erro : hospital.getRegistoErros()) {
                System.out.println("[LOG] " + erro);
            }
        }
        Data dataRef = new Data(2025, 3, 20);

        Enfermaria eg1 = hospital.procurarEnfermaria("E001");
        Enfermaria ep1 = hospital.procurarEnfermaria("E101");
        Enfermaria ec1 = hospital.procurarEnfermaria("E201");

        if (eg1 == null || ep1 == null || ec1 == null) {
            System.out.println("Erro: enfermarias não encontradas. Verifica o ficheiro CSV.");
            return;
        }

        //Indicadores de ocupação
        //Pedir ao utilizador para introduzir a enfermaria
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduza o ID da enfermaria: ");
        String idEnfermaria = scanner.nextLine();

        Enfermaria enfermaria = hospital.procurarEnfermaria(idEnfermaria);

        if (enfermaria == null) {
            System.out.println("Enfermaria não encontrada.");
        } else {
            System.out.println("Enfermaria encontrada: " + enfermaria.getIdEnfermaria());
        }


        // Estado num intervalo de datas






    }
}

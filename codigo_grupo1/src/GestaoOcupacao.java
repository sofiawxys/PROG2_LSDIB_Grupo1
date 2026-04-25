public interface GestaoOcupacao {
    /* Interface que define as operações obrigatórias para qualquer entidade que faça gestão de camas e ocupação hospitalar
    Neste momento apenas temos enfermarias, mas será uma interface útil para a segunda iteração
     Caso começemos também a lidar com centros de saúde ou clínicas, por exemplo
     */

    int calcularOcupacao(Data dataReferencia);
    double calcularTaxaOcupacao(Data dataReferencia);
    boolean isEmPressao(Data dataReferencia);
}

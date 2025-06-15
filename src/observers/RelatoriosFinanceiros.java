/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observers;

/**
 *
 * @author marcos_miller
 */

import models.Pagamento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é um "Observer" concreto. Ela "implementa" o contrato ObservadorPagamento.
 * Sua responsabilidade é registrar pagamentos finalizados para futuros relatórios financeiros.
 * Pense nela como o "Contador Chefe" que anota todas as transações de pagamento.
 */
public class RelatoriosFinanceiros implements ObservadorPagamento {

    // Uma lista interna para simular onde os dados do relatório seriam armazenados.
    // Em um sistema real, isso seria salvo em um banco de dados, um arquivo, ou enviado para outro serviço.
    private List<String> transacoesRegistradas;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Construtor da classe RelatoriosFinanceiros.
     * Inicializa a lista que armazenará as transações para o relatório.
     */
    public RelatoriosFinanceiros() {
        this.transacoesRegistradas = new ArrayList<>();
        System.out.println("RelatoriosFinanceiros: Iniciado para registrar transações.");
    }

    /**
     * Este método é chamado automaticamente pelo objeto Pagamento quando ele é finalizado.
     * É aqui que a lógica de registro para o relatório financeiro é executada,
     * em resposta à "notícia" da finalização do pagamento.
     *
     * @param pagamento O Pagamento que acabou de ser finalizado.
     */
    @Override
    public void notificarAssinantes(Pagamento pagamento) { // Implementação do método da interface
        System.out.println("\n--- RelatoriosFinanceiros: Pagamento " + pagamento.getId() + " detectado ---");

        // Constrói uma linha de registro com os detalhes do pagamento.
        // Pega as informações do objeto 'pagamento' que foi passado.
        String registro = String.format(
            "[%s] Pagamento ID: %d, Valor: R$ %.2f, Tipo: %s, OS: %s",
            LocalDateTime.now().format(formatter), // Pega a data/hora AGORA para o registro no relatório
            pagamento.getId(),
            pagamento.getValor(),
            pagamento.getTipo(),
            // Verifica se tem OrdemDeServiço associada e pega o código dela, senão mostra "N/A"
            pagamento.getOrdemServico() != null ? pagamento.getOrdemServico().getCodigo() : "N/A"
        );
        transacoesRegistradas.add(registro); // Adiciona o registro à lista interna
        System.out.println("RelatoriosFinanceiros: Transação registrada: " + registro);

        // Imagine que aqui poderia ter código para:
        // - Salvar esta transação em um banco de dados específico para relatórios.
        // - Exportar para um arquivo CSV financeiro.
        // - Enviar os dados para um sistema de contabilidade externo.
    }

    /**
     * Método para exibir o relatório financeiro parcial registrado até o momento.
     * Útil para fins de demonstração (DEBUG).
     */
    public void exibirRelatorioParcial() { // Mantido para fins de demonstração
        System.out.println("\n--- Relatório Financeiro Parcial ---");
        if (transacoesRegistradas.isEmpty()) {
            System.out.println("Nenhuma transação registrada ainda.");
        } else {
            for (String transacao : transacoesRegistradas) {
                System.out.println(transacao);
            }
        }
        System.out.println("------------------------------------");
    }
    
    // O método 'gerarRelatorioFinanceiro()' da sua UML pode ser implementado aqui
    // para, por exemplo, consolidar as transações registradas e gerar um relatório final.
    // Exemplo:
    public void gerarRelatorioFinanceiro() {
        System.out.println("\n--- Gerando Relatório Financeiro Final ---");
        // Lógica para consolidar os dados da transacoesRegistradas
        // e gerar um relatório formatado, por exemplo, imprimindo no console ou salvando em um arquivo.
        if (transacoesRegistradas.isEmpty()) {
            System.out.println("Nenhuma transação para gerar relatório.");
            return;
        }
        double totalGeral = 0.0;
        System.out.println("Relatório de Todas as Transações:");
        for (String transacao : transacoesRegistradas) {
            System.out.println(transacao);
            // Simplesmente para o exemplo, tente extrair o valor e somar
            try {
                String valorStr = transacao.split("Valor: R\\$ ")[1].split(",")[0];
                totalGeral += Double.parseDouble(valorStr);
            } catch (Exception e) {
                // Ignorar erros de parsing para o exemplo
            }
        }
        System.out.println("Total Geral Registrado: R$ " + String.format("%.2f", totalGeral));
        System.out.println("--------------------------------------");
    }
}

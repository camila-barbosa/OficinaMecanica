/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica; // Mantenha o seu pacote original 'oficinamecanica'

import models.*; // Importa todas as classes de modelo
import models.enums.TipoPagamento; // Importa o enum de tipos de pagamento
import observers.RelatoriosFinanceiros; // Importa a classe do Observer concreto

import java.util.ArrayList; // Para listas, se necessário
import java.util.Date;     // Para Date em OrdemDeServico, se ainda usar (considere migrar para LocalDateTime)
import java.util.List;     // Para List


/**
 * Essa será nossa classe Main para testar o Padrão Observer.
 *
 * @author Camila (mantenha seu nome)
 */
public class Principal {

    public static void main(String[] args) {

        System.out.println("--- Iniciando o Teste do Padrão Observer ---");

        // --- 1. Configuração Inicial: Mock de Objetos ---
        // Precisamos de instâncias das classes que Pagamento depende
        // (OrdemDeServico, Cliente, Veiculo, Mecanico, Servico, StatusOrdem, TipoPagamento, etc.)
        // Para simplificar, vamos criar instâncias mínimas necessárias,
        // ajustando os construtores para o que você já tem.

        // Mocks de dependências para OrdemDeServico
        // Certifique-se que o construtor de Cliente, Veiculo, Mecanico e Servico estejam corretos
        Cliente clienteTeste = new Cliente("Maria Silva", "99988877766", "maria@email.com");
        Veiculo veiculoTeste = new Veiculo("XYZ9876", "Fusca", "Azul", clienteTeste, StatusOrdem.EM_DIAGNOSTICO);
        Mecanico mecanicoTeste = new Mecanico("Dr. Carro", "11122233344", "Rua Principal", "dr.carro@oficina.com", "99998888", "senha123", "Geral", true);
        
        // Exemplo de StatusOrdem (enum que você já tem)
        StatusOrdem statusOSInicial = StatusOrdem.AGUARDANDO_PAGAMENTO;

        // Criar uma lista de serviços (mesmo que vazia para este teste de pagamento)
        List<Servico> servicosOS = new ArrayList<>();
        // Adicionar um serviço de exemplo se o calcularTotal ou outra lógica da OS precisar
        // Ex: Servico("S001", "Troca de Pneu", 150.00, "Manutenção", new ItemEstoque("PneuX", "Pneu", 1, 100.00));
        
        // Criar uma instância de OrdemDeServico para associar ao Pagamento
        // Usando o construtor da sua OrdemDeServico
        OrdemServico osTeste = new OrdemServico(
            "OS-ABC-2025", new Date(), 0.0, veiculoTeste, clienteTeste, mecanicoTeste, statusOSInicial, servicosOS
        );
        System.out.println("\nOrdem de Serviço criada para teste: " + osTeste.getCodigo());


        // --- 2. Implementação do Padrão Observer ---

        // A. Criar o Subject (Pagamento)
        // O Pagamento será o nosso "Serviço de Assinatura Financeira"
        System.out.println("\n--- Cenário 1: Testando um Pagamento com Assinante ---");
        Pagamento pagamento1 = new Pagamento(osTeste); // Pagamento associado à OS
        
        // B. Criar o Observer (RelatoriosFinanceiros)
        // O RelatoriosFinanceiros será o nosso "Contador Chefe"
        RelatoriosFinanceiros relatoriosFinanceiros = new RelatoriosFinanceiros();

        // C. Registrar o Observer no Subject (O Contador Chefe assina o Serviço de Pagamento)
        pagamento1.adicionarObservador(relatoriosFinanceiros);


        // --- 3. Testando o Fluxo de Notificação ---

        // Teste 1: Finalizar Pagamento 1 (Deverá notificar o RelatoriosFinanceiros)
        System.out.println("\n--- Ação: Finalizando Pagamento 1 ---");
        pagamento1.finalizar(150.75, TipoPagamento.CARTAO_CREDITO);
        pagamento1.exibirDetalhes();

        // Verificar se o RelatoriosFinanceiros registrou (O Contador Chefe deve ter anotado!)
        relatoriosFinanceiros.exibirRelatorioParcial();
        relatoriosFinanceiros.gerarRelatorioFinanceiro(); // Chama o método de geração de relatório final


        // Teste 2: Criar outro Pagamento e registrar o mesmo Assinante
        System.out.println("\n--- Cenário 2: Testando outro Pagamento com o MESMO Assinante ---");
        Pagamento pagamento2 = new Pagamento(osTeste);
        // Podemos adicionar o mesmo observador a múltiplos subjects
        pagamento2.adicionarObservador(relatoriosFinanceiros); // O Contador Chefe assina este novo Pagamento

        System.out.println("\n--- Ação: Finalizando Pagamento 2 ---");
        pagamento2.finalizar(300.00, TipoPagamento.PIX);
        pagamento2.exibirDetalhes();

        // Verificar o relatório novamente para ver se ambos foram registrados
        relatoriosFinanceiros.exibirRelatorioParcial();


        // Teste 3: Finalizar Pagamento sem um Assinante registrado
        System.out.println("\n--- Cenário 3: Testando Pagamento SEM Assinante ---");
        Pagamento pagamento3 = new Pagamento(osTeste); // Este Pagamento não terá o Contador Chefe como assinante

        System.out.println("\n--- Ação: Finalizando Pagamento 3 (sem assinante) ---");
        pagamento3.finalizar(50.50, TipoPagamento.DINHEIRO);
        pagamento3.exibirDetalhes();

        // O relatório parcial NÃO deve mostrar o Pagamento 3, pois o RelatoriosFinanceiros não foi registrado nele
        System.out.println("\nVerificando relatório financeiro (Pagamento 3 NÃO deve aparecer):");
        relatoriosFinanceiros.exibirRelatorioParcial();


        // Teste 4: Remover um Assinante e testar novamente
        System.out.println("\n--- Cenário 4: Removendo Assinante do Pagamento 1 ---");
        pagamento1.removerObservador(relatoriosFinanceiros);

        System.out.println("\n--- Ação: Finalizando Pagamento 1 NOVAMENTE (NÃO deve ser registrado AGORA) ---");
        pagamento1.finalizar(10.00, TipoPagamento.CARTAO_DEBITO); // Valor fictício, apenas para testar que não notifica mais
        pagamento1.exibirDetalhes();

        System.out.println("\nVerificando relatório financeiro (Nenhum novo registro do Pagamento 1 com 10.00):");
        relatoriosFinanceiros.exibirRelatorioParcial(); // Nenhuma nova entrada do Pagamento 1 com valor 10.00
        
        System.out.println("\n--- Teste do Padrão Observer Finalizado ---");
    }
}
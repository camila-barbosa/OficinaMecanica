/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica; 

import models.*; 
import models.enums.TipoPagamento;
import observers.RelatoriosFinanceiros; 

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Essa será nossa classe Main para testar o Padrão Observer.
 * O objetivo é demonstrar o fluxo de notificação do Pagamento para RelatoriosFinanceiros.
 *
 * @author barbo
 */
public class Principal {

    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("          INÍCIO DO TESTE: PADRÃO OBSERVER          ");
        System.out.println("=====================================================");

        // --- 1. CRIAÇÃO DE DADOS MOCADOS PARA O TESTE ---

        // Criando instâncias
        Cliente clienteTeste = new Cliente("Ana Costa", "11987654321", "ana.costa@email.com");
        Veiculo veiculoTeste = new Veiculo("ABC1D23", "Fiat Argo", "Preto", clienteTeste, null); 
        Mecanico mecanicoTeste = new Mecanico("Pedro Mecanico", "12345678901", "Rua das Oficinas, 10", "pedro@oficina.com", "987654321", "senhaMec", "Motor", true );
        StatusOrdem statusOSInicial = StatusOrdem.AGUARDANDO_PAGAMENTO;
        List<Servico> servicosOS = new ArrayList<>();

        // Criando uma Ordem de Serviço para associar ao Pagamento
        OrdemServico osTeste = new OrdemServico(
            "OS-TESTE-001", new Date(), 0.0, veiculoTeste, clienteTeste, mecanicoTeste, statusOSInicial, servicosOS
        );
        System.out.println("\n[SETUP] Ordem de Serviço criada: " + osTeste.getCodigo());
        
        System.out.println("-----------------------------------------------------");


        // --- 2. DEMONSTRAÇÃO DO PADRÃO OBSERVER ---

        // A. Criação do Publicador (Subject): A instância de Pagamento
       
        System.out.println("\n[PASSO 1] Criando o Publicador (Pagamento)...");
        Pagamento pagamentoPrincipal = new Pagamento(osTeste); // O Pagamento é associado à OS
        
        // B. Criação do Assinante (Observer): A instância de RelatoriosFinanceiros
        
        System.out.println("[PASSO 2] Criando o Assinante (RelatoriosFinanceiros)...");
        RelatoriosFinanceiros relatoriosFinanceiros = new RelatoriosFinanceiros();

        // C. Registro do Assinante no Publicador: O Contador Chefe assina o Serviço de Pagamento
        
        System.out.println("[PASSO 3] Assinante RelatoriosFinanceiros se registrando no Pagamento " + pagamentoPrincipal.getId() + "...");
        pagamentoPrincipal.adicionarObservador(relatoriosFinanceiros);
        
        System.out.println("-----------------------------------------------------");


        // --- 3. CENÁRIOS DE TESTE ---

        // CENÁRIO 1:
        System.out.println("\n--- CENÁRIO 1: Pagamento Finalizado com Sucesso ---");
        // O método finalizar() do Pagamento é chamado, alterando seu estado
        System.out.println("[AÇÃO] Finalizando o Pagamento " + pagamentoPrincipal.getId() + "...");
        pagamentoPrincipal.finalizar(250.75, TipoPagamento.CARTAO_CREDITO);
        // Exibe os detalhes do Pagamento para confirmar o estado final
        System.out.println("\n[VERIFICAÇÃO] Detalhes do Pagamento após finalização:");
        pagamentoPrincipal.exibirDetalhes();
        // Exibe o relatório parcial para mostrar que a transação foi registrada
        System.out.println("\n[RESULTADO] Verificando o relatório financeiro parcial:");
        relatoriosFinanceiros.exibirRelatorioParcial();
        // Gera o relatório final para consolidação (se a lógica for mais complexa)
        relatoriosFinanceiros.gerarRelatorioFinanceiro();
        
        System.out.println("-----------------------------------------------------");


        // CENÁRIO 2: O Pagamento é Finalizado, mas SEM Assinante Registrado
        System.out.println("\n--- CENÁRIO 2: Pagamento Finalizado SEM Assinante ---");
        // Criando um novo Pagamento que não terá o RelatoriosFinanceiros como assinante
        Pagamento pagamentoSemAssinante = new Pagamento(osTeste);
        System.out.println("[SETUP] Novo Pagamento " + pagamentoSemAssinante.getId() + " criado (sem assinante registrado).");

        // Finaliza o Pagamento. O RelatoriosFinanceiros NÃO deve ser notificado.
        System.out.println("[AÇÃO] Finalizando Pagamento " + pagamentoSemAssinante.getId() + "...");
        pagamentoSemAssinante.finalizar(75.00, TipoPagamento.DINHEIRO);
        System.out.println("\n[VERIFICAÇÃO] Detalhes do Pagamento " + pagamentoSemAssinante.getId() + " (não deve aparecer no relatório):");
        pagamentoSemAssinante.exibirDetalhes();
        
        // Verifica o relatório. O novo pagamento NÃO deve aparecer.
        System.out.println("\n[RESULTADO] Verificando o relatório financeiro parcial (Pagamento " + pagamentoSemAssinante.getId() + " NÃO deve aparecer):");
        relatoriosFinanceiros.exibirRelatorioParcial();
        
        System.out.println("-----------------------------------------------------");


        // CENÁRIO 3: Assinante é Removido e o Pagamento é Finalizado Novamente
        System.out.println("\n--- CENÁRIO 3: Assinante Removido ---");
        // Remove o RelatoriosFinanceiros do pagamentoPrincipal
        System.out.println("[AÇÃO] Removendo RelatoriosFinanceiros do Pagamento " + pagamentoPrincipal.getId() + "...");
        pagamentoPrincipal.removerObservador(relatoriosFinanceiros);

        // Finaliza o Pagamento 1 novamente. O RelatoriosFinanceiros NÃO deve ser notificado.
        System.out.println("[AÇÃO] Finalizando Pagamento " + pagamentoPrincipal.getId() + " NOVAMENTE (não deve notificar):");
        pagamentoPrincipal.finalizar(10.00, TipoPagamento.CARTAO_DEBITO);
        
        // Verifica o relatório. Nenhuma nova entrada para este pagamento deve aparecer.
        System.out.println("\n[RESULTADO] Verificando o relatório financeiro parcial (nenhuma nova entrada para Pagamento " + pagamentoPrincipal.getId() + " deve aparecer):");
        relatoriosFinanceiros.exibirRelatorioParcial();
        
        System.out.println("=====================================================");
        System.out.println("          FIM DO TESTE: PADRÃO OBSERVER            ");
        System.out.println("=====================================================");
    }
}
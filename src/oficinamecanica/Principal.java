/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica; 

import models.*; 
import models.enums.TipoPagamento; // Pode ser removido se não for usar Pagamento/Financeiro no Main
import observers.IObservavelOrdemServico; // Importa a interface Subject

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import view.PainelMecanico;
import observers.IObservadorOrdemServico;


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
        System.out.println("          (OrdemServico -> PainelMecanico)         ");
        System.out.println("=====================================================");

        // --- 1. CRIAÇÃO DE DADOS MOCADOS (FICTÍCIOS) PARA O TESTE ---

        // Criando instâncias mínimas para as dependências da OrdemServico
        // CONFIRA SE OS CONSTRUTORES ESTÃO ALINHADOS COM SUAS CLASSES REAIS!
        Cliente clienteTeste = new Cliente("Ana Costa", "11987654321", "ana.costa@email.com");
        Veiculo veiculoTeste = new Veiculo("DEF5678", "Ford Ka", "Vermelho", clienteTeste, null); 
        // Mecanico e Atendente são necessários para criar a OrdemServico, mas não serão passados na notificação
        Mecanico mecanicoChefe = new Mecanico("Carlos Chefe", "11122233344", "Rua A", "carlos@oficina.com", "987654321", "senha123", "Geral", true); 
        
        // Crie o objeto da OS que será o Publicador
        OrdemServico osCarroA = new OrdemServico(
            "OS-001-A", new Date(), 0.0, veiculoTeste, clienteTeste, mecanicoChefe, StatusOrdem.EM_DIAGNOSTICO, new ArrayList<Servico>()
        );
        System.out.println("\n[SETUP] Ordem de Serviço Publicador criada: " + osCarroA.getCodigo() + " (Status Inicial: " + osCarroA.getStatus() + ")");
        
        System.out.println("-----------------------------------------------------");


        // --- 2. DEMONSTRAÇÃO DO PADRÃO OBSERVER ---

        // A. Criação do Assinante (Observer): A instância do PainelMecanico
        System.out.println("\n[PASSO 1] Criando o Assinante (PainelMecanico)...");
        PainelMecanico painelMecanico = new PainelMecanico();

        // B. Registro do Assinante no Publicador: O PainelMecanico assina a OrdemServico
        System.out.println("[PASSO 2] PainelMecanico se registrando na OrdemServico " + osCarroA.getCodigo() + "...");
        osCarroA.adicionarObservador(painelMecanico); // O PainelMecanico agora observará as mudanças de status da OS
        
        System.out.println("-----------------------------------------------------");


        // --- 3. CENÁRIOS DE TESTE: ALTERAÇÕES DE STATUS DA OS ---

        // CENÁRIO 1: Alteração para status relevante para o Mecânico (EM_DIAGNÓSTICO)
        System.out.println("\n--- CENÁRIO 1: Status para 'EM_DIAGNÓSTICO' (relevante para mecânico) ---");
        // O método alterarStatus agora SÓ recebe o novoStatus
        System.out.println("[AÇÃO] Alterando status da OS " + osCarroA.getCodigo() + " para EM_DIAGNOSTICO...");
        osCarroA.alterarStatus(StatusOrdem.EM_DIAGNOSTICO); // REMOVIDO o parâmetro Usuario para simplicidade
        
        System.out.println("-----------------------------------------------------");

        // CENÁRIO 2: Outra alteração para status relevante (EM_EXECUÇÃO)
        System.out.println("\n--- CENÁRIO 2: Status para 'EM_EXECUÇÃO' (relevante para mecânico) ---");
        System.out.println("[AÇÃO] Alterando status da OS " + osCarroA.getCodigo() + " para EM_EXECUCAO...");
        osCarroA.alterarStatus(StatusOrdem.EM_EXECUCAO); // REMOVIDO o parâmetro Usuario
        
        System.out.println("-----------------------------------------------------");

        // CENÁRIO 3: Alteração para status NÃO relevante para o Mecânico (AGUARDANDO_PAGAMENTO)
        System.out.println("\n--- CENÁRIO 3: Status para 'AGUARDANDO_PAGAMENTO' (NÃO relevante para mecânico) ---");
        System.out.println("[AÇÃO] Alterando status da OS " + osCarroA.getCodigo() + " para AGUARDANDO_PAGAMENTO...");
        osCarroA.alterarStatus(StatusOrdem.AGUARDANDO_PAGAMENTO); // REMOVIDO o parâmetro Usuario
        
        System.out.println("-----------------------------------------------------");

        // CENÁRIO 4: Assinante é Removido e o Status é Alterado Novamente
        System.out.println("\n--- CENÁRIO 4: Removendo Assinante e Testando Novamente ---");
        System.out.println("[AÇÃO] Removendo PainelMecanico como assinante da OS " + osCarroA.getCodigo() + "...");
        osCarroA.removerObservador(painelMecanico);

        System.out.println("[AÇÃO] Alterando status da OS " + osCarroA.getCodigo() + " para FINALIZADA (PainelMecanico NÃO deve reagir)...");
        osCarroA.alterarStatus(StatusOrdem.FINALIZADA); // REMOVIDO o parâmetro Usuario
        // O PainelMecanico NÃO deve exibir mensagens de notificação para esta última alteração.
        
        System.out.println("=====================================================");
        System.out.println("          FIM DO TESTE: PADRÃO OBSERVER            ");
        System.out.println("=====================================================");
    }
}
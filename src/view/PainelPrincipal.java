/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.Scanner;
import models.Usuario;
import repository.UsuarioCRUD;
import service.RegistroPontoService;
import util.UserSession;
import view.componentes.ComponentePonto;

/**
 *
 * @author marcos_miller
 */
public class PainelPrincipal {

    private Usuario usuarioLogado; // O usuário que está logado, obtido da UserSession
    private UsuarioCRUD usuarioCRUD; // Dependência para gerenciar usuários (se o painel precisar)
    private RegistroPontoService pontoService; // Dependência para as operações de ponto
    private Scanner scanner; // Scanner compartilhado para entrada do usuário

    // Componentes que serão exibidos no painel
    private ComponentePonto componentePonto;
    // Futuramente: ComponenteOrdemServicoEspecializada componenteOSEspecializada;

    /**
     * Construtor do PainelPrincipal.
     * Recebe as dependências necessárias para suas operações.
     * @param usuarioCRUD O CRUD de usuários.
     * @param pontoService O serviço de negócio para o registro de ponto.
     * @param scanner O scanner para entrada do usuário.
     */
    public PainelPrincipal(UsuarioCRUD usuarioCRUD, RegistroPontoService pontoService, Scanner scanner) {
        this.usuarioCRUD = usuarioCRUD;
        this.pontoService = pontoService;
        this.scanner = scanner;
        
        // Recupera o usuário logado da sessão, que já foi definido no Main após o login
        this.usuarioLogado = UserSession.getInstance().getLoggedInUser();
        
        // Inicializa os componentes visuais
        this.componentePonto = new ComponentePonto(pontoService, scanner);
        // Futuramente: this.componenteOSEspecializada = new ComponenteOrdemServicoEspecializada(osService, scanner);

        // Verificação de segurança: se por algum motivo o usuário não estiver logado, encerra.
        if (this.usuarioLogado == null) {
            System.err.println("Erro: Tentativa de exibir PainelPrincipal sem usuário logado. Encerrando.");
            System.exit(1);
        }
    }

    /**
     * Inicia e exibe o loop principal do painel.
     * Gerencia a interação com o usuário através dos componentes e menus específicos.
     */
    public void exibirPainel() {
        int opcao;
        do {
            System.out.println("\n=============================================");
            System.out.println("--- PAINEL PRINCIPAL: " + usuarioLogado.getTipo().getDescricao().toUpperCase() + " ---");
            System.out.println("Bem-vindo(a), " + usuarioLogado.getNome() + "!");
            System.out.println("---------------------------------------------");

            // 1. Exibir e Processar o Componente de Ponto
            // Isso irá mostrar o status do ponto e pedir para assinar entrada/saída.
            int opcaoPonto = componentePonto.exibirStatusEPedirAcao();
            if (opcaoPonto == 8 || opcaoPonto == 9) { // Se a opção for de ponto
                componentePonto.processarAcaoPonto(opcaoPonto, usuarioLogado);
            }
            System.out.println("---------------------------------------------"); // Separador

            // 2. Exibir a Lista de O.S. Especializadas (Lógica a ser implementada futuramente)
            exibirOrdensDeServicoEspecializadas();
            System.out.println("---------------------------------------------"); // Separador

            // 3. Exibir o Menu de Opções Específico para o Tipo de Usuário
            exibirMenuOpcoesPorTipo();

            System.out.print("Escolha uma opção do menu principal ou '0' para sair: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer
                opcao = -1; // Opção inválida para repetir o loop
            }

            // 4. Processar a Opção Escolhida no Menu
            processarOpcaoMenu(opcao);

        } while (opcao != 0); // Loop continua até o usuário escolher sair (opção 0)
    }

    // --- Métodos Privados para Orquestração dos Componentes ---

    private void exibirOrdensDeServicoEspecializadas() {
        System.out.println("\n[ORDENS DE SERVIÇO ATRIBUÍDAS / EM ABERTO]");
        // Essa lógica será baseada no usuarioLogado.getTipo()
        // Por enquanto, apenas uma mensagem
        System.out.println("Nenhuma Ordem de Serviço exibida ainda (Lógica a ser implementada futuramente).");
    }

    private void exibirMenuOpcoesPorTipo() {
        System.out.println("\n[MENU DE OPÇÕES]");
        switch (usuarioLogado.getTipo()) {
            case ATENDENTE:
                System.out.println("1. Gerenciar Agendamentos");
                System.out.println("2. Gerar Nova Ordem de Serviço");
                System.out.println("3. Processar Pagamento");
                break;
            case MECANICO:
                System.out.println("1. Visualizar Minhas Ordens de Serviço");
                System.out.println("2. Registrar Diagnóstico");
                System.out.println("3. Registrar Execução de Serviço");
                break;
            case GERENTE:
                System.out.println("1. Gerenciar Usuários");
                System.out.println("2. Gerenciar Estoque");
                System.out.println("3. Acessar Relatórios Financeiros");
                break;
            default:
                System.out.println("Nenhuma opção disponível para este tipo de usuário.");
                break;
        }
        System.out.println("0. Sair do Painel");
    }

    private void processarOpcaoMenu(int opcao) {
        // Opção de saída é tratada no loop 'do-while'
        if (opcao == 0) {
            System.out.println("Saindo do Painel " + usuarioLogado.getTipo().getDescricao() + ".");
            return; // Sai do método e do loop principal
        }

        switch (usuarioLogado.getTipo()) {
            case ATENDENTE:
                processarMenuAtendente(opcao);
                break;
            case MECANICO:
                processarMenuMecanico(opcao);
                break;
            case GERENTE:
                processarMenuGerente(opcao);
                break;
            default:
                System.out.println("Opção inválida para este tipo de usuário.");
                break;
        }
    }

    private void processarMenuAtendente(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("Funcionalidade 'Gerenciar Agendamentos' (Atendente) ainda não implementada.");
                break;
            case 2:
                System.out.println("Funcionalidade 'Gerar Nova Ordem de Serviço' (Atendente) ainda não implementada.");
                break;
            case 3:
                System.out.println("Funcionalidade 'Processar Pagamento' (Atendente) ainda não implementada.");
                break;
            default:
                System.out.println("Opção inválida para Atendente.");
                break;
        }
    }

    private void processarMenuMecanico(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("Funcionalidade 'Visualizar Minhas Ordens de Serviço' (Mecânico) ainda não implementada.");
                break;
            case 2:
                System.out.println("Funcionalidade 'Registrar Diagnóstico' (Mecânico) ainda não implementada.");
                break;
            case 3:
                System.out.println("Funcionalidade 'Registrar Execução de Serviço' (Mecânico) ainda não implementada.");
                break;
            default:
                System.out.println("Opção inválida para Mecânico.");
                break;
        }
    }

    private void processarMenuGerente(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("Abrindo Gerenciamento de Usuários...");
                ComponenteMenuUsuario menuUsuarioGerente = new ComponenteMenuUsuario(usuarioCRUD, scanner);
                menuUsuarioGerente.exibirMenu(); // Entra no loop do MenuUsuario
                break;
            case 2:
                System.out.println("Funcionalidade 'Gerenciar Estoque' (Gerente) ainda não implementada.");
                break;
            case 3:
                System.out.println("Funcionalidade 'Acessar Relatórios Financeiros' (Gerente) ainda não implementada.");
                break;
            default:
                System.out.println("Opção inválida para Gerente.");
                break;
        }
    }
}
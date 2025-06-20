/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica; // Seu pacote principal, que contém a classe Main/Principal

import java.util.Scanner;
import models.Usuario;
import models.enums.TipoUsuario;
import repository.PontoRepository;
import repository.UsuarioCRUD;
import service.RegistroPontoService;
import util.AuthService;
import util.UserSession;
import view.MenuUsuario;
import view.componentes.ComponentePonto;

/**
 * Ponto de entrada principal do Sistema de Gerenciamento da Oficina.
 * Responsável por iniciar o sistema, gerenciar o fluxo de login
 * e redirecionar para os painéis de usuário apropriados.
 *
 * @author barbo
 */
public class Principal {

    // Instâncias estáticas para os repositórios e scanner, acessíveis em toda a classe
    private static UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
    private static PontoRepository pontoRepository = new PontoRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gerenciamento da Oficina...");
        AuthService authService = new AuthService(usuarioCRUD, scanner);
        
        // Tenta realizar o login do usuário
        Usuario usuarioLogado = authService.login();

        // Verifica se o login foi bem-sucedido
        if (usuarioLogado != null) {
            // Se o login for bem-sucedido, define o usuário na UserSession
            UserSession.getInstance().setLoggedInUser(usuarioLogado);
            System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNome() + "!");
            
            // Exibe o painel apropriado com base no tipo de usuário logado
            exibirPainelPorTipoUsuario(); 
        } else {
            // Se o login falhar (usuário digitou '0' para sair ou esgotou tentativas), encerra o sistema
            System.out.println("Não foi possível realizar o login. Encerrando o sistema.");
        }

        // Fecha o scanner no final da execução do programa
        scanner.close();
    }

    /**
     * Orquestra a exibição do painel ou menu apropriado com base no TipoUsuario logado na sessão.
     * Este método obtém o usuário logado da UserSession.
     */
    private static void exibirPainelPorTipoUsuario() {
        // Obtém o usuário logado diretamente da UserSession
        Usuario usuario = UserSession.getInstance().getLoggedInUser();
        
        // Verificação de segurança: caso não haja usuário logado (não deveria ocorrer após login bem-sucedido)
        if (usuario == null) {
            System.err.println("Erro: Nenhum usuário logado na sessão. Encerrando o programa.");
            System.exit(1); // Sai com código de erro
        }

        TipoUsuario tipo = usuario.getTipo(); // Pega o tipo do usuário logado

        // Instancia o serviço de ponto aqui para passá-lo ao componente de ponto nos painéis
        RegistroPontoService pontoService = new RegistroPontoService(pontoRepository);

        // Usa um switch para rotear para o painel (ou placeholder) específico de cada tipo de usuário
        switch (tipo) {
            case ATENDENTE:
                System.out.println("\n--- PAINEL DO ATENDENTE ---");
                // Integração do ComponentePonto
                ComponentePonto componentePontoAtendente = new ComponentePonto(pontoService, scanner);
                int opcaoPontoAtendente = componentePontoAtendente.exibirStatusEPedirAcao();
                if (opcaoPontoAtendente == 8 || opcaoPontoAtendente == 9) {
                    componentePontoAtendente.processarAcaoPonto(opcaoPontoAtendente, usuario);
                }
                System.out.println("---------------------------------------------"); // Separador
                
                // Exibe o menu de usuário (temporário para este painel)
                MenuUsuario menuAtendente = new MenuUsuario(usuarioCRUD, scanner); 
                menuAtendente.exibirMenu();
                break;
            case GERENTE:
                System.out.println("\n--- PAINEL DO GERENTE ---");
                // Integração do ComponentePonto (similar ao Atendente)
                ComponentePonto componentePontoGerente = new ComponentePonto(pontoService, scanner);
                int opcaoPontoGerente = componentePontoGerente.exibirStatusEPedirAcao();
                if (opcaoPontoGerente == 8 || opcaoPontoGerente == 9) {
                    componentePontoGerente.processarAcaoPonto(opcaoPontoGerente, usuario);
                }
                System.out.println("---------------------------------------------");

                System.out.println("Funcionalidade do Painel do Gerente ainda não implementada. Encerrando o programa.");
                System.exit(0); // Finaliza o programa
                break;
            case MECANICO:
                System.out.println("\n--- PAINEL DO MECÂNICO ---");
                // Integração do ComponentePonto (similar ao Atendente)
                ComponentePonto componentePontoMecanico = new ComponentePonto(pontoService, scanner);
                int opcaoPontoMecanico = componentePontoMecanico.exibirStatusEPedirAcao();
                if (opcaoPontoMecanico == 8 || opcaoPontoMecanico == 9) {
                    componentePontoMecanico.processarAcaoPonto(opcaoPontoMecanico, usuario);
                }
                System.out.println("---------------------------------------------");

                System.out.println("Funcionalidade do Painel do Mecânico ainda não implementada. Encerrando o programa.");
                System.exit(0); // Finaliza o programa
                break;
            default: // Caso seja um tipo de Usuário não previsto ou não mapeado
                System.out.println("\n--- Tipo de usuário não reconhecido ou sem painel específico. Encerrando o programa. ---");
                System.exit(0); // Finaliza o programa
                break;
        }
    }
}
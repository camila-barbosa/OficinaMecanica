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
import view.ComponenteMenuUsuario;
import view.componentes.ComponentePonto;

/**
 * Ponto de entrada principal do Sistema de Gerenciamento da Oficina.
 * Responsável por iniciar o sistema, gerenciar o fluxo de login
 * e redirecionar para os painéis de usuário apropriados.
 *
 * @author barbo
 */
public class Principal {

    private static UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
    private static PontoRepository pontoRepository = new PontoRepository();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gerenciamento da Oficina...");

        AuthService authService = new AuthService(usuarioCRUD, scanner);
        Usuario usuarioLogado = authService.login();

        if (usuarioLogado != null) {
            UserSession.getInstance().setLoggedInUser(usuarioLogado);
            exibirPainelPorTipoUsuario();
        } else {
            System.out.println("Não foi possível realizar o login. Encerrando o sistema.");
        }
        scanner.close();
    }

    /**
     * Orquestra a exibição do painel ou menu apropriado com base no TipoUsuario logado.
     * Este método agora obtém o usuário da UserSession.
     */
    private static void exibirPainelPorTipoUsuario() {
        Usuario usuario = UserSession.getInstance().getLoggedInUser();
        if (usuario == null) {
            System.out.println("Erro: Nenhum usuário logado na sessão. Encerrando.");
            System.exit(1);
        }

        TipoUsuario tipo = usuario.getTipo();

        // Instanciar o serviço de ponto aqui para passá-lo ao componente
        RegistroPontoService pontoService = new RegistroPontoService(pontoRepository);

        switch (tipo) {
            case ATENDENTE:
                System.out.println("\n--- PAINEL DO ATENDENTE ---");
                // --- INTEGRAÇÃO DO COMPONENTE PONTO AQUI ---
                ComponentePonto componentePontoAtendente = new ComponentePonto(pontoService, scanner);
                
                // Exibe o status e permite assinar o ponto
                int opcaoPonto = componentePontoAtendente.exibirStatusEPedirAcao();
                
                // Se a opção for 8 (entrada) ou 9 (saída), processa a ação
                if (opcaoPonto == 8 || opcaoPonto == 9) {
                    componentePontoAtendente.processarAcaoPonto(opcaoPonto, usuario);
                }
                System.out.println("---------------------------------------------"); // Separador após o ponto

                // Continua para o menu específico (ComponenteMenuUsuario por enquanto)
                ComponenteMenuUsuario menuAtendente = new ComponenteMenuUsuario(usuarioCRUD, scanner);
                menuAtendente.exibirMenu();
                break;
            case GERENTE:
                System.out.println("\n--- PAINEL DO GERENTE ---");
                // --- INTEGRAÇÃO DO COMPONENTE PONTO AQUI (similar ao ATENDENTE) ---
                ComponentePonto componentePontoGerente = new ComponentePonto(pontoService, scanner);
                int opcaoPontoGerente = componentePontoGerente.exibirStatusEPedirAcao();
                if (opcaoPontoGerente == 8 || opcaoPontoGerente == 9) {
                    componentePontoGerente.processarAcaoPonto(opcaoPontoGerente, usuario);
                }
                System.out.println("---------------------------------------------");
                
                System.out.println("Funcionalidade do Painel do Gerente ainda não implementada. Encerrando o programa.");
                System.exit(0);
                break;
            case MECANICO:
                System.out.println("\n--- PAINEL DO MECÂNICO ---");
                // --- INTEGRAÇÃO DO COMPONENTE PONTO AQUI (similar ao ATENDENTE) ---
                ComponentePonto componentePontoMecanico = new ComponentePonto(pontoService, scanner);
                int opcaoPontoMecanico = componentePontoMecanico.exibirStatusEPedirAcao();
                if (opcaoPontoMecanico == 8 || opcaoPontoMecanico == 9) {
                    componentePontoMecanico.processarAcaoPonto(opcaoPontoMecanico, usuario);
                }
                System.out.println("---------------------------------------------");

                System.out.println("Funcionalidade do Painel do Mecânico ainda não implementada. Encerrando o programa.");
                System.exit(0);
                break;
            default: // Caso seja um tipo de Usuário não previsto.
                System.out.println("\n--- Tipo de usuário não reconhecido ou sem painel específico. Encerrando o programa. ---");
                System.exit(0);
                break;
        }
    }
}
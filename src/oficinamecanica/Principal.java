/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica; 


import java.util.Scanner;
import models.Usuario;
import models.enums.TipoUsuario;
import repository.UsuarioCRUD;
import util.AuthService;
import util.UserSession;
import view.MenuUsuario;


/**
 * 
 * O objetivo é demonstrar o fluxo de notificação da OrdemServico para PainelMecanico.
 *
 * @author barbo
 */
public class Principal {

     private static UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gerenciamento da Oficina...");
        
        AuthService authService = new AuthService(usuarioCRUD, scanner);
        Usuario usuarioLogado = authService.login();
        // Não fechamos o scanner aqui. Ele será fechado apenas no final do main.

        if (usuarioLogado != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo, " + usuarioLogado.getNome() + "!");
            
            // --- AQUI É ONDE DEFINIMOS O USUÁRIO LOGADO NA SESSÃO ---
            UserSession.getInstance().setLoggedInUser(usuarioLogado);

            // Agora, o método exibirPainelPorTipoUsuario não precisa mais receber o Usuario como parâmetro
            exibirPainelPorTipoUsuario(); 
        } else {
            System.out.println("Não foi possível realizar o login. Encerrando o sistema.");
        }

        scanner.close(); // Fecha o scanner apenas no final do main
    }

    /**
     * Orquestra a exibição do painel ou menu apropriado com base no TipoUsuario logado na sessão.
     * Este método agora obtém o usuário da UserSession.
     */
    private static void exibirPainelPorTipoUsuario() { // Removemos o parâmetro 'Usuario usuario'
        // Obtém o usuário logado diretamente da sessão
        Usuario usuario = UserSession.getInstance().getLoggedInUser(); 

        // Adiciona uma verificação extra para garantir que o usuário não seja nulo (embora não deva acontecer aqui)
        if (usuario == null) {
            System.out.println("Erro: Usuário não encontrado na sessão. Encerrando o programa.");
            System.exit(1); // Sai com código de erro
        }

        TipoUsuario tipo = usuario.getTipo(); // Pega o tipo do usuário logado

        switch (tipo) {
            case ATENDENTE:
                System.out.println("\n--- PAINEL DO ATENDENTE ---");
                // Menus ainda recebem o CRUD e o scanner, mas não o 'Usuario' logado,
                // pois eles podem obtê-lo da UserSession se precisarem.
                MenuUsuario menuAtendente = new MenuUsuario(usuarioCRUD, scanner); 
                menuAtendente.exibirMenu();
                break;
            case GERENTE:
                System.out.println("\n--- PAINEL DO GERENTE ---");
                System.out.println("Funcionalidade do Painel do Gerente ainda não implementada. Encerrando o programa.");
                System.exit(0);
                break;
            case MECANICO:
                System.out.println("\n--- PAINEL DO MECÂNICO ---");
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
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
import view.MenuUsuario;


/**
 * 
 * O objetivo é demonstrar o fluxo de notificação da OrdemServico para PainelMecanico.
 *
 * @author barbo
 */
public class Principal {

    private static UsuarioCRUD usuarioCRUD = new UsuarioCRUD();
    // O Scanner é criado uma única vez e é estático para ser acessível em métodos estáticos
    private static Scanner scanner = new Scanner(System.in); 

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gerenciamento da Oficina...");

        // --- TESTE INICIAL: Garante que haja usuários para login na primeira execução ---
        if (usuarioCRUD.listarUsuarios().isEmpty()) {
            System.out.println("\n--- Primeira execução ou usuários não encontrados ---");
            System.out.println("Adicionando usuários padrão para teste de login. Por favor, reinicie o programa para fazer login.");
            
            // Adicionando usuários de teste com seus respectivos Tipos
            Usuario atendente = new Usuario("Atendente Joana", "11111111111", "Av. Central, 456", "joana@oficina.com", "988888888", TipoUsuario.ATENDENTE, "senhaatendente");
            usuarioCRUD.adicionarUsuario(atendente);
            System.out.println("- Usuário: 'Atendente Joana' (Tipo: " + atendente.getTipo().getDescricao() + ", Senha: senhaatendente)");

            Usuario mecanico = new Usuario("Mecanico Pedro", "22222222222", "Rua das Oficinas, 789", "pedro@oficina.com", "977777777", TipoUsuario.MECANICO, "senhamecanico");
            usuarioCRUD.adicionarUsuario(mecanico);
            System.out.println("- Usuário: 'Mecanico Pedro' (Tipo: " + mecanico.getTipo().getDescricao() + ", Senha: senhamecanico)");

            Usuario gerente = new Usuario("Gerente Carlos", "33333333333", "Praça Principal, 101", "carlos@oficina.com", "966666666", TipoUsuario.GERENTE, "senhagerente");
            usuarioCRUD.adicionarUsuario(gerente);
            System.out.println("- Usuário: 'Gerente Carlos' (Tipo: " + gerente.getTipo().getDescricao() + ", Senha: senhagerente)");
            return; 
        }
        // --- FIM DO TESTE INICIAL ---

        AuthService authService = new AuthService(usuarioCRUD, scanner); // Passa o scanner para AuthService
        Usuario usuarioLogado = authService.login();
        // Não fechamos o scanner aqui. Ele será fechado apenas no final do main.

        if (usuarioLogado != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo, " + usuarioLogado.getNome() + "!");
            // Chama o método estático sem precisar passar o scanner como parâmetro,
            // pois 'scanner' já é um atributo estático da classe Main.
            exibirPainelPorTipoUsuario(usuarioLogado); 
        } else {
            System.out.println("Não foi possível realizar o login. Encerrando o sistema.");
        }

        scanner.close(); // Fecha o scanner apenas no final do main
    }

    /**
     * Orquestra a exibição do painel ou menu apropriado com base no TipoUsuario logado.
     * Este método agora é estático.
     * @param usuario O objeto Usuario autenticado.
     */
    private static void exibirPainelPorTipoUsuario(Usuario usuario) { // Removemos o parâmetro scanner aqui
        TipoUsuario tipo = usuario.getTipo();

        switch (tipo) {
            case ATENDENTE:
                System.out.println("\n--- PAINEL DO ATENDENTE ---");
                // Menus futuros seriam instanciados aqui
                // O MenuUsuario agora também acessa o scanner diretamente do Main (se necessário)
                MenuUsuario menuAtendente = new MenuUsuario(usuarioCRUD, scanner); // Passa o scanner estático
                menuAtendente.exibirMenu();
                break;
            case GERENTE:
                System.out.println("\n--- PAINEL DO GERENTE ---");
                System.out.println("Funcionalidade do Painel do Gerente ainda não implementada. Encerrando o programa.");
                System.exit(0); // Finaliza o programa
                break;
            case MECANICO:
                System.out.println("\n--- PAINEL DO MECÂNICO ---");
                System.out.println("Funcionalidade do Painel do Mecânico ainda não implementada. Encerrando o programa.");
                System.exit(0); // Finaliza o programa
                break;
            default: // Caso seja um tipo de Usuário não previsto.
                System.out.println("\n--- Tipo de usuário não reconhecido ou sem painel específico. Encerrando o programa. ---");
                System.exit(0); // Finaliza o programa
                break;
        }
    }
}
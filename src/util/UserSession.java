/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import models.Usuario;

/**
 *
 * @author marcos_miller
 */
public class UserSession {

    // 1. A única instância da classe (privada e estática)
    // Isso garante que 'instance' seja a única referência para um objeto UserSession.
    private static UserSession instance;

    // 2. O atributo para armazenar o usuário logado
    // Será preenchido após um login bem-sucedido.
    private Usuario loggedInUser;

    /**
     * 3. Construtor privado.
     * Impede que outras classes criem instâncias de UserSession diretamente usando 'new'.
     * Isso reforça o padrão Singleton.
     */
    private UserSession() {
        // Inicializa como null, pois nenhum usuário está logado no início.
        this.loggedInUser = null;
    }

    /**
     * 4. Método estático para obter a única instância de UserSession.
     * Este é o ponto de acesso global para o Singleton.
     * Se a instância ainda não existir, ela é criada. Caso contrário, a existente é retornada.
     * @return A única instância de UserSession.
     */
    public static UserSession getInstance() {
        // Verifica se a instância é nula (primeira vez que está sendo acessada)
        if (instance == null) {
            // Se for nula, cria a única instância da classe
            instance = new UserSession();
        }
        // Retorna a instância existente
        return instance;
    }

    /**
     * 5. Define o usuário que acabou de logar com sucesso.
     * Este método será chamado após a autenticação bem-sucedida pelo AuthService.
     * @param user O objeto Usuario que foi autenticado.
     */
    public void setLoggedInUser(Usuario user) {
        this.loggedInUser = user;
    }

    /**
     * 6. Retorna o usuário atualmente logado na sessão.
     * Qualquer parte do sistema que precise saber quem está logado pode chamar este método.
     * @return O objeto Usuario logado, ou null se nenhum usuário estiver logado.
     */
    public Usuario getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * 7. Verifica se há um usuário logado na sessão.
     * @return true se um usuário estiver logado (loggedInUser não é null), false caso contrário.
     */
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    /**
     * 8. Faz o logout do usuário, limpando a sessão.
     * Define o usuário logado como null.
     */
    public void logout() {
        this.loggedInUser = null;
        System.out.println("Usuário desconectado.");
    }
}

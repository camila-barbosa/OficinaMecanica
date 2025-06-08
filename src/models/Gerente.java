/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author marcos_miller
 */

//só iniciada mas ja ta rodante

public class Gerente extends Atendente {
    
    //chama o construtor de usuario 
    public Gerente(String nome, String cpf, String endereco, String email, String telefone, String senha) {
        super(nome, cpf, endereco, email, telefone, senha);
    }

    //metodos para cadastro de um usuário
    public void cadastrarUsuario(Usuario novoUsuario) {
        System.out.println(this.getNome() + " (Gerente) está cadastrando um novo usuário: " + novoUsuario.getNome());
        // lógica para adicionar o novoUsuario a uma lista ou banco de dados de usuários
        // ex: SistemaOficina.getUsuarioCRUD().adicionarUsuario(novoUsuario);
    }

    public void gerenciarUsuarios() {
        System.out.println(this.getNome() + " (Gerente) está acessando a funcionalidade de gerenciamento de usuários.");
        // Lógica para listar, editar, remover usuários existentes
    }

    /**
     * Permite ao gerente gerenciar as despesas da oficina.
     */
    public void gerenciarDespesas() {
        System.out.println(this.getNome() + " (Gerente) está gerenciando as despesas financeiras da oficina.");
        // Lógica para registrar, consultar ou aprovar despesas
    }

    /**
     * Permite ao gerente gerenciar e revisar o registro de ponto dos funcionários.
     */
    public void gerenciarRegistroPonto() {
        System.out.println(this.getNome() + " (Gerente) está revisando os registros de ponto dos funcionários.");
        // Lógica para visualizar, ajustar ou aprovar registros de ponto
    }
}

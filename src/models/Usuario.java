/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

import java.time.LocalDateTime;
import models.enums.TipoUsuario;

/**
 * Superclasse que serve como base para os usuários do sistema
 * @author barbo
 */
public class Usuario {
    // Atributo estático para gerar IDs únicos para cada nova instância de Usuario
    public static int proximoId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String email;
    private String telefone;
    private TipoUsuario tipo; 
    private String senha;

    /**
     * Construtor para criar uma nova instância de Usuário.
     * Atribui um ID único e inicializa os dados básicos do usuário.
     * @param nome O nome completo do usuário.
     * @param cpf O CPF do usuário (será validado no setter).
     * @param endereco O endereço completo do usuário.
     * @param email O endereço de email do usuário.
     * @param telefone O número de telefone do usuário.
     * @param tipo O tipo (perfil) do usuário (Gerente, Atendente, Mecanico).
     * @param senha A senha do usuário (não armazenar em texto puro em produção).
     */
    public Usuario(String nome, String cpf, String endereco, String email, String telefone, TipoUsuario tipo, String senha) {
        this.id = proximoId++; 
        this.nome = nome;
        setCpf(cpf); // Usa o setter para aplicar a validação do CPF
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.tipo = tipo; 
        this.senha = senha;
    }

    // --- Métodos Getters ---

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
    
    public TipoUsuario getTipo(){ 
        return tipo;
    }
    

    // --- Métodos Setters ---

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o CPF do usuário, aplicando validação para garantir que tenha 11 dígitos numéricos.
     * @param cpf O CPF a ser definido.
     * @throws IllegalArgumentException Se o CPF for nulo ou não contiver exatamente 11 dígitos.
     */
    public void setCpf(String cpf) {
        // Validação: verifica se o CPF não é nulo e contém exatamente 11 dígitos numéricos.
        if (cpf != null && cpf.matches("\\d{11}")) {
            this.cpf = cpf;
        } else {
            // Lança uma exceção para indicar um valor inválido.
            // Em um sistema real, essa exceção seria capturada e tratada adequadamente.
            throw new IllegalArgumentException("CPF inválido: Deve conter exatamente 11 dígitos numéricos.");
        }
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setTipo(TipoUsuario tipo){ 
        this.tipo = tipo;
    }

    // --- Métodos de Comportamento e Utilitários ---

    /**
     * Retorna o CPF do usuário em um formato pseudoanonimizado para privacidade.
     * Exemplo: "***.123.***-78"
     * @return O CPF pseudoanonimizado ou uma mensagem de "CPF inválido" se o CPF não for válido.
     */
    public String getCpfPseudoanonimizado() {
        if (cpf != null && cpf.length() == 11) {
            // Formata o CPF para mascarar partes, mantendo apenas alguns dígitos visíveis.
            return "***." + cpf.substring(3, 6) + ".***-" + cpf.substring(9);
        } else {
            return "CPF inválido ou não formatado";
        }
    }

    /**
     * Permite que o usuário altere sua senha.
     * Em um sistema real, a senha antiga seria comparada com um hash.
     * @param senhaAntiga A senha atual do usuário para verificação.
     * @param senhaNova A nova senha a ser definida.
     */
    public void alterarSenha(String senhaAntiga, String senhaNova) {
        if (!this.senha.equals(senhaAntiga)) {
            System.out.println("Erro: Senha antiga incorreta.");
        } else if (this.senha.equals(senhaNova)) {
            System.out.println("Erro: A nova senha não pode ser igual à senha atual.");
        } else {
            this.senha = senhaNova;
            System.out.println("Senha alterada com sucesso.");
        }
    }

    /**
     * Verifica as credenciais de login do usuário.
     * @param emailDigitado O email informado pelo usuário.
     * @param senhaDigitada A senha informada pelo usuário.
     * @return true se o email e a senha corresponderem, false caso contrário.
     */
    public boolean fazerLogin(String emailDigitado, String senhaDigitada) {
        // Compara o email e a senha digitados com os atributos do usuário.
        return this.email.equals(emailDigitado) && this.senha.equals(senhaDigitada);
    }

    /**
     * Simula o registro de ponto do funcionário.
     * (A lógica completa será implementada em uma classe dedicada de Registro de Ponto,
     * que pode ser associada a este usuário.)
     */
    public void registrarPonto() {
        System.out.println(this.nome + " (ID: " + this.id + ") registrou o ponto às " + LocalDateTime.now() + ".");
        // Futuramente, esta informação seria persistida em um sistema de controle de ponto.
    }

    /**
     * Sobrescreve o método toString() para fornecer uma representação textual do objeto Usuario.
     * Útil para depuração e logs.
     * @return Uma string formatada com os detalhes do usuário, exceto a senha completa.
     */
    @Override
    public String toString() {
        // Incluindo o tipo do usuário na representação em String
        return "Usuario{"
                + "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + getCpfPseudoanonimizado() + '\'' +
                ", endereco='" + endereco + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipo='" + tipo.getDescricao() + '\'' + 
                '}';
    }

    // É uma boa prática sobrescrever equals e hashCode se você for usar objetos Usuario
    // em coleções como HashSet ou como chaves em HashMap, ou para comparar objetos por valor.
    // Isso é especialmente importante quando se tem um ID único.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id; // Usuários são considerados iguais se tiverem o mesmo ID
    }

    @Override
    public int hashCode() {
        return id; // O hash code pode ser baseado no ID único para consistência com equals
    }
}


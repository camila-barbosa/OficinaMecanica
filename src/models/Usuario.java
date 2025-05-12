/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

/**
 * Superclasse que serve como base para os usuários do sistema
 * @author barbo
 */
public class Usuario {

    private String nome;
    private String cpf;
    private String endereco;
    private String email;
    private String telefone;
    private String senha;

    //construtor
    public Usuario(String nome, String cpf, String endereco, String email, String telefone, String senha) {
        this.nome = nome;
        setCpf(cpf);
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    //getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null && cpf.matches("\\d{11}")) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    //pseudoanonimização
    public String getCpfPseudoanonimizado() {
        if (cpf != null && cpf.length() == 11) {
            return "***." + cpf.substring(3, 6) + ".***-" + cpf.substring(9);
        } else {
            return "CPF inválido";
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //senha não terá get e set por segurança
    /**
     * permite que o usuário altere a sua senha caso tenha esquecido
     *
     * @param senhaAntiga Atual senha do usuário
     * @param senhaNova nova senha definida
     */
    public void alterarSenha(String senhaAntiga, String senhaNova) {
        if (!this.senha.equals(senhaAntiga)) {
            System.out.println("Senha antiga incorreta");
        } else if (this.senha.equals(senhaNova)) {
            System.out.println("A senha atual não pode ser igual a antiga");
        } else {
            this.senha = senhaNova;
            System.out.println("Senha alterada com sucesso");
        }
    }

    /**
     * Verifica se o login é válido comparando email e senha.
     *
     * @param email O email informado no login.
     * @param senha A senha informada no login.
     * @return true se as credenciais estiverem corretas, false caso contrário.
     */
    public boolean fazerLogin(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    /**
     * Registra um ponto de entrada ou saída com data e hora atual.
     *
     */
    public void registrarPonto() {
        //será criada a classe de registrar ponto, adicionar depois como irá funcionar o método
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "nome='" + nome + '\''
                + ", cpf='" + getCpfPseudoanonimizado() + '\''
                + ", endereco='" + endereco + '\''
                + ", email='" + email + '\''
                + ", telefone='" + telefone + '\''
                + // Senha incluída por segurança
                '}';
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 * representa um cliente da oficina
 *
 * @author barbo
 */
public class Cliente {
    public static int proximoId = 1; 

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private List<Veiculo> veiculos;  //perguntar pelli sobre usar private final

    //construtor
    public Cliente(String nome, String telefone, String email) {
        this.id = proximoId++;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.veiculos = new ArrayList<>(); //inicia lista de veiculos
    }

    //getters e setters
    
    public int getId() { 
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * adiciona um veiculo na lista de veiculos do cliente
     *
     * @param veiculo veiculo a ser adicionado, e nao pode ser nulo
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo");
        }
        veiculos.add(veiculo);
    }

    /**
     * remove um veiculo na lista de veiculos do cliente
     *
     * @param placa Placa do veiculo que vai ser removido
     * @return true se o veículo foi encontrado e removido e false se acontecer
     * o contrário
     */
    public boolean removerVeiculo(String placa) {
        return veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
    }
    
    /**
     * retorna lista de veiculos do cliente
     *
     * @return Lista contendo todos os veiculos do cliente
     */
    public List<Veiculo> listarVeiculos() {
        return new ArrayList<>(veiculos);
    }

    @Override
    public String toString() {
        return "Cliente{"
                + "nome='" + nome + '\''
                + ", telefone='" + telefone + '\''
                + ", email='" + email + '\''
                + ", veiculos=" + veiculos
                + '}';
    }

}

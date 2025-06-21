/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

import java.util.Objects;
import models.enums.StatusOrdem;

/**
 *Classe que representa veículo
 * @author camila_barbosa
 */
public class Veiculo {
    public static int proximoId = 1; // Contador estático para gerar IDs únicos

    private int id; // ID único do veículo
    private String placa;
    private String modelo;
    private String cor;
    private int idCliente; // ALTERADO: De Cliente para int (referência por ID)
    // REMOVIDO: O atributo 'status: StatusOrdem' do Veiculo.
    // Um veículo não tem um "status de ordem" por si só;
    // ele pode estar em uma Ordem de Serviço que tem um status.

    /**
     * Construtor para criar um novo veículo.
     * @param placa A placa do veículo (não pode ser nula).
     * @param modelo O modelo do veículo (não pode ser nulo).
     * @param cor A cor do veículo (não pode ser nula).
     * @param idCliente O ID do cliente proprietário do veículo.
     */
    public Veiculo (String placa, String modelo, String cor, int idCliente){ // ALTERADO: Remove StatusOrdem, recebe idCliente
        this.id = proximoId++;
        this.placa = Objects.requireNonNull(placa, "Placa não pode ser nula.");
        this.modelo = Objects.requireNonNull(modelo, "Modelo não pode ser nulo.");
        this.cor = Objects.requireNonNull(cor, "Cor não pode ser nula.");
        this.idCliente = idCliente; // Atribui o ID do cliente
    }

    /**
     * Construtor para uso pelo Gson ao desserializar (reconstruir o objeto do JSON).
     * @param id ID do veículo.
     * @param placa Placa do veículo.
     * @param modelo Modelo do veículo.
     * @param cor Cor do veículo.
     * @param idCliente ID do cliente proprietário.
     */
    public Veiculo(int id, String placa, String modelo, String cor, int idCliente) { // ALTERADO: Remove StatusOrdem, recebe idCliente
        this.id = id;
        this.placa = Objects.requireNonNull(placa, "Placa não pode ser nula.");
        this.modelo = Objects.requireNonNull(modelo, "Modelo não pode ser nulo.");
        this.cor = Objects.requireNonNull(cor, "Cor não pode ser nula.");
        this.idCliente = idCliente;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }
    
    public String getPlaca(){
        return placa;
    }
    public void setPlaca(String placa){
        this.placa = Objects.requireNonNull(placa, "Placa não pode ser nula.");
    }
    
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = Objects.requireNonNull(modelo, "Modelo não pode ser nulo.");
    }
    public String getCor(){
        return cor;
    }
    public void setCor(String cor){
        this.cor = Objects.requireNonNull(cor, "Cor não pode ser nula.");
    }
    
    // Getter para o ID do cliente
    public int getIdCliente(){ // ALTERADO: Retorna int (ID)
        return idCliente;
    }
    // Setter para o ID do cliente
    public void setIdCliente(int idCliente){ // ALTERADO: Recebe int (ID)
        this.idCliente = idCliente;
    }
    
    // REMOVIDOS getters e setters para 'status'

    // --- Métodos de Comportamento ---
    // O método 'verificarStatus()' não faz mais sentido aqui, pois o status foi removido do veículo.
    // O status é da Ordem de Serviço, não do veículo em si.
    // public StatusOrdem verificarStatus(){ return this.status; } // REMOVER

    @Override
    public String toString() {
        return "Veiculo {" +
                "ID=" + id +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", clienteID=" + idCliente + // Exibe o ID do cliente
                '}';
    }

    // Adição de equals e hashCode para garantir que Veiculo possa ser comparado por ID ou placa
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return id == veiculo.id; // Ou: return placa.equalsIgnoreCase(veiculo.placa); se placa for sua chave única
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Ou Objects.hash(placa);
    }
}
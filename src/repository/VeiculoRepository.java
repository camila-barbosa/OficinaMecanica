/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.Veiculo;
import util.JsonFileHandler;

/**
 *
 * @author marcos_miller
 */
public class VeiculoRepository {

    private List<Veiculo> veiculos; // A lista de todos os veículos em memória
    private JsonFileHandler<Veiculo> fileHandler; // O handler para salvar/carregar JSON

    /**
     * Construtor do VeiculoRepository.
     * Ao ser instanciado, tenta carregar os veículos do arquivo JSON.
     */
    public VeiculoRepository() {
        // Define o tipo para o JsonFileHandler: uma Lista de Veiculo
        Type typeOfListOfVeiculos = new TypeToken<List<Veiculo>>() {}.getType();
        // Inicializa o fileHandler com o nome do arquivo específico para veículos
        this.fileHandler = new JsonFileHandler<>("veiculos.json", typeOfListOfVeiculos);

        // Carrega os veículos ao iniciar o repositório
        this.veiculos = fileHandler.load();

        // CRÍTICO: Ajustar o próximo ID para Veiculo após o carregamento
        int maxId = 0;
        for (Veiculo veiculo : this.veiculos) {
            if (veiculo.getId() > maxId) {
                maxId = veiculo.getId();
            }
        }
        Veiculo.proximoId = maxId + 1; // Ajusta o contador estático na classe Veiculo
        System.out.println("Contador de ID de Veículo ajustado para: " + Veiculo.proximoId);
    }

    /**
     * Adiciona um novo veículo à coleção em memória e persiste as alterações no arquivo JSON.
     * @param veiculo O objeto Veiculo a ser adicionado.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
        System.out.println("Veículo '" + veiculo.getPlaca() + "' (ID: " + veiculo.getId() + ") adicionado ao repositório.");
        fileHandler.save(veiculos); // Salva a lista atualizada no JSON
    }

    /**
     * Atualiza um veículo existente na coleção em memória e persiste as alterações.
     * @param veiculoParaAtualizar O objeto Veiculo que foi modificado.
     */
    public void atualizarVeiculo(Veiculo veiculoParaAtualizar) {
        // Como o objeto veiculoParaAtualizar já é uma referência da lista 'veiculos',
        // basta salvar a lista inteira para persistir as alterações.
        System.out.println("Veículo '" + veiculoParaAtualizar.getPlaca() + "' (ID: " + veiculoParaAtualizar.getId() + ") atualizado no repositório.");
        fileHandler.save(veiculos); // Salva a lista atualizada no JSON
    }

    /**
     * Remove um veículo da coleção em memória pelo seu ID e persiste as alterações.
     * @param id O ID do Veiculo a ser removido.
     * @return true se o veículo foi removido, false caso contrário.
     */
    public boolean removerVeiculo(int id) {
        boolean removido = veiculos.removeIf(veiculo -> veiculo.getId() == id);
        if (removido) {
            System.out.println("Veículo com ID " + id + " removido do repositório.");
            fileHandler.save(veiculos);
        } else {
            System.out.println("Veículo com ID " + id + " não encontrado para remoção no repositório.");
        }
        return removido;
    }

    /**
     * Busca um veículo pelo seu ID.
     * @param id O ID do Veiculo a ser buscado.
     * @return Um Optional contendo o Veiculo se encontrado, ou um Optional vazio.
     */
    public Optional<Veiculo> buscarVeiculoPorId(int id) {
        for (Veiculo v : veiculos) {
            if (v.getId() == id) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    /**
     * Busca um veículo pela sua placa.
     * @param placa A placa do Veiculo a ser buscada.
     * @return Um Optional contendo o Veiculo se encontrado, ou um Optional vazio.
     */
    public Optional<Veiculo> buscarVeiculoPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) { // Comparação case-insensitive
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    /**
     * Lista todos os veículos existentes no repositório.
     * @return Uma lista (cópia) de todos os veículos.
     */
    public List<Veiculo> listarTodosVeiculos() {
        return new ArrayList<>(veiculos); // Retorna uma nova lista para encapsulamento
    }
}
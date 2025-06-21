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
import models.Cliente;
import util.JsonFileHandler;

/**
 *
 * @author marcos_miller
 */
public class ClienteRepository {

    private List<Cliente> clientes; // A lista de todos os clientes em memória
    private JsonFileHandler<Cliente> fileHandler; // O handler para salvar/carregar JSON

    /**
     * Construtor do ClienteRepository.
     * Ao ser instanciado, tenta carregar os clientes do arquivo JSON.
     */
    public ClienteRepository() {
        // Define o tipo para o JsonFileHandler: uma Lista de Cliente
        Type typeOfListOfClientes = new TypeToken<List<Cliente>>() {}.getType();
        // Inicializa o fileHandler com o nome do arquivo específico para clientes
        this.fileHandler = new JsonFileHandler<>("clientes.json", typeOfListOfClientes);

        // Carrega os clientes ao iniciar o repositório
        this.clientes = fileHandler.load();

        // CRÍTICO: Ajustar o próximo ID para Cliente após o carregamento
        // Isso evita que novos clientes tenham IDs duplicados com os já carregados do arquivo.
        int maxId = 0;
        for (Cliente cliente : this.clientes) {
            if (cliente.getId() > maxId) {
                maxId = cliente.getId();
            }
        }
        Cliente.proximoId = maxId + 1; // Ajusta o contador estático na classe Cliente
        System.out.println("Contador de ID de Cliente ajustado para: " + Cliente.proximoId);
    }

    /**
     * Adiciona um novo cliente à coleção em memória e persiste as alterações no arquivo JSON.
     * @param cliente O objeto Cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente '" + cliente.getNome() + "' (ID: " + cliente.getId() + ") adicionado ao repositório.");
        fileHandler.save(clientes); // Salva a lista atualizada no JSON
    }

    /**
     * Atualiza um cliente existente na coleção em memória e persiste as alterações.
     * Deve ser chamado após as alterações no objeto Cliente já terem sido feitas.
     * @param clienteParaAtualizar O objeto Cliente que foi modificado.
     */
    public void atualizarCliente(Cliente clienteParaAtualizar) {
        // Como o objeto clienteParaAtualizar já é uma referência da lista 'clientes',
        // basta salvar a lista inteira para persistir as alterações.
        System.out.println("Cliente '" + clienteParaAtualizar.getNome() + "' (ID: " + clienteParaAtualizar.getId() + ") atualizado no repositório.");
        fileHandler.save(clientes); // Salva a lista atualizada no JSON
    }

    /**
     * Remove um cliente da coleção em memória pelo seu ID e persiste as alterações.
     * @param id O ID do Cliente a ser removido.
     * @return true se o cliente foi removido, false caso contrário.
     */
    public boolean removerCliente(int id) {
        boolean removido = clientes.removeIf(cliente -> cliente.getId() == id);
        if (removido) {
            System.out.println("Cliente com ID " + id + " removido do repositório.");
            fileHandler.save(clientes);
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado para remoção no repositório.");
        }
        return removido;
    }

    /**
     * Busca um cliente pelo seu ID.
     * @param id O ID do Cliente a ser buscado.
     * @return Um Optional contendo o Cliente se encontrado, ou um Optional vazio.
     */
    public Optional<Cliente> buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * Busca um cliente pelo seu email.
     * @param email O email do Cliente a ser buscado.
     * @return Um Optional contendo o Cliente se encontrado, ou um Optional vazio.
     */
    public Optional<Cliente> buscarClientePorEmail(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(email)) { // Comparação case-insensitive
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * Busca um cliente pelo seu telefone.
     * @param telefone O telefone do Cliente a ser buscado.
     * @return Um Optional contendo o Cliente se encontrado, ou um Optional vazio.
     */
    public Optional<Cliente> buscarClientePorTelefone(String telefone) {
        for (Cliente c : clientes) {
            if (c.getTelefone().equals(telefone)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * Lista todos os clientes existentes no repositório.
     * @return Uma lista (cópia) de todos os clientes.
     */
    public List<Cliente> listarTodosClientes() {
        return new ArrayList<>(clientes); // Retorna uma nova lista para encapsulamento
    }
}

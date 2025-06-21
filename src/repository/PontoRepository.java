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
import models.RegistroPonto;
import util.JsonFileHandler;

/**
 *
 * @author marcos_miller
 */
public class PontoRepository {

    private List<RegistroPonto> registros; // A lista de todos os registros de ponto
    private JsonFileHandler<RegistroPonto> fileHandler; // O handler para salvar/carregar JSON

    /**
     * Construtor do PontoRepository.
     * Ao ser instanciado, tenta carregar os registros de ponto do arquivo JSON.
     */
    public PontoRepository() {
        // Define o tipo para o JsonFileHandler: uma Lista de RegistroPonto
        Type typeOfListOfRegistros = new TypeToken<List<RegistroPonto>>() {}.getType();
        // Inicializa o fileHandler com o nome do arquivo específico para registros de ponto
        this.fileHandler = new JsonFileHandler<>("registros_ponto.json", typeOfListOfRegistros);

        // Carrega os registros de ponto ao iniciar o repositório
        this.registros = fileHandler.load();

        // CRÍTICO: Ajustar o próximo ID para RegistroPonto após o carregamento
        // Isso evita que novos registros tenham IDs duplicados com os já carregados do arquivo.
        int maxId = 0;
        for (RegistroPonto registro : this.registros) {
            if (registro.getId() > maxId) {
                maxId = registro.getId();
            }
        }
        RegistroPonto.proximoId = maxId + 1; // Ajusta o contador estático na classe RegistroPonto
        System.out.println("Contador de ID de RegistroPonto ajustado para: " + RegistroPonto.proximoId);
    }

    /**
     * Adiciona um novo registro de ponto à coleção e persiste as alterações.
     * @param registro O objeto RegistroPonto a ser adicionado.
     */
    public void adicionarRegistro(RegistroPonto registro) {
        registros.add(registro);
        System.out.println("Registro de Ponto ID " + registro.getId() + " adicionado para Usuário ID " + registro.getIdUsuario() + ".");
        fileHandler.save(registros); // Salva a lista atualizada no JSON
    }

    /**
     * Atualiza um registro de ponto existente na coleção e persiste as alterações.
     * Geralmente usado para adicionar a dataHoraSaida a um registro de entrada.
     * @param registroParaAtualizar O objeto RegistroPonto que foi modificado (referência já existente na lista).
     */
    public void atualizarRegistro(RegistroPonto registroParaAtualizar) {
        // Como você está atualizando uma referência do objeto que já está na lista 'registros',
        // não é necessário "encontrar" e "substituir" aqui. Apenas salve a lista.
        // O PontoRepository já contém a referência a esse objeto na sua lista 'registros'.
        System.out.println("Registro de Ponto ID " + registroParaAtualizar.getId() + " atualizado para Usuário ID " + registroParaAtualizar.getIdUsuario() + ".");
        fileHandler.save(registros); // Salva a lista atualizada no JSON
    }

    /**
     * Busca o último registro de ponto de ENTRADA sem uma SAÍDA registrada para um usuário.
     * @param idUsuario O ID do usuário.
     * @return Um Optional contendo o RegistroPonto, se encontrado, ou um Optional vazio.
     */
    public Optional<RegistroPonto> buscarUltimoPontoAbertoPorUsuario(int idUsuario) {
        // Percorre a lista de trás para frente para encontrar o mais recente primeiro
        for (int i = registros.size() - 1; i >= 0; i--) {
            RegistroPonto registro = registros.get(i);
            // Verifica se é do usuário correto E se a dataHoraSaida ainda é nula
            if (registro.getIdUsuario() == idUsuario && registro.getDataHoraSaida() == null) {
                return Optional.of(registro);
            }
        }
        return Optional.empty(); // Nenhum registro de entrada aberto encontrado
    }

    /**
     * Lista todos os registros de ponto para um usuário específico.
     * @param idUsuario O ID do usuário.
     * @return Uma lista (cópia) de todos os registros de ponto do usuário.
     */
    public List<RegistroPonto> listarRegistrosPorUsuario(int idUsuario) {
        List<RegistroPonto> registrosDoUsuario = new ArrayList<>();
        for (RegistroPonto registro : registros) {
            if (registro.getIdUsuario() == idUsuario) {
                registrosDoUsuario.add(registro);
            }
        }
        return registrosDoUsuario; // Retorna uma nova lista para encapsulamento
    }

    /**
     * Lista todos os registros de ponto existentes no sistema.
     * @return Uma lista (cópia) de todos os registros de ponto.
     */
    public List<RegistroPonto> listarTodosRegistros() {
        return new ArrayList<>(registros); // Retorna uma nova lista para encapsulamento
    }
}

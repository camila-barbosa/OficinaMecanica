/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import models.RegistroPonto;
import models.Usuario;
import repository.PontoRepository;

/**
 *
 * @author marcos_miller
 */
public class RegistroPontoService {

    private PontoRepository pontoRepository; // Dependência do repositório de ponto

    /**
     * Construtor do RegistroPontoService.
     * @param pontoRepository Instância do PontoRepository para gerenciar a persistência dos registros de ponto.
     */
    public RegistroPontoService(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    /**
     * Registra a entrada de ponto para um usuário.
     * @param usuario O usuário que está batendo o ponto.
     * @return O RegistroPonto criado, ou null se já houver uma entrada aberta.
     * @throws IllegalStateException Se o usuário já tiver uma entrada de ponto em aberto.
     */
    public RegistroPonto registrarEntrada(Usuario usuario) throws IllegalStateException {
        // 1. Validação de Negócio: Verificar se já existe uma entrada em aberto para este usuário
        Optional<RegistroPonto> pontoAberto = pontoRepository.buscarUltimoPontoAbertoPorUsuario(usuario.getId());
        if (pontoAberto.isPresent()) {
            throw new IllegalStateException("Erro: Usuário já possui uma entrada de ponto em aberto.");
        }

        // 2. Criação do Modelo: Cria um novo objeto RegistroPonto para a entrada
        RegistroPonto novoRegistro = new RegistroPonto(usuario.getId()); // O construtor já define a hora atual

        // 3. Persistência: Adiciona o novo registro ao repositório
        pontoRepository.adicionarRegistro(novoRegistro);
        System.out.println("Ponto de entrada registrado para " + usuario.getNome() + " às " + novoRegistro.getDataHoraEntrada().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm dd/MM")));
        return novoRegistro;
    }

    /**
     * Registra a saída de ponto para um usuário.
     * @param usuario O usuário que está batendo o ponto.
     * @return O RegistroPonto atualizado com a saída, ou null se não houver entrada em aberto.
     * @throws IllegalStateException Se o usuário não tiver uma entrada de ponto em aberto.
     */
    public RegistroPonto registrarSaida(Usuario usuario) throws IllegalStateException {
        // 1. Validação de Negócio: Buscar a entrada de ponto em aberto
        Optional<RegistroPonto> pontoAberto = pontoRepository.buscarUltimoPontoAbertoPorUsuario(usuario.getId());
        if (pontoAberto.isEmpty()) {
            throw new IllegalStateException("Erro: Não há registro de entrada de ponto em aberto para este usuário.");
        }

        RegistroPonto registroParaAtualizar = pontoAberto.get();
        // 2. Atualização do Modelo: Define a data e hora de saída no registro existente
        registroParaAtualizar.setDataHoraSaida(LocalDateTime.now());

        // 3. Persistência: Atualiza o registro no repositório (salva a lista)
        pontoRepository.atualizarRegistro(registroParaAtualizar);
        System.out.println("Ponto de saída registrado para " + usuario.getNome() + " às " + registroParaAtualizar.getDataHoraSaida().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm dd/MM")));
        return registroParaAtualizar;
    }

    /**
     * Obtém o status atual do ponto de um usuário (entrada em aberto ou não).
     * @param usuario O usuário para verificar o status.
     * @return Um Optional contendo o RegistroPonto se houver uma entrada em aberto, ou um Optional vazio.
     */
    public Optional<RegistroPonto> obterStatusPontoAtual(Usuario usuario) {
        return pontoRepository.buscarUltimoPontoAbertoPorUsuario(usuario.getId());
    }

    /**
     * Lista o histórico de registros de ponto para um usuário.
     * @param usuario O usuário para listar o histórico.
     * @return Uma lista dos registros de ponto do usuário.
     */
    public List<RegistroPonto> listarHistorico(Usuario usuario) {
        return pontoRepository.listarRegistrosPorUsuario(usuario.getId());
    }
}

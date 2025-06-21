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
import models.Usuario;
import util.JsonFileHandler;

/**
 *
 * @author marcos_miller
 */

public class UsuarioCRUD {

    private List<Usuario> usuarios = new ArrayList<>();
    // Instância do seu JsonFileHandler para Usuario, responsável por salvar/carregar
    private JsonFileHandler<Usuario> fileHandler;

    /**
     * Construtor da classe UsuarioCRUD.
     * Ao ser instanciado, tenta carregar a lista de usuários de um arquivo JSON existente.
     */
    public UsuarioCRUD() {
        // Define o tipo para o JsonFileHandler. Essencial para desserialização genérica de List<Usuario>!
        Type typeOfListOfUsers = new TypeToken<List<Usuario>>() {}.getType();
        // Inicializa o fileHandler com o nome do arquivo e o tipo da lista
        this.fileHandler = new JsonFileHandler<>("usuarios.json", typeOfListOfUsers);

        // Carrega os usuários ao iniciar o CRUD
        this.usuarios = fileHandler.load(); // A lista de usuários é carregada pelo handler

        // CRÍTICO: Ajustar o próximo ID após o carregamento para evitar IDs duplicados
        int maxId = 0;
        for (Usuario usuario : this.usuarios) {
            if (usuario.getId() > maxId) {
                maxId = usuario.getId();
            }
        }
        Usuario.proximoId = maxId + 1;
        System.out.println("Contador de ID de Usuário ajustado para: " + Usuario.proximoId);
    }

    /**
     * Adiciona um novo usuário à lista e persiste as alterações.
     * @param usuario O objeto Usuario a ser adicionado.
     */
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário " + usuario.getNome() + " (ID: " + usuario.getId() + ") adicionado com sucesso.");
        fileHandler.save(usuarios); // Salva a lista atualizada após adicionar
    }

    /**
     * Remove um usuário da lista pelo seu ID e persiste as alterações.
     * @param id O ID do usuário a ser removido.
     * @return true se o usuário foi removido, false caso contrário.
     */
    public boolean removerUsuario(int id) {
        boolean removido = usuarios.removeIf(usuario -> usuario.getId() == id);
        if (removido) {
            System.out.println("Usuário com ID " + id + " removido com sucesso.");
            fileHandler.save(usuarios); // Salva a lista atualizada após remover
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado para remoção.");
        }
        return removido;
    }

    /**
     * Atualiza os dados de um usuário existente na lista pelo seu ID.
     * Assume que os dados (novoNome, novoEndereco, etc.) já foram validados
     * pela camada de serviço (UsuarioService).
     * @param id O ID do usuário a ser atualizado.
     * @param novoNome O novo nome do usuário.
     * @param novoEndereco O novo endereço do usuário.
     * @param novoEmail O novo email do usuário.
     * @param novoTelefone O novo telefone do usuário.
     * @return true se o usuário foi atualizado, false caso contrário.
     */
    public boolean atualizarUsuario(int id, String novoNome, String novoEndereco, String novoEmail, String novoTelefone) {
        // Encontra o usuário pelo ID
        Optional<Usuario> usuarioOpt = buscarUsuarioPorIdOptional(id);
        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuário com ID " + id + " não encontrado para atualização.");
            return false;
        }

        Usuario usuarioParaAtualizar = usuarioOpt.get();
        
        // Atualiza os atributos do objeto Usuario em memória
        usuarioParaAtualizar.setNome(novoNome);
        usuarioParaAtualizar.setEndereco(novoEndereco);
        usuarioParaAtualizar.setEmail(novoEmail);
        usuarioParaAtualizar.setTelefone(novoTelefone);
        // Note: O tipo não é atualizado aqui porque este método do CRUD não recebe o tipo.
        // Se você quiser que o CRUD atualize o tipo, o parâmetro `novoTipo` precisaria ser adicionado aqui.
        // No entanto, o `UsuarioService.atualizarUsuario` já atualiza o objeto diretamente, então está ok.

        System.out.println("Usuário com ID " + id + " atualizado em memória.");
        fileHandler.save(usuarios); // Salva a lista (que contém o objeto atualizado)
        return true;
    }

    /**
     * Lista todos os usuários cadastrados.
     * @return Uma nova lista contendo todos os usuários, para evitar modificações diretas na lista interna.
     */
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios); // Retorna uma cópia para encapsulamento
    }

    /**
     * Busca um usuário pelo seu ID.
     * @param id O ID do usuário a ser buscado.
     * @return O objeto Usuario se encontrado, ou null caso contrário.
     * @deprecated Use {@link #buscarUsuarioPorIdOptional(int)} para lidar com ausência de forma mais segura.
     */
    @Deprecated
    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Busca um usuário pelo seu ID, retornando um Optional.
     * Recomendado para lidar com a ausência de um valor de forma explícita.
     * @param id O ID do usuário a ser buscado.
     * @return Um Optional contendo o Usuario se encontrado, ou um Optional vazio.
     */
    public Optional<Usuario> buscarUsuarioPorIdOptional(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    
    // NOVOS MÉTODOS DE BUSCA: Essenciais para o UsuarioService!
    

    /**
     * Busca um usuário pelo seu CPF, retornando um Optional.
     * @param cpf O CPF do usuário a ser buscado.
     * @return Um Optional contendo o Usuario se encontrado, ou um Optional vazio.
     */
    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    /**
     * Busca um usuário pelo seu Email, retornando um Optional.
     * @param email O Email do usuário a ser buscado.
     * @return Um Optional contendo o Usuario se encontrado, ou um Optional vazio.
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}
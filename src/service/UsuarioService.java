/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import models.Usuario;
import models.enums.TipoUsuario;
import repository.UsuarioCRUD;

/**
 *
 * @author marcos_miller
 */
public class UsuarioService {

    private UsuarioCRUD usuarioCRUD; // Dependência da camada de dados

    /**
     * Construtor do UsuarioService.
     * @param usuarioCRUD Instância do UsuarioCRUD para gerenciar a persistência de usuários.
     */
    public UsuarioService(UsuarioCRUD usuarioCRUD) {
        this.usuarioCRUD = usuarioCRUD;
    }

    /**
     * Adiciona um novo usuário ao sistema.
     * Realiza validações de negócio (unicidade de CPF e Email) antes de persistir o usuário.
     * O formato do CPF é validado na própria classe Usuario.
     * @param nome O nome do usuário.
     * @param cpf O CPF do usuário.
     * @param endereco O endereço do usuário.
     * @param email O email do usuário.
     * @param telefone O telefone do usuário.
     * @param senha A senha do usuário.
     * @param tipo O tipo de usuário (ATENDENTE, MECANICO, GERENTE).
     * @return O objeto Usuario recém-criado e persistido.
     * @throws IllegalArgumentException Se o CPF for inválido (formato - vindo do construtor de Usuario).
     * @throws IllegalStateException Se o CPF ou Email já estiverem cadastrados.
     */
    public Usuario adicionarUsuario(String nome, String cpf, String endereco, String email,
                                   String telefone, String senha, TipoUsuario tipo)
                                   throws IllegalArgumentException, IllegalStateException {
        // Validações de nulidade para parâmetros obrigatórios
        Objects.requireNonNull(nome, "Nome do usuário não pode ser nulo.");
        Objects.requireNonNull(cpf, "CPF do usuário não pode ser nulo.");
        Objects.requireNonNull(endereco, "Endereço do usuário não pode ser nulo.");
        Objects.requireNonNull(email, "Email do usuário não pode ser nulo.");
        Objects.requireNonNull(telefone, "Telefone do usuário não pode ser nulo.");
        Objects.requireNonNull(senha, "Senha do usuário não pode ser nula.");
        Objects.requireNonNull(tipo, "Tipo de usuário não pode ser nulo.");

        // --- Validações de Negócio ---
        // 1. Validação de Unicidade do CPF
        Optional<Usuario> usuarioExistenteCpf = usuarioCRUD.buscarUsuarioPorCpf(cpf);
        if (usuarioExistenteCpf.isPresent()) {
            throw new IllegalStateException("Erro: CPF '" + cpf + "' já cadastrado no sistema.");
        }

        // 2. Validação de Unicidade do Email
        Optional<Usuario> usuarioExistenteEmail = usuarioCRUD.buscarUsuarioPorEmail(email);
        if (usuarioExistenteEmail.isPresent()) {
            throw new IllegalStateException("Erro: Email '" + email + "' já cadastrado no sistema.");
        }

        // --- Orquestração da Criação do Modelo e Persistência ---
        // A classe Usuario (model) faz a validação do formato do CPF em seu construtor/setter.
        // Se o CPF tiver formato inválido, uma IllegalArgumentException será lançada pelo construtor de Usuario.
        Usuario novoUsuario = new Usuario(nome, cpf, endereco, email, telefone, tipo, senha);

        // Delega a persistência para a camada de repositório (CRUD)
        usuarioCRUD.adicionarUsuario(novoUsuario);
        return novoUsuario;
    }

    /**
     * Atualiza os dados de um usuário existente.
     * Realiza validações de negócio, como a unicidade do novo email, se alterado.
     * @param id O ID do usuário a ser atualizado.
     * @param novoNome O novo nome.
     * @param novoEndereco O novo endereço.
     * @param novoEmail O novo email.
     * @param novoTelefone O novo telefone.
     * @param novoTipo O novo tipo de usuário.
     * @return true se a atualização foi bem-sucedida, false caso contrário (usuário não encontrado).
     * @throws IllegalStateException Se o novo Email já estiver cadastrado por outro usuário.
     */
    public boolean atualizarUsuario(int id, String novoNome, String novoEndereco,
                                   String novoEmail, String novoTelefone, TipoUsuario novoTipo)
                                   throws IllegalStateException {
        // Validações de nulidade para parâmetros obrigatórios
        Objects.requireNonNull(novoNome, "Novo nome do usuário não pode ser nulo.");
        Objects.requireNonNull(novoEndereco, "Novo endereço do usuário não pode ser nulo.");
        Objects.requireNonNull(novoEmail, "Novo email do usuário não pode ser nulo.");
        Objects.requireNonNull(novoTelefone, "Novo telefone do usuário não pode ser nulo.");
        Objects.requireNonNull(novoTipo, "Novo tipo de usuário não pode ser nulo.");


        Optional<Usuario> usuarioParaAtualizarOpt = usuarioCRUD.buscarUsuarioPorIdOptional(id);
        if (usuarioParaAtualizarOpt.isEmpty()) {
            return false; // Usuário não encontrado no repositório
        }

        Usuario usuarioParaAtualizar = usuarioParaAtualizarOpt.get();

        // Validação de Unicidade do Email:
        // Acontece SOMENTE se o email for diferente do atual DO MESMO USUÁRIO
        // E se o novo email já existir em OUTRO usuário no sistema.
        if (!usuarioParaAtualizar.getEmail().equals(novoEmail)) { // Se o email mudou
            Optional<Usuario> usuarioComEmailExistente = usuarioCRUD.buscarUsuarioPorEmail(novoEmail);
            if (usuarioComEmailExistente.isPresent() && usuarioComEmailExistente.get().getId() != id) {
                // Se encontrou outro usuário com o novo email (e não é o próprio usuário que estamos atualizando)
                throw new IllegalStateException("Erro: Novo email '" + novoEmail + "' já cadastrado por outro usuário.");
            }
        }
        
        // --- Atualiza os dados no objeto Usuario (model) ---
        // As validações intrínsecas (ex: formato de CPF/Email se fossem sets separados) seriam feitas aqui pelos setters.
        usuarioParaAtualizar.setNome(novoNome);
        usuarioParaAtualizar.setEndereco(novoEndereco);
        usuarioParaAtualizar.setEmail(novoEmail);
        usuarioParaAtualizar.setTelefone(novoTelefone);
        usuarioParaAtualizar.setTipo(novoTipo); // Atualiza o tipo do usuário!

        // Delega ao repositório para salvar as alterações.
        // O repositório UsuarioCRUD já chama fileHandler.save(usuarios) no seu método atualizarUsuario.
        // Como o objeto 'usuarioParaAtualizar' é uma referência da lista interna do CRUD,
        // as modificações já estão na lista. Basta chamar o método atualizarUsuario do CRUD
        // que ele se encarrega de persistir a lista atualizada.
        // O método `atualizarUsuario` do `UsuarioCRUD` que você me forneceu espera o `id` e os novos dados
        // (nome, endereco, email, telefone). Ele não espera o objeto Usuario já atualizado.
        // A chamada mais correta seria:
        boolean sucessoAtualizacaoNoCRUD = usuarioCRUD.atualizarUsuario(id, novoNome, novoEndereco, novoEmail, novoTelefone);
        return sucessoAtualizacaoNoCRUD; // Retorna o resultado da operação do CRUD
    }

    /**
     * Remove um usuário pelo ID.
     * @param id O ID do usuário a ser removido.
     * @return true se o usuário foi removido, false caso contrário.
     */
    public boolean removerUsuario(int id) {
        return usuarioCRUD.removerUsuario(id); // Delega diretamente para o CRUD
    }

    /**
     * Lista todos os usuários.
     * @return Uma lista de todos os usuários.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioCRUD.listarUsuarios(); // Delega diretamente para o CRUD
    }

    /**
     * Busca um usuário pelo ID.
     * @param id O ID do usuário.
     * @return Um Optional contendo o usuário, ou vazio se não encontrado.
     */
    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return usuarioCRUD.buscarUsuarioPorIdOptional(id); // Delega para o CRUD
    }

    /**
     * Busca um usuário pelo CPF.
     * @param cpf O CPF do usuário.
     * @return Um Optional contendo o usuário, ou vazio se não encontrado.
     */
    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        return usuarioCRUD.buscarUsuarioPorCpf(cpf); // Delega para o CRUD
    }

    /**
     * Busca um usuário pelo Email.
     * @param email O Email do usuário.
     * @return Um Optional contendo o usuário, ou vazio se não encontrado.
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioCRUD.buscarUsuarioPorEmail(email); // Delega para o CRUD
    }
}

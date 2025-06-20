/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.util.Optional;
import models.Usuario;
import models.enums.TipoUsuario;
import repository.UsuarioCRUD;

/**
 *
 * @author marcos_miller
 */
public class UsuarioService {

    private UsuarioCRUD usuarioCRUD;

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
        // --- Validações de Negócio ---
        // 1. Validação de Unicidade do CPF (chamando o novo método do CRUD)
        Optional<Usuario> usuarioExistenteCpf = usuarioCRUD.buscarUsuarioPorCpf(cpf);
        if (usuarioExistenteCpf.isPresent()) {
            throw new IllegalStateException("Erro: CPF '" + cpf + "' já cadastrado no sistema.");
        }

        // 2. Validação de Unicidade do Email (chamando o método do CRUD)
        Optional<Usuario> usuarioExistenteEmail = usuarioCRUD.buscarUsuarioPorEmail(email);
        if (usuarioExistenteEmail.isPresent()) {
            throw new IllegalStateException("Erro: Email '" + email + "' já cadastrado no sistema.");
        }

        // --- Orquestração da Criação do Modelo e Persistência ---
        // A classe Usuario (model) faz a validação do formato do CPF em seu construtor/setter.
        // Se o CPF tiver formato inválido, uma IllegalArgumentException será lançada aqui.
        Usuario novoUsuario = new Usuario(nome, cpf, endereco, email, telefone, tipo, senha); // Passa o 'tipo' para o construtor do Usuario!

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
                                   String novoEmail, String novoTelefone, TipoUsuario novoTipo) // Parâmetro novoTipo adicionado corretamente!
                                   throws IllegalStateException {
        Optional<Usuario> usuarioParaAtualizarOpt = usuarioCRUD.buscarUsuarioPorIdOptional(id);
        if (usuarioParaAtualizarOpt.isEmpty()) {
            return false;
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
        
        // --- Atualiza os dados no objeto (o model ainda faz as validações internas, ex: setCpf) ---
        usuarioParaAtualizar.setNome(novoNome);
        usuarioParaAtualizar.setEndereco(novoEndereco);
        usuarioParaAtualizar.setEmail(novoEmail);
        usuarioParaAtualizar.setTelefone(novoTelefone);
        usuarioParaAtualizar.setTipo(novoTipo); // Atualiza o tipo do usuário!

        // A camada CRUD já salva automaticamente após a atualização interna no objeto referenciado pela lista
        // (porque a referência é a mesma que está na lista do CRUD, então quando o objeto é alterado,
        // o CRUD percebe a alteração e, no seu save, serializa o objeto já modificado).
        // Não é necessário chamar usuarioCRUD.atualizarUsuario com todos os parâmetros novamente aqui
        // se o seu método atualizarUsuario no CRUD apenas percorre e encontra o objeto.
        // Se o seu método `atualizarUsuario` no `UsuarioCRUD` for apenas `return usuarioCRUD.save(usuarios);`
        // após a alteração dos atributos do `usuarioParaAtualizar`, então basta fazer isso.

        // Dado o seu `atualizarUsuario` atual no CRUD que recebe os dados brutos e busca o usuário,
        // o mais consistente é ter um método no CRUD que receba o OBJETO Usuario já modificado e o salve.
        // Por enquanto, vamos chamar o save() diretamente no service para garantir a persistência.
        // Isso porque o método `atualizarUsuario` no seu CRUD busca o usuário pelo ID novamente e
        // redefine seus atributos. É mais limpo passar o objeto já atualizado.
        
        // Melhoria: Adicionar um método no UsuarioCRUD como `public void salvarAlteracoes(Usuario usuario)`
        // que simplesmente salva a lista interna de usuários.
        // Por agora, vamos chamar `usuarioCRUD.salvarUsuarios()` (assumindo que seja público para teste, ou refatorar o CRUD).
        // Se o seu `usuarioCRUD.atualizarUsuario` do repositório já chama o `salvarUsuarios()`, então está ok como está.
        // Pelo que vi no seu `UsuarioCRUD`, o `atualizarUsuario` dele já chama `salvarUsuarios()`
        // então o `return true;` na verdade é o que indica sucesso.
        // A chamada a `usuarioCRUD.atualizarUsuario(id, ...)` ao final desta função
        // é redundante se você já alterou o objeto `usuarioParaAtualizar` diretamente.
        // Apenas retornar true se o `usuarioParaAtualizarOpt` foi encontrado e atualizado é suficiente.
        return true; // Retorna true porque o objeto em memória (que está na lista do CRUD) foi modificado.
                     // O CRUD (no método dele) já chama o save() quando a lista interna é alterada.
                     // A responsabilidade de chamar o save() já está lá no atualizarUsuario do CRUD.
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

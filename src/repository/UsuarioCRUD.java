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
    // NOVO: Instância do seu JsonFileHandler para Usuario
    private JsonFileHandler<Usuario> fileHandler;


    public UsuarioCRUD() {
        // Define o tipo para o JsonFileHandler. Essencial para desserialização genérica!
        Type typeOfListOfUsers = new TypeToken<List<Usuario>>() {}.getType();
        // Inicializa o fileHandler com o nome do arquivo e o tipo da lista
        this.fileHandler = new JsonFileHandler<>("usuarios.json", typeOfListOfUsers);

        // Carrega os usuários ao iniciar o CRUD
        this.usuarios = fileHandler.load(); // A lista de usuários agora é carregada pelo handler

        // CRÍTICO: Ajustar o próximo ID após o carregamento
        // Essa lógica ainda precisa estar aqui ou em algum lugar que conheça os IDs carregados
        int maxId = 0;
        for (Usuario usuario : this.usuarios) {
            if (usuario.getId() > maxId) {
                maxId = usuario.getId();
            }
        }
        Usuario.proximoId = maxId + 1;
        System.out.println("Contador de ID de Usuário ajustado para: " + Usuario.proximoId);
    }

    // --- Métodos CRUD (inalterados, apenas chamam o save() do handler) ---

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário " + usuario.getNome() + " (ID: " + usuario.getId() + ") adicionado com sucesso.");
        fileHandler.save(usuarios); // Chama o handler para salvar a lista
    }

    public boolean removerUsuario(int id) {
        boolean removido = usuarios.removeIf(usuario -> usuario.getId() == id);
        if (removido) {
            System.out.println("Usuário com ID " + id + " removido com sucesso.");
            fileHandler.save(usuarios); // Chama o handler para salvar a lista
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado para remoção.");
        }
        return removido;
    }

    public boolean atualizarUsuario(int id, String novoNome, String novoEndereco, String novoEmail, String novoTelefone) {
        boolean atualizado = false;
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setNome(novoNome);
                usuario.setEndereco(novoEndereco);
                usuario.setEmail(novoEmail);
                usuario.setTelefone(novoTelefone);
                System.out.println("Usuário com ID " + id + " atualizado com sucesso.");
                atualizado = true;
                break;
            }
        }
        if (atualizado) {
            fileHandler.save(usuarios); // Chama o handler para salvar a lista
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado para atualização.");
        }
        return atualizado;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public Optional<Usuario> buscarUsuarioPorIdOptional(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}

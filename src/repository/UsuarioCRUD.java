/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.Usuario;

/**
 *
 * @author marcos_miller
 */

public class UsuarioCRUD {

    private List<Usuario> usuarios = new ArrayList<>();

  
    public void adicionarUsuario(Usuario usuario) { 
        usuarios.add(usuario);
        System.out.println("Usuário " + usuario.getNome() + " (ID: " + usuario.getId() + ") adicionado com sucesso.");
    }

 
    public boolean removerUsuario(int id) {
        boolean removido = usuarios.removeIf(usuario -> usuario.getId() == id);
        if (removido) {
            System.out.println("Usuário com ID " + id + " removido com sucesso.");
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado para remoção.");
        }
        return removido;
    }

 
    public boolean atualizarUsuario(int id, String novoNome, String novoEndereco, String novoEmail, String novoTelefone) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setNome(novoNome);
                usuario.setEndereco(novoEndereco);
                usuario.setEmail(novoEmail);
                usuario.setTelefone(novoTelefone);
                System.out.println("Usuário com ID " + id + " atualizado com sucesso.");
                return true;
            }
        }
        System.out.println("Usuário com ID " + id + " não encontrado para atualização.");
        return false;
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

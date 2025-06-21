/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.componentes;

/**
 *
 * @author marcos_miller
 */

import java.util.Scanner;
import models.ItemEstoque;
import repository.EstoqueCRUD;

public class CompGerenciarEstoque {
    private EstoqueCRUD estoque = new EstoqueCRUD();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n===== Menu do Estoque =====");
            System.out.println("1. Adicionar item");
            System.out.println("2. Remover item");
            System.out.println("3. Atualizar item");
            System.out.println("4. Listar itens");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> adicionarItem();
                case 2 -> removerItem();
                case 3 -> atualizarItem();
                case 4 -> listarItens();
                case 0 -> System.out.println("Saindo do menu...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void adicionarItem() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Preço unitário: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        ItemEstoque item = new ItemEstoque(nome, quantidade, preco);
        estoque.adicionarItem(item);
        System.out.println("Item adicionado com sucesso!");
    }

    private void removerItem() {
        System.out.print("Informe o ID do item a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean removido = estoque.removerItem(id);
        if (removido) {
            System.out.println("Item removido com sucesso.");
        } else {
            System.out.println("Item não encontrado.");
        }
    }

    private void atualizarItem() {
        System.out.print("Informe o ID do item a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ItemEstoque item = estoque.buscarItemPorId(id);
        if (item == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova quantidade: ");
        int novaQuantidade = scanner.nextInt();
        System.out.print("Novo preço unitário: ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine();

        boolean atualizado = estoque.atualizarItem(id, novoNome, novaQuantidade, novoPreco);
        if (atualizado) {
            System.out.println("Item atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar item.");
        }
    }

    private void listarItens() {
        System.out.println("\n=== Itens no Estoque ===");
        for (ItemEstoque item : estoque.listarItens()) {
            System.out.println(item);
        }
    }
}
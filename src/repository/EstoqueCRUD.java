/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

/**
 *
 * @author marcos_miller
 */

import java.util.ArrayList;
import java.util.List;
import models.ItemEstoque;

public class EstoqueCRUD {
    private List<ItemEstoque> itens = new ArrayList<>();

    public void adicionarItem(ItemEstoque item) {
        itens.add(item);
    }

    public boolean removerItem(int id) {
        return itens.removeIf(item -> item.getId() == id);
    }

    public boolean atualizarItem(int id, String novoNome, int novaQuantidade, double novoPreco) {
        for (ItemEstoque item : itens) {
            if (item.getId() == id) {
                item.setNome(novoNome);
                item.setQuantidade(novaQuantidade);
                item.setPrecoUnitario(novoPreco);
                return true;
            }
        }
        return false;
    }

    public List<ItemEstoque> listarItens() {
        return new ArrayList<>(itens);
    }

    public ItemEstoque buscarItemPorId(int id) {
        for (ItemEstoque item : itens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}


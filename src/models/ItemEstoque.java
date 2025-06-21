/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author marcos_miller
 */

public class ItemEstoque {
    private static int proximoId = 1;

    private int id;
    private String nome;
    private int quantidade;
    private double precoUnitario;

    public ItemEstoque(String nome, int quantidade, double precoUnitario) {
        this.id = proximoId++;
        this.nome = nome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Quantidade: %d | Preço: R$ %.2f",
                id, nome, quantidade, precoUnitario);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 * Representação de serviços que podem ser associados a OrdemServico.
 *
 * @author camila_barbosa
 */
public class Servico {

    private String codigo;
    private String descricao;
    private double preco;
    private String categoria;
    private List<ItemEstoque> pecas;

    //construtor 
    public Servico(String codigo, String descricao, double preco, String categoria, ItemEstoque pecas) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.pecas = (List<ItemEstoque>) pecas;
    }

    //getters e setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo!");
        }
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ItemEstoque getPecas() {
        return (ItemEstoque) pecas;
    }

    public void setPecas(ItemEstoque pecas) {
        this.pecas = (List<ItemEstoque>) pecas;
    }

    //métodos
    /**
     * Atualiza a descrição do serviço
     *
     * @param novaDescricao Nova descrição que será atribuída
     */
    public void atualizarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    @Override
    public String toString() {
        return "Servico{"
                + "codigo='" + codigo + '\''
                + ", descricao='" + descricao + '\''
                + ", preco=" + preco
                + ", categoria='" + categoria + '\''
                + ", pecas=" + pecas + '\''
                + '}';
    }
}

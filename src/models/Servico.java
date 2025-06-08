/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package models;

/**
 *
 * @author camila_barbosa
 */
public enum Servico{
    TROCA_OLEO("Troca de Óleo", 150.00),
    ALINHAMENTO("Alinhamento", 120.00),
    BALANCEAMENTO("Balanceamento", 100.00),
    TROCA_PASTILHA_FREIO("Troca de Pastilhas de Freio", 200.00),
    TROCA_AMORTECEDOR("Troca de Amortecedor", 350.00),
    REVISAO_FREIOS("Revisão do Sistema de Freios", 180.00),
    DIAGNOSTICO_ELETRICO("Diagnóstico Elétrico", 90.00),
    TROCA_FILTRO_AR("Troca do Filtro de Ar", 80.00),
    TROCA_VELAS("Troca de Velas de Ignição", 130.00),
    LAVAGEM("Lavagem Completa", 60.00);

    private final String descricao;
    private final double preco;

    Servico(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}
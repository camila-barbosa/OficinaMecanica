/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;
import models.enums.TipoPagamento;

/**
 *
 * @author marcos_miller
 */
public class Pagamento {
    //meus atributos
    private static int proximoId = 1;
    
    private int id;
    private LocalDateTime dataHora;
    private Double valor;
    private TipoPagamento tipo; 
    
    //um construtor
    public Pagamento(LocalDateTime dataHora, double valor, TipoPagamento tipo){
    this.id = proximoId++;
    this.dataHora = dataHora;
    this.valor = valor;
    this.tipo = tipo;
    
    }
    
    // Métodos Getters

    public int getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() { // Retorna LocalDateTime
        return dataHora;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    // --- Métodos Setters ---
    // (Considere se todos os atributos devem ser mutáveis)

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataHora(LocalDateTime dataHora) { // Recebe LocalDateTime
        this.dataHora = dataHora;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }
    
    //metodo para finalizar o pagamento
    public String finalizar(Double valor, TipoPagamento tipo){
        this.setValor(valor);
        this.dataHora = LocalDateTime.now();
        this.setTipo(tipo);
        
         return "Pagamento de R$" + String.format("%.2f", valor) +
               " finalizado via " + tipo.getPagamento() +
               " em " + this.dataHora + ". ID: " + this.id; 
    }
    
    //metodo para exibir os detalhes do pagamento
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Pagamento ---");
        System.out.println("ID: " + this.id);
        System.out.println("Valor: R$" + String.format("%.2f", this.valor));
        System.out.println("Data/Hora: " + this.dataHora);
        System.out.println("Forma: " + this.tipo.getPagamento());
        System.out.println("-----------------------------");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import models.enums.TipoPagamento;
import observers.ObservadorPagamento;

/**
 *
 * @author marcos_miller
 */

/**
 * Representa um pagamento realizado na oficina.
 * Agora atua como um 'Subject' no padrão Observer, notificando interessados ao ser finalizado.
 */
public class Pagamento {
    private static int proximoId = 1; // Gerador automático de IDs

    private int id;
    private LocalDateTime dataHora;
    private Double valor;
    private TipoPagamento tipo;
    private OrdemServico ordemServico; // Associação direta com a Ordem de Serviço

    // Esta é a lista dos "assinantes" que querem ser avisados quando este pagamento for finalizado.
    private final List<ObservadorPagamento> observadores;

    /**
     * Construtor para criar um novo pagamento.
     * O pagamento é criado associado a uma Ordem de Serviço, mas ainda não está 'finalizado'.
     * @param ordemServico A OrdemDeServiço à qual este pagamento se refere.
     */
    public Pagamento(OrdemServico ordemServico) {
        this.id = proximoId++; // Atribui um ID único
        this.ordemServico = ordemServico; // Define a OS associada
        this.observadores = new ArrayList<>(); // Inicializa a lista de assinantes VAZIA
    }

    // --- Métodos do Padrão Observer (Adicionados à sua classe existente) ---

    /**
     * Adiciona um assinante à lista. Este assinante será notificado quando o pagamento for finalizado.
     * @param obs O assinante (quem vai ser avisado).
     */
    public void adicionarObservador(ObservadorPagamento obs) {
        if (!observadores.contains(obs)) { // Evita adicionar o mesmo assinante várias vezes
            observadores.add(obs);
            System.out.println("Pagamento ID " + this.id + ": Assinante adicionado.");
        }
    }

    /**
     * Remove um assinante da lista. Ele não será mais notificado.
     * @param obs O assinante a ser removido.
     */
    public void removerObservador(ObservadorPagamento obs) {
        observadores.remove(obs);
        System.out.println("Pagamento ID " + this.id + ": Assinante removido.");
    }

    /**
     * Envia a notícia (notifica) todos os assinantes registrados sobre a finalização deste pagamento.
     * Este método é chamado internamente pelo método 'finalizar()'.
     */
    public void notificarObservadores() {
        System.out.println("Pagamento ID " + this.id + ": Enviando notícia aos assinantes sobre a finalização...");
        for (ObservadorPagamento obs : observadores) {
            obs.notificarAssinantes(this); // Chamada alterada para o novo nome do método na interface
        }
    }

    // --- Métodos Getters (para acessar os dados do Pagamento) ---
    public int getId() { return id; }
    public Double getValor() { return valor; }
    public LocalDateTime getDataHora() { return dataHora; }
    public TipoPagamento getTipo() { return tipo; }
    public OrdemServico getOrdemServico() { return ordemServico; } // Getter para a OS associada

    // --- Métodos Setters (para alterar os dados do Pagamento, se permitido) ---
    public void setId(int id) { this.id = id; }
    public void setValor(Double valor) { this.valor = valor; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setTipo(TipoPagamento tipo) { this.tipo = tipo; }
    public void setOrdemServico(OrdemServico ordemDeServico) { this.ordemServico = ordemDeServico; }


    /**
     * Finaliza o pagamento, definindo seu valor, data/hora e tipo.
     * Após a finalização, ENVIA A NOTÍCIA (notifica) a todos os assinantes registrados.
     * @param valorFinal O valor final pago.
     * @param tipoFinal O tipo de pagamento (DINHEIRO, CARTAO_CREDITO, etc.).
     * @return Uma mensagem de confirmação da finalização.
     */
    public String finalizar(Double valorFinal, TipoPagamento tipoFinal) {
        this.setValor(valorFinal); // Define o valor do pagamento
        this.dataHora = LocalDateTime.now(); // Define a data/hora como o momento atual
        this.setTipo(tipoFinal); // Define o tipo de pagamento

        String mensagem = "Pagamento de R$" + String.format("%.2f", valorFinal) +
                " finalizado via " + tipoFinal + // Usa o enum diretamente para a string
                " em " + this.dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                ". ID: " + this.id +
                (this.ordemServico != null ? ", OS: " + this.ordemServico.getCodigo() : ""); // Inclui OS se houver

        System.out.println(mensagem); // Exibe a mensagem no console

        // O ponto chave do Observer: Avisar quem está interessado!
        notificarObservadores();

        return mensagem;
    }

    /**
     * Exibe os detalhes completos do pagamento.
     */
    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Pagamento ---");
        System.out.println("ID: " + this.id);
        System.out.println("Valor: R$" + String.format("%.2f", this.valor));
        System.out.println("Data/Hora: " + (this.dataHora != null ? this.dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "N/A"));
        System.out.println("Forma: " + this.tipo);
        System.out.println("OS Relacionada: " + (this.ordemServico != null ? this.ordemServico.getCodigo() : "N/A"));
        System.out.println("-----------------------------");
    }
}
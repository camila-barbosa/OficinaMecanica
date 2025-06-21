/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package observers;

import models.Pagamento;

/**
 *
 * @author marcos_miller
 */

/**
 * Esta interface define o "contrato" para qualquer classe que queira "assinar"
 * (observar) um objeto Pagamento.
 * Todas as classes que implementam esta interface concordam em ter um método
 * que será chamado quando o Pagamento for finalizado.
 */
public interface ObservadorPagamento {
    /**
     * Método chamado pelo Subject (Pagamento) para enviar a notícia aos assinantes.
     * Quando um Pagamento é finalizado, este método é invocado em todos os assinantes registrados.
     *
     * @param pagamento O objeto Pagamento que foi finalizado.
     */
    void notificarAssinantes(Pagamento pagamento);
}

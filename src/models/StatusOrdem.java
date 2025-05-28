/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package models;

/**
 *Enumeração que representa os possíveis status de uma ordem de serviço na oficina.
 * @author camila_barbosa
 */
public enum StatusOrdem {
    EM_DIAGNOSTICO, //Veículo está sendo avaliado 
    AGUARDANDO_LIBERACAO, //Aguardando aprovação do serviço pelo cliente
    EM_EXECUCAO, //Serviço está em andamento
    AGUARDANDO_PAGAMENTO, //Serviço foi concluido, aguardando o pagamento
    FINALIZADA, //Ordem de serviço foi finalizada
    CANCELADA;  //Ordem de serviço cancelada
}

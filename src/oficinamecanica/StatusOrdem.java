/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package oficinamecanica;

/**
 *Esta enumeração representa os possíveis status de uma ordem de serviço e do veículo.
 * @author barbo
 */
public enum StatusOrdem {
    /** O veículo está em fase de dignóstico */
    EM_DIAGNOSTICO,
    /** O veículo está aguardando liberação para execução do serviço */
    AGUARDANDO_LIBERACAO,
    /** O serviço está sendo executado*/
    EM_EXECUCAO,
    /**O serviço foi concluído e está aguardando pagamento */
    AGUARDANDO_PAGAMENTO,
    /**A ordem de serviço foi finalizada com sucesso*/
    FINALIZADA,
    /** A ordem de srviço foi cancelada*/
    CANCELADA;
}

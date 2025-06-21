/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package models.enums;

/**
 *
 * @author camila_barbosa
 */
public enum StatusOrdem {
    AGUARDANDO_DIAGNOSTICO("Aguardando Diagnóstico"),
    EM_DIAGNOSTICO("Em Diagnóstico"),
    AGUARDANDO_LIBERACAO("Aguardando Liberação Cliente"),
    EM_EXECUCAO("Em Execução"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada");

    private final String descricao;

    private StatusOrdem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
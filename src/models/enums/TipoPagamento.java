/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.enums;

/**
 *
 * @author marcos_miller
 */
public enum TipoPagamento {
    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartao de Credito"),
    CARTAO_DEBITO("Cartao de Debito"),
    PIX("Pix");
    
    private final String pagamento;
    
    private TipoPagamento(String pagamento){
        this.pagamento = pagamento;
    }
    
    public String getPagamento(){
    return pagamento;
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.enums;

/**
 *
 * @author marcos_miller
 */
public enum TipoUsuario {
    ATENDENTE("Atendente"),
    MECANICO("Mecânico"), 
    GERENTE("Gerente");

    private final String descricao;

    private TipoUsuario(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}

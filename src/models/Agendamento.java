/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;
import models.enums.StatusAgendamento;

/**
 *
 * @author marcos_miller
 */
public class Agendamento {
    //criando meusatributos
    private LocalDateTime dataHora;
    private Cliente cliente;
    private Veiculo veiculo;
    private StatusAgendamento status;
    
    //construtor
    public Agendamento(LocalDateTime dataHora, Cliente cliente, Veiculo veiculo){
    this.dataHora = dataHora;
    this.cliente = cliente;
    this.veiculo = veiculo;
    this.status = StatusAgendamento.PENDENTE;
    }
    
    //métodos
    
    //reagendar
    public boolean reagendar(LocalDateTime novaDataHora){
        if(status == StatusAgendamento.CANCELADO || status == StatusAgendamento.CONFIRMADO){
            return false;
        }
        this.dataHora = novaDataHora;
        return true;
    }
    
    //cancelar
    public void cancelar(){
    this.status = StatusAgendamento.CANCELADO;
    }
    
    //confirmar
    public void confirmar(){
    this.status = StatusAgendamento.CONFIRMADO;
    }
    
    //ver se ta vencido
    public boolean estaVencid(){
        return LocalDateTime.now().isAfter(this.dataHora) &&
                (status == StatusAgendamento.PENDENTE || status == StatusAgendamento.CONFIRMADO);
    }
    
    //ver os detalhes
    
}

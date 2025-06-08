/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author barbo
 */
public class Mecanico extends Usuario {

    private String especialidade;
    private boolean disponivel;

    //construtor 
    public Mecanico(String nome, String cpf, String endereco, String email, String telefone, String senha, String especialidade, boolean disponivel) {
        super(nome, cpf, endereco, email, telefone, senha);
        this.disponivel = disponivel;
        this.especialidade = especialidade;
    }

    //getters e setters
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    //métodos
    //IRÁ MUDAR POIS IOREMOS USAR JSON, MUIDAR DIAGRAMA DE CLASSES
    /**
     *
     * @param veiculo
     * @param cliente
     * @return
     */
    public OrdemServico realizarDiagnostico(Veiculo veiculo, Cliente cliente) {
        if (!this.disponivel) {
            throw new IllegalStateException("Mecânico não disponível");
        }

         ///criar enum 

        return new OrdemServico(
                "OS-" + System.currentTimeMillis(),
                new Date(),
                0.0,
                veiculo,
                cliente,
                this,
                StatusOrdem.EM_DIAGNOSTICO,
                servicos
        );
    }

    /**
     *
     * @param ordem
     */
    public void executarOrdemServico(OrdemServico ordem) {
        //validações inicias
        if (!this.disponivel) {
            throw new IllegalStateException("Mecânico não está disonível para executar ordem de serviço");
        }
        if (ordem.getStatus() != StatusOrdem.AGUARDANDO_LIBERACAO) {
            throw new IllegalStateException("Ordem precisa estar com status aguardando liberação para ser executada");
        }
        //atualiza status do mecânico e da ordem
        this.setDisponivel(false);
        ordem.setStatus(StatusOrdem.EM_EXECUCAO);}

      
}

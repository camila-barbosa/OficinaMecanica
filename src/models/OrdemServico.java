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
public class OrdemServico {

    private String codigo;
    private Date data;
    private Double precoTotal;
    private Veiculo veiculo;
    private Cliente cliente;
    private Mecanico mecanicoResponsavel;
    private StatusOrdem status;
    private final List<Servico> servicos;  //private final foi sugerido pela IDE, perguntar pelli.

    //construtor
    public OrdemServico(String codigo, Date data, double precoTotal, Veiculo veiculo,
            Cliente cliente, Mecanico mecanicoResponsavel, StatusOrdem status, List<Servico> servicos) {
        this.codigo = codigo;
        this.data = data;
        this.precoTotal = precoTotal;
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.status = status;
        this.servicos = new ArrayList<> (servicos);
    }

    //getters e setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    //métodos
    /**
     * Calcula o preço total somando os serviços
     * @return retorna o valor total dos serviços realizados
     */
    public double calcularTotal() {
        this.precoTotal = servicos.stream() //converte a lista em stream
                .mapToDouble(Servico::getPreco) //extrai precos
                .sum(); //soma os valores
        return this.precoTotal; //total dps de calculado
    }

    /**
     * Adiciona serviços a serem realizados na ordem de serviço
     *
     * @param servico serviço que será adicionado
     */
    public void adicionarServico(Servico servico) {
        servicos.add(servico);
        calcularTotal(); //atualiza preço total
    }

    /**
     * remove servico
     *
     * @param servico servico que será removido
     */
    public void removerServico(Servico servico) {
        servicos.remove(servico);
        calcularTotal(); //atualiza preço total
    }

    /**
     * Altera o status da ordem de serviço
     *
     * @param novoStatus status para qual será atualizado
     */
    public void alterarStatus(StatusOrdem novoStatus) {
        this.status = novoStatus;
    }
    
    @Override
public String toString() {
    return "OrdemDeServico{" +
           "codigo='" + codigo + '\'' +
           ", status=" + status +
           ", precoTotal=" + precoTotal +
           ", veiculo=" + veiculo +
           ", cliente=" + cliente +
           ", qtdServicos=" + servicos.size() +
           '}';
}
}
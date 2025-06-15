/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import observers.IObservavelOrdemServico; // Importa a interface do Publicador
import observers.IObservadorOrdemServico; 

/**
 * Representa uma Ordem de Serviço na oficina.
 * Agora atua como um 'Subject' no padrão Observer, notificando interessados
 * sobre mudanças de status.
 *
 * @author barbo
 */
public class OrdemServico implements IObservavelOrdemServico { // OrdemServico AGORA IMPLEMENTA a interface de Publicador

    private String codigo;
    private Date data;
    private Double precoTotal;
    private Veiculo veiculo;
    private Cliente cliente;
    private Mecanico mecanicoResponsavel;
    private StatusOrdem status;
    private final List<Servico> servicos;

    // NOVO ATRIBUTO: Lista para armazenar os observadores desta OrdemServico
    private final List<IObservadorOrdemServico> observadores;

    // Construtor original, modificado para inicializar a lista de observadores
    public OrdemServico(String codigo, Date data, double precoTotal, Veiculo veiculo,
            Cliente cliente, Mecanico mecanicoResponsavel, StatusOrdem status, List<Servico> servicos) {
        this.codigo = codigo;
        this.data = data;
        this.precoTotal = precoTotal;
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.status = status;
        this.servicos = new ArrayList<>(servicos);
        this.observadores = new ArrayList<>(); // Inicializa a lista de observadores
    }

    // --- MÉTODOS DO PADRÃO OBSERVER (Implementando IObservavelOrdemServico) ---

    @Override // BOA PRÁTICA: Indica que este método está sobrescrevendo um método da interface
    public void adicionarObservador(IObservadorOrdemServico obs) {
        if (!observadores.contains(obs)) { // Evita adicionar o mesmo observador múltiplas vezes
            observadores.add(obs);
            System.out.println("[OrdemServico " + this.codigo + "] Assinante de OS adicionado.");
        }
    }

    @Override // BOA PRÁTICA: Indica que este método está sobrescrevendo um método da interface
    public void removerObservador(IObservadorOrdemServico obs) {
        observadores.remove(obs);
        System.out.println("[OrdemServico " + this.codigo + "] Assinante de OS removido.");
    }

    @Override // BOA PRÁTICA: Indica que este método está sobrescrevendo um método da interface
    public void notificarObservadores() { // Este método não precisa de parâmetros aqui, conforme definido na interface
        System.out.println("[OrdemServico " + this.codigo + "] Enviando notícia de status aos assinantes...");
        for (IObservadorOrdemServico obs : observadores) {
            obs.notificarStatusOrdem(this); // Passa a si mesma (a OrdemServico) para o assinante.
        }
    }

    // --- MÉTODOS GETTERS E SETTERS (ORIGINAIS) ---
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
        // O método 'alterarStatus' é quem vai chamar 'notificarObservadores()', não o 'setStatus'
    }
    
    // NOVO GETTER: Para a lista de serviços (útil para Observers se precisarem detalhes de serviço)
    public List<Servico> getServicos() { 
        return new ArrayList<>(servicos); // Retorna uma cópia para proteger a lista interna
    }

    // --- MÉTODOS DE NEGÓCIO (ORIGINAIS, COM MODIFICAÇÃO NO alterarStatus) ---

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
        System.out.println("[OrdemServico " + this.codigo + "] Serviço '" + servico.getDescricao() + "' adicionado.");
    }

    /**
     * remove servico
     *
     * @param servico servico que será removido
     */
    public void removerServico(Servico servico) {
        servicos.remove(servico);
        calcularTotal(); //atualiza preço total
        System.out.println("[OrdemServico " + this.codigo + "] Serviço '" + servico.getDescricao() + "' removido.");
    }

    /**
     * Altera o status da ordem de serviço.
     * Após a alteração, NOTIFICA todos os assinantes registrados sobre a mudança.
     *
     * @param novoStatus status para qual será atualizado
     */
    public void alterarStatus(StatusOrdem novoStatus) { // Este método agora DISPARA a notificação
        if (this.status != novoStatus) { // Notifica apenas se o status realmente mudou
            this.status = novoStatus;
            System.out.println("\n[OrdemServico " + this.codigo + "] Status alterado para -> " + novoStatus);
            notificarObservadores(); // CHAMA OS OBSERVADORES AQUI!
        } else {
            System.out.println("\n[OrdemServico " + this.codigo + "] Status já é " + novoStatus + ". Nenhuma alteração/notificação.");
        }
    }
    
    @Override
    public String toString() {
        return "OrdemServico{" +
                "codigo='" + codigo + '\'' +
                ", status=" + status +
                ", precoTotal=" + precoTotal +
                ", veiculo=" + (veiculo != null ? veiculo.getPlaca() : "N/A") +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") + // Use getNome() ou ajuste conforme seu Cliente
                ", qtdServicos=" + servicos.size() +
                '}';
    }
}
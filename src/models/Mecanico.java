/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import models.enums.StatusOrdem;
import models.enums.TipoUsuario;

/**
 * Classe que representa um mecânico da oficina.
 * @author barbo
 */
public class Mecanico extends Usuario {
    private String especialidade;
    private boolean disponivel;
    private List<Servico> servicosQualificados;

    /**
     * Construtor da classe Mecanico.
     * @param nome Nome completo do mecânico
     * @param cpf CPF do mecânico (11 dígitos)
     * @param endereco Endereço completo
     * @param email Email válido
     * @param telefone Telefone para contato
     * @param senha Senha de acesso ao sistema
     * @param especialidade Área de especialização do mecânico (ex: "Motor", "Freios")
     */
    public Mecanico(String nome, String cpf, String endereco, String email,
                    String telefone, TipoUsuario tipo, String senha, String especialidade) { // TipoUsuario no construtor
        super(nome, cpf, endereco, email, telefone, tipo, senha); // Passa o TipoUsuario para o super construtor
        this.especialidade = Objects.requireNonNull(especialidade, "Especialidade não pode ser nula");
        this.disponivel = true; // Inicia disponível
        this.servicosQualificados = new ArrayList<>(); // Inicializa a lista de qualificações
    }

    // --- Getters e Setters ---
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) { // Adicionar setter para disponivel
        this.disponivel = disponivel;
    }

    /**
     * Adiciona uma qualificação que o mecânico pode realizar.
     * @param servico Serviço a ser adicionado nas qualificações.
     */
    public void adicionarQualificacao(Servico servico) {
        Objects.requireNonNull(servico, "Serviço não pode ser nulo para qualificação.");
        if (!servicosQualificados.contains(servico)) { // Usa o equals/hashCode de Servico
            servicosQualificados.add(servico);
            System.out.println("Mecânico " + getNome() + " qualificado para: " + servico.getDescricao());
        } else {
            System.out.println("Mecânico " + getNome() + " já qualificado para: " + servico.getDescricao());
        }
    }

    /**
     * Verifica se o mecânico está qualificado para executar um serviço.
     * @param servico Serviço que será verificado.
     * @return true se o mecânico é qualificado, se não, retorna false.
     */
    public boolean podeExecutarServico(Servico servico) {
        return servicosQualificados.contains(servico);
    }

    /**
     * Inicia um diagnóstico técnico e gera uma nova ordem de serviço.
     * @param veiculo O veículo que será diagnosticado.
     * @param cliente O cliente proprietário do veículo.
     * @return Irá retornar uma nova ordem de serviço.
     * @throws IllegalStateException Se o mecânico não estiver disponível.
     */
    public OrdemServico realizarDiagnostico(Veiculo veiculo, Cliente cliente) {
        Objects.requireNonNull(veiculo, "Veículo não pode ser nulo para diagnóstico.");
        Objects.requireNonNull(cliente, "Cliente não pode ser nulo para diagnóstico.");

        if (!this.disponivel) {
            throw new IllegalStateException("Mecânico " + getNome() + " não está disponível para diagnóstico.");
        }

        // Cria lista de serviços inicial com diagnóstico padrão
        List<Servico> servicosIniciais = new ArrayList<>();
        // Preço do diagnóstico como BigDecimal
        Servico diagnostico = new Servico(
            "DIAG-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm")), // Código único
            "Diagnóstico Inicial do Veículo",
            new BigDecimal("50.00"), // ALTERADO: Preço como BigDecimal
            "Diagnóstico",
            0 // Sem peça associada ao diagnóstico (ID 0 ou null, dependendo da sua regra)
        );
        servicosIniciais.add(diagnostico);

        // Preço total inicial (apenas o diagnóstico), usando BigDecimal
        // O calcularTotal da OrdemServico já faz isso automaticamente.
        // Não é necessário calcular aqui e passar para o construtor da OS.
        // OrdemServico vai calcular por conta própria.

        OrdemServico os = new OrdemServico(
            gerarCodigoOS(),
            LocalDateTime.now(),
            veiculo.getId(), // ALTERADO: Passa o ID do Veículo
            cliente.getId(), // ALTERADO: Passa o ID do Cliente
            this.getId(), // ALTERADO: Passa o ID deste Mecânico
            StatusOrdem.EM_DIAGNOSTICO // Status inicial da OS
        );
        
        // Adiciona os serviços iniciais à OS (ela vai calcular o total)
        for (Servico s : servicosIniciais) {
            os.adicionarServico(s);
        }

        this.disponivel = false; // Mecânico fica ocupado com a OS
        System.out.println("Mecânico " + getNome() + " iniciou diagnóstico para veículo " + veiculo.getPlaca() + ". OS: " + os.getCodigo());
        return os;
    }

    /**
     * Executa um serviço em uma ordem de serviço que já existe.
     * @param ordem Ordem de serviço a ser executada.
     * @param servico Serviço que será realizado.
     * @throws IllegalStateException Se o mecânico não estiver disponível, não qualificado ou a OS não estiver no status correto.
     */
    public void executarServico(OrdemServico ordem, Servico servico) {
        Objects.requireNonNull(ordem, "Ordem de Serviço não pode ser nula.");
        Objects.requireNonNull(servico, "Serviço não pode ser nulo.");

        if (!this.disponivel) {
            throw new IllegalStateException("Mecânico " + getNome() + " não está disponível.");
        }
        if (!podeExecutarServico(servico)) {
            throw new IllegalStateException("Mecânico " + getNome() + " não qualificado para este serviço: " + servico.getDescricao());
        }
        // Verificar se a ordem está em um status que permite execução (ex: AGUARDANDO_LIBERACAO ou EM_DIAGNOSTICO)
        // O seu diagrama diz AGUARDANDO_LIBERACAO. Vamos manter isso.
        if (!ordem.getStatus().equals(StatusOrdem.AGUARDANDO_LIBERACAO)) {
            throw new IllegalStateException("Ordem de Serviço " + ordem.getCodigo() + " não está pronta para execução. Status atual: " + ordem.getStatus().getDescricao());
        }

        // Adiciona o serviço à ordem (a ordem se encarrega de recalcular o total)
        ordem.adicionarServico(servico);
        // Altera o status da OS para EM_EXECUCAO (ela notifica os observadores)
        ordem.alterarStatus(StatusOrdem.EM_EXECUCAO);
        this.disponivel = false; // Mecânico fica ocupado
        System.out.println("Mecânico " + getNome() + " iniciou execução do serviço '" + servico.getDescricao() + "' na OS " + ordem.getCodigo());
    }

    /**
     * Gera um código único para cada OS.
     * @return Retorna código no formato "OS-{timestamp}-{3primeirosDigitosCPF}"
     */
    private String gerarCodigoOS() {
        return "OS-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) +
               "-" + this.getCpf().substring(0, 3);
    }

    @Override
    public String toString() {
        return String.format("Mecânico [ID: %d | Nome: %s] - Especialidade: %s - %s - %d qualificações",
            getId(), // Usando o ID herdado de Usuario
            getNome(), // Usando o Nome herdado de Usuario
            especialidade,
            disponivel ? "Disponível" : "Ocupado",
            servicosQualificados.size());
    }
}
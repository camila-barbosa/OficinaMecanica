/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author camila_barbosa
 */
public class Servico {

    // NOVO: Adicionar ID único para Servico
    public static int proximoId = 1;

    private int id; // ID único do serviço
    private String codigo;
    private String descricao;
    private BigDecimal preco; // ALTERADO: De Double para BigDecimal
    private String categoria;
    private int idItemEstoquePeca; // ALTERADO: De ItemEstoque para int (referência por ID)

    /**
     * Construtor para criar um novo Serviço.
     * @param codigo O código único do serviço.
     * @param descricao A descrição detalhada do serviço.
     * @param preco O preço do serviço.
     * @param categoria A categoria do serviço.
     * @param idItemEstoquePeca O ID da peça de estoque associada a este serviço (0 se não houver peça).
     */
    public Servico(String codigo, String descricao, BigDecimal preco, String categoria, int idItemEstoquePeca) {
        this.id = proximoId++; // Atribui ID único e incrementa
        this.codigo = Objects.requireNonNull(codigo, "Código do serviço não pode ser nulo.");
        this.descricao = Objects.requireNonNull(descricao, "Descrição do serviço não pode ser nula.");
        setPreco(preco); // Usa o setter para validação de preço
        this.categoria = Objects.requireNonNull(categoria, "Categoria do serviço não pode ser nula.");
        this.idItemEstoquePeca = idItemEstoquePeca; // Guarda o ID da peça
    }

    /**
     * Construtor para Servico sem peça associada.
     * @param codigo O código único do serviço.
     * @param descricao A descrição detalhada do serviço.
     * @param preco O preço do serviço.
     * @param categoria A categoria do serviço.
     */
    public Servico(String codigo, String descricao, BigDecimal preco, String categoria) {
        // Chama o construtor principal com idItemEstoquePeca = 0 (ou outro valor padrão para "sem peça")
        this(codigo, descricao, preco, categoria, 0); 
    }

    // NOVO: Construtor para uso pelo Gson ao desserializar (reconstruir o objeto do JSON)
    public Servico(int id, String codigo, String descricao, BigDecimal preco, String categoria, int idItemEstoquePeca) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.idItemEstoquePeca = idItemEstoquePeca;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = Objects.requireNonNull(codigo, "Código do serviço não pode ser nulo.");
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { // Renomeado para setDescricao para consistência
        this.descricao = Objects.requireNonNull(descricao, "Descrição do serviço não pode ser nula.");
    }

    public BigDecimal getPreco() { // ALTERADO: Retorna BigDecimal
        return preco;
    }

    public void setPreco(BigDecimal preco) { // ALTERADO: Recebe BigDecimal
        Objects.requireNonNull(preco, "Preço do serviço não pode ser nulo.");
        if (preco.compareTo(BigDecimal.ZERO) < 0) { // Comparação com BigDecimal.ZERO
            throw new IllegalArgumentException("Preço do serviço não pode ser negativo.");
        }
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = Objects.requireNonNull(categoria, "Categoria do serviço não pode ser nula.");
    }

    // Getter para o ID da peça de estoque
    public int getIdItemEstoquePeca() {
        return idItemEstoquePeca;
    }

    // Setter para o ID da peça de estoque
    public void setIdItemEstoquePeca(int idItemEstoquePeca) {
        this.idItemEstoquePeca = idItemEstoquePeca;
    }

    @Override
    public String toString() {
        // Usar format() para BigDecimal, e exibir a referência da peça por ID
        return String.format("Serviço [Código: %s | Descrição: %s | Preço: R$ %.2f | Categoria: %s %s]",
                codigo,
                descricao,
                preco, // BigDecimal já tem seu próprio toString ou pode ser formatado
                categoria,
                (idItemEstoquePeca != 0) ? "| Peça ID: " + idItemEstoquePeca : "" // Exibe ID da peça
        );
    }
    
    // É uma boa prática sobrescrever equals e hashCode se você for usar objetos Servico
    // em coleções que dependem da igualdade (ex: removerServico de uma List).
    // Usaremos o ID para identificar unicidade.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return id == servico.id; // Serviços são iguais se tiverem o mesmo ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash code baseado no ID
    }
}
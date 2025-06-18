package com.controladordeestoque.model;

import java.util.Date;

/**
 * Representa um registro de movimentação de estoque (uma entrada ou uma saída).
 * Esta classe é usada para auditoria e para manter o histórico de todas as
 * alterações na quantidade de um produto.
 */
public class MovimentoEstoque {

    /**
     * O identificador único do registro de movimento.
     */
    private int id;

    /**
     * O produto que foi movimentado.
     */
    private Produto produto;

    /**
     * A quantidade de itens do produto que foi movimentada.
     */
    private int quantidade;

    /**
     * O tipo de movimento realizado. Espera-se "ENTRADA" ou "SAIDA".
     */
    private String tipo;

    /**
     * A data e a hora em que o movimento foi registrado.
     */
    private Date data;

    /**
     * Construtor padrão.
     * Inicializa uma nova instância de MovimentoEstoque com valores padrão,
     * incluindo um produto vazio e a data/hora atual.
     */
    public MovimentoEstoque() {
        this.id = 0;
        this.produto = new Produto(); // Produto vazio
        this.quantidade = 0;
        this.tipo = "";
        this.data = new Date(); // Data atual
    }

    /**
     * Construtor completo.
     * Cria uma nova instância de MovimentoEstoque com os valores especificados.
     *
     * @param id         O ID do movimento.
     * @param produto    O {@link Produto} associado ao movimento.
     * @param quantidade A quantidade de itens movimentada.
     * @param tipo       O tipo de movimento ("ENTRADA" ou "SAIDA").
     * @param data       A data em que o movimento ocorreu.
     */
    public MovimentoEstoque(int id, Produto produto, int quantidade, String tipo, Date data) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

    // --- Getters e Setters ---

    /**
     * Retorna o ID do movimento de estoque.
     *
     * @return O ID do movimento.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do movimento de estoque.
     *
     * @param id O novo ID do movimento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o produto associado a este movimento.
     *
     * @return O objeto {@link Produto}.
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado a este movimento.
     *
     * @param produto O novo objeto {@link Produto}.
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a quantidade de itens movimentados.
     *
     * @return A quantidade.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade de itens movimentados.
     *
     * @param quantidade A nova quantidade.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o tipo de movimento.
     *
     * @return A string representando o tipo (ex: "ENTRADA").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo de movimento.
     *
     * @param tipo A nova string de tipo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna a data em que o movimento foi registrado.
     *
     * @return A data do movimento.
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data do movimento.
     *
     * @param data A nova data do movimento.
     */
    public void setData(Date data) {
        this.data = data;
    }
}
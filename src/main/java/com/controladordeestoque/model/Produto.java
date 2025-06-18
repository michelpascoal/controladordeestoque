package com.controladordeestoque.model;

import java.util.Date;

/**
 * Representa a entidade Produto, a classe central do sistema de estoque.
 * Contém todos os atributos que definem um produto, como nome, quantidade,
 * preço e informações de controle de estoque.
 */
public class Produto {

    /**
     * O identificador único do produto (chave primária).
     */
    private int id;
    
    /**
     * O nome comercial do produto.
     */
    private String nome;
    
    /**
     * Uma descrição detalhada do produto.
     */
    private String descricao;
    
    /**
     * A quantidade atual do produto disponível em estoque.
     */
    private int quantidade;
    
    /**
     * A data de validade do produto. Pode ser nulo se não aplicável.
     */
    private Date validade;
    
    /**
     * A categoria à qual o produto pertence.
     */
    private Categoria categoria;
    
    /**
     * O preço de venda de uma única unidade do produto.
     */
    private double precoUnitario;
    
    /**
     * A quantidade mínima recomendada de itens em estoque.
     */
    private int quantidadeMinima;
    
    /**
     * A quantidade máxima recomendada de itens em estoque.
     */
    private int quantidadeMaxima;

    /**
     * Construtor padrão.
     * Inicializa uma nova instância de Produto com valores padrão.
     */
    public Produto() {
        this.id = 0;
        this.nome = "";
        this.descricao = "";
        this.quantidade = 0;
        this.validade = null; // É mais seguro iniciar como nulo
        this.categoria = new Categoria(); // Nova categoria vazia
        this.precoUnitario = 0.0;
        this.quantidadeMinima = 0;
        this.quantidadeMaxima = 0;
    }

    /**
     * Construtor completo.
     * Cria uma nova instância de Produto com todos os atributos especificados.
     *
     * @param id O ID do produto.
     * @param nome O nome do produto.
     * @param descricao A descrição do produto.
     * @param quantidade A quantidade inicial em estoque.
     * @param validade A data de validade.
     * @param categoria A {@link Categoria} do produto.
     * @param precoUnitario O preço por unidade.
     * @param quantidadeMinima O limite mínimo para o estoque.
     * @param quantidadeMaxima O limite máximo para o estoque.
     */
    public Produto(int id, String nome, String descricao, int quantidade, Date validade, Categoria categoria, double precoUnitario, int quantidadeMinima, int quantidadeMaxima) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.validade = validade;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
    }

    // --- Getters e Setters ---

    /**
     * Retorna o ID do produto.
     * @return O ID do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do produto.
     * @param id O novo ID do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     * @return O nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     * @param nome O novo nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição do produto.
     * @return A descrição do produto.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     * @param descricao A nova descrição do produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a quantidade em estoque.
     * @return A quantidade atual.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade em estoque.
     * @param quantidade A nova quantidade.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna a data de validade.
     * @return A data de validade.
     */
    public Date getValidade() {
        return validade;
    }

    /**
     * Define a data de validade.
     * @param validade A nova data de validade.
     */
    public void setValidade(Date validade) {
        this.validade = validade;
    }

    /**
     * Retorna o preço unitário.
     * @return O preço por unidade.
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /**
     * Define o preço unitário.
     * @param precoUnitario O novo preço por unidade.
     */
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    /**
     * Retorna a quantidade mínima de estoque.
     * @return O limite mínimo de estoque.
     */
    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    /**
     * Define a quantidade mínima de estoque.
     * @param quantidadeMinima O novo limite mínimo de estoque.
     */
    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
    
    /**
     * Retorna a quantidade máxima de estoque.
     * @return O limite máximo de estoque.
     */
    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    /**
     * Define a quantidade máxima de estoque.
     * @param quantidadeMaxima O novo limite máximo de estoque.
     */
    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    /**
     * Retorna a categoria do produto.
     * @return O objeto {@link Categoria}.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     * @param categoria O novo objeto {@link Categoria}.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    /**
     * @deprecated Este método é um stub gerado e não está implementado.
     * Para definir o preço, utilize o método {@link #setPrecoUnitario(double)}. Considere remover este método.
     */
    @Deprecated
    public void setPreco(double parseDouble) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
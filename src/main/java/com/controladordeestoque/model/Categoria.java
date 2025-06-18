package com.controladordeestoque.model;

/**
 * Representa a entidade Categoria no sistema.
 * Esta classe é um modelo simples (POJO) para encapsular os dados de uma categoria de produto.
 */
public class Categoria {

    /**
     * O identificador único da categoria, geralmente a chave primária no banco de dados.
     */
    private int id;

    /**
     * O nome descritivo da categoria (ex: "Eletrônicos", "Alimentos", "Limpeza").
     */
    private String nome;

    /**
     * Construtor padrão.
     * Inicializa uma nova instância de Categoria com valores padrão (id 0, nome vazio).
     */
    public Categoria() {
        this.id = 0;
        this.nome = "";
    }

    /**
     * Construtor completo.
     * Cria uma nova instância de Categoria com os valores especificados.
     *
     * @param id   O identificador único da categoria.
     * @param nome O nome da categoria.
     */
    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // --- Getters e Setters ---

    /**
     * Retorna o ID da categoria.
     *
     * @return O ID da categoria.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da categoria.
     *
     * @param id O novo ID para a categoria.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return O nome da categoria.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da categoria.
     *
     * @param nome O novo nome para a categoria.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
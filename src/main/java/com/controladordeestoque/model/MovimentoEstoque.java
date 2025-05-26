package com.controladordeestoque.model;

import java.util.Date;

public class MovimentoEstoque {

    private int id;
    private Produto produto;
    private int quantidade;
    private String tipo; // "ENTRADA" ou "SAÍDA"
    private Date data;

    // Construtor padrão com valores iniciais
    public MovimentoEstoque() {
        this.id = 0;
        this.produto = new Produto();  // Produto vazio
        this.quantidade = 0;
        this.tipo = "";
        this.data = new Date();  // Data atual
    }

    // Construtor completo
    public MovimentoEstoque(int id, Produto produto, int quantidade, String tipo, Date data) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

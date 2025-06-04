package com.controladordeestoque.model;

import java.util.Date;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private int quantidade;
    private Date validade;
    private Categoria categoria;
    private double precoUnitario;
    private int quantidadeMinima;
    private int quantidadeMaxima;

    // Construtor padrão com valores iniciais
    public Produto() {
        this.id = 0;
        this.nome = "";
        this.descricao = "";
        this.quantidade = 0;
        this.validade = new Date(); // define como data atual
        this.categoria = new Categoria(); // nova categoria vazia
        this.precoUnitario = 0.0; // adiciona valor padrão ao preço
    }

    // Construtor completo
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

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }
    
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    
    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }
}
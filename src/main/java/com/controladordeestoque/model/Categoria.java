package com.controladordeestoque.model;

public class Categoria {

    private int id;
    private String nome;

    // Construtor padrão com valores iniciais
    public Categoria() {
        this.id = 0;
        this.nome = "";
    }

    // Construtor completo
    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e setters
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
}
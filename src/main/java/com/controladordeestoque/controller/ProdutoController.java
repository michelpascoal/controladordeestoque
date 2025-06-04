package com.controladordeestoque.controller;

import com.controladordeestoque.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private List<Produto> listaProdutos = new ArrayList<>();

    public boolean cadastrarProduto(Produto produto) {
        if (produto != null && produto.getNome().length() >= 2) {
            listaProdutos.add(produto);
            return true;
        }
        return false;
    }

    public List<Produto> listarProdutos() {
        return listaProdutos;
    }

    public boolean removerProduto(int id) {
        return listaProdutos.removeIf(p -> p.getId() == id);
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto p : listaProdutos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Método auxiliar para atualizar um produto existente (simples)
    public boolean atualizarProduto(Produto novoProduto) {
        for (int i = 0; i < listaProdutos.size(); i++) {
            if (listaProdutos.get(i).getId() == novoProduto.getId()) {
                listaProdutos.set(i, novoProduto);
                return true;
            }
        }
        return false;
    }
}

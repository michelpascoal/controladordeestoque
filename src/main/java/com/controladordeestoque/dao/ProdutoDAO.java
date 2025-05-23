package com.controladordeestoque.dao;

import com.controladordeestoque.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Simulando um banco de dados com lista em memória
    private static List<Produto> listaProdutos = new ArrayList<>();

    public boolean salvar(Produto produto) {
        if (produto != null) {
            listaProdutos.add(produto);
            return true;
        }
        return false;
    }

    public List<Produto> listarTodos() {
        return listaProdutos;
    }

    public boolean remover(int id) {
        return listaProdutos.removeIf(p -> p.getId() == id);
    }

    public Produto buscarPorId(int id) {
        for (Produto p : listaProdutos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean atualizar(Produto novoProduto) {
        for (int i = 0; i < listaProdutos.size(); i++) {
            if (listaProdutos.get(i).getId() == novoProduto.getId()) {
                listaProdutos.set(i, novoProduto);
                return true;
            }
        }
        return false;
    }
}

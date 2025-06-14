package com.controladordeestoque.dao;

import com.controladordeestoque.model.MovimentoEstoque;
import java.util.ArrayList;
import java.util.List;

public class MovimentoEstoqueDAO {

    private static List<MovimentoEstoque> listaMovimentos = new ArrayList<>();

    public boolean registrarMovimento(MovimentoEstoque movimento) {
        if (movimento != null && movimento.getProduto() != null) {
            listaMovimentos.add(movimento);
            return true;
        }
        return false;
    }

    public List<MovimentoEstoque> listarTodos() {
        return listaMovimentos;
    }

    public List<MovimentoEstoque> listarPorTipo(String tipo) {
        List<MovimentoEstoque> filtrados = new ArrayList<>();
        for (MovimentoEstoque m : listaMovimentos) {
            if (m.getTipo().equalsIgnoreCase(tipo)) {
                filtrados.add(m);
            }
        }
        return filtrados;
    }

    public List<MovimentoEstoque> listarPorProdutoId(int produtoId) {
        List<MovimentoEstoque> filtrados = new ArrayList<>();
        for (MovimentoEstoque m : listaMovimentos) {
            if (m.getProduto().getId() == produtoId) {
                filtrados.add(m);
            }
        }
        return filtrados;
    }
}

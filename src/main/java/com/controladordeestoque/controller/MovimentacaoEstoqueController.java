package com.controladordeestoque.controller;

import com.controladordeestoque.model.Produto;
import java.util.List;
import javax.swing.JOptionPane;

public class MovimentacaoEstoqueController {

    private List<Produto> listaProdutos;

    public MovimentacaoEstoqueController(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public boolean registrarEntrada(int idProduto, int quantidade) {
        for (Produto p : listaProdutos) {
            if (p.getId() == idProduto) {
                int novaQuantidade = p.getQuantidade() + quantidade;

                if (novaQuantidade > p.getQuantidadeMaxima()) {
                    JOptionPane.showMessageDialog(null,
                        "Erro: Estoque ultrapassa o limite máximo permitido!",
                        "Limite Excedido", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                p.setQuantidade(novaQuantidade);
                return true;
            }
        }
        return false;
    }

    public boolean registrarSaida(int idProduto, int quantidade) {
        for (Produto p : listaProdutos) {
            if (p.getId() == idProduto) {
                int novaQuantidade = p.getQuantidade() - quantidade;

                if (novaQuantidade < p.getQuantidadeMinima()) {
                    JOptionPane.showMessageDialog(null,
                        "Erro: Estoque abaixo do limite mínimo permitido!",
                        "Estoque Insuficiente", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                p.setQuantidade(novaQuantidade);
                return true;
            }
        }
        return false;
    }
}

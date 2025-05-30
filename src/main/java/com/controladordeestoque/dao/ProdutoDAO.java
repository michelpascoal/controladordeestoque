package com.controladordeestoque.dao;

import com.controladordeestoque.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static List<Produto> listaProdutos = new ArrayList<>();

    public boolean salvar(Produto produto) {
        if (produto != null) {
            return listaProdutos.add(produto);
        }
        return false;
    }

    public List<Produto> listarTodos() {
        return listaProdutos;
    }

    public Produto buscarPorId(int id) {
        for (Produto p : listaProdutos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        return listaProdutos.removeIf(p -> p.getId() == id);
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

    /**
     * Reajusta o preço de todos os produtos em um determinado percentual.
     * @param percentual Percentual a ser aplicado (ex: 10 para 10%).
     */
    public void reajustarPrecos(double percentual) {
        for (Produto p : listaProdutos) {
            double precoAtual = p.getPrecoUnitario();
            double novoPreco = precoAtual + (precoAtual * percentual / 100);
            p.setPrecoUnitario(novoPreco);
        }
    }
    public List<String> gerarRelatorioListaPrecos() {
        List<String> relatorio = new ArrayList<>();
        List<Produto> ordenados = new ArrayList<>(listaProdutos);

    // Ordena os produtos pelo nome
    ordenados.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));

    for (Produto p : ordenados) {
        String linha = String.format("Nome: %s | Preço: R$ %.2f | Quantidade: %d | Categoria: %s",
                p.getNome(), p.getPrecoUnitario(), p.getQuantidade(), p.getCategoria().getNome());
        relatorio.add(linha);
    }
    return relatorio;
   }
    public List<String> gerarRelatorioBalanco() {
    List<String> relatorio = new ArrayList<>();
    double valorTotalEstoque = 0.0;

    for (Produto p : listaProdutos) {
        double valorProduto = p.getQuantidade() * p.getPrecoUnitario();
        valorTotalEstoque += valorProduto;

        String linha = String.format("Nome: %s | Quantidade: %d | Unitário: R$ %.2f | Total: R$ %.2f",
                p.getNome(), p.getQuantidade(), p.getPrecoUnitario(), valorProduto);

        relatorio.add(linha);
    }

    relatorio.add(String.format("=== VALOR TOTAL DO ESTOQUE: R$ %.2f ===", valorTotalEstoque));
    return relatorio;
   }
    public List<String> gerarRelatorioAbaixoMinimo() {
    List<String> relatorio = new ArrayList<>();

    for (Produto p : listaProdutos) {
        if (p.getQuantidade() < p.getQuantidadeMinima()) {
            String linha = String.format("Produto: %s | Quantidade: %d | Mínimo: %d",
                    p.getNome(), p.getQuantidade(), p.getQuantidadeMinima());
            relatorio.add(linha);
        }
    }
    return relatorio;
   }
}

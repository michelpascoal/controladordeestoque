package com.controladordeestoque.controller;

import com.controladordeestoque.model.Produto;
import com.controladordeestoque.model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    public static void setPrecoUnitario(double preco) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void setQuantidade(int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void setQuantidadeMinima(int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void setQuantidadeMaxima(int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static boolean btnSalvar(Produto produto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<Produto> listaProdutos = new ArrayList<>();

    public boolean cadastrarProduto(Produto produto) {
        try {
            validarProduto(produto); // Valida antes de cadastrar
            listaProdutos.add(produto);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
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

    public boolean atualizarProduto(Produto novoProduto) {
        try {
            validarProduto(novoProduto); // Valida antes de atualizar
            for (int i = 0; i < listaProdutos.size(); i++) {
                if (listaProdutos.get(i).getId() == novoProduto.getId()) {
                    listaProdutos.set(i, novoProduto);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
        return false;
    }

    // ✅ Método de validação
    private void validarProduto(Produto produto) throws Exception {
        if (produto == null) {
            throw new Exception("Produto não pode ser nulo.");
        }
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new Exception("O nome do produto não pode estar vazio.");
        }
        if (produto.getPrecoUnitario() <= 0) {
            throw new Exception("O preço do produto deve ser maior que zero.");
        }
        if (produto.getQuantidade() < 0) {
            throw new Exception("A quantidade não pode ser negativa.");
        }
    }

    // 🔍 Buscar produtos por nome (parcial, sem case sensitive)
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : listaProdutos) {
            if (p.getNome() != null && p.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // 🔍 Buscar produtos por categoria exata
    public List<Produto> buscarPorCategoria(Categoria categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : listaProdutos) {
            if (p.getCategoria() != null && p.getCategoria().equals(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}

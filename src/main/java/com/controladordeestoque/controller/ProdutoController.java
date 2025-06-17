package com.controladordeestoque.controller;

import com.controladordeestoque.dao.CategoriaDAO; 
import com.controladordeestoque.dao.ProdutoDAO;   
import com.controladordeestoque.model.Produto;
import com.controladordeestoque.model.Categoria;
import java.util.ArrayList; 
import java.util.List;

public class ProdutoController {

    public static void setQuantidadeMaxima(int qtd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void setPrecoUnitario(double preco) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private ProdutoDAO produtoDAO;
    private CategoriaDAO categoriaDAO;

    // Construtor do Controller, responsável por instanciar os DAOs
    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    
    public boolean cadastrarProduto(Produto produto) {
        try {
            validarProduto(produto); // Chama o método de validação interna

            // Lógica para garantir que a categoria do produto esteja persistida.
            // Se a categoria vem sem ID (id == 0), tentamos buscá-la por nome.
            // Se não existir, salvamos a nova categoria no banco.
            if (produto.getCategoria() != null && produto.getCategoria().getId() == 0) {
                Categoria categoriaExistente = categoriaDAO.buscarPorNome(produto.getCategoria().getNome());
                if (categoriaExistente != null) {
                    produto.setCategoria(categoriaExistente); // Atribui a categoria existente do banco ao produto
                } else {
                    categoriaDAO.salvar(produto.getCategoria()); // Salva a nova categoria e o ID é setado nela pelo DAO
                }
            }
            
            return produtoDAO.salvar(produto); // Delega a operação de salvar ao ProdutoDAO
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos os produtos cadastrados no banco de dados.
     * @return Uma lista de objetos Produto.
     */
    public List<Produto> listarProdutos() {
        return produtoDAO.listarTodos(); // Delega a operação de listar ao ProdutoDAO
    }

    
    public boolean removerProduto(int id) {
        return produtoDAO.remover(id); // Delega a operação de remover ao ProdutoDAO
    }

   
    public Produto buscarProdutoPorId(int id) {
        return produtoDAO.buscarPorId(id); // Delega a operação de busca ao ProdutoDAO
    }

    
    public boolean atualizarProduto(Produto novoProduto) {
        try {
            validarProduto(novoProduto); // Chama o método de validação interna

            // Lógica para garantir que a categoria do produto esteja persistida.
            // Similar ao cadastrarProduto, trata categorias novas ou alteradas.
             if (novoProduto.getCategoria() != null && novoProduto.getCategoria().getId() == 0) {
                Categoria categoriaExistente = categoriaDAO.buscarPorNome(novoProduto.getCategoria().getNome());
                if (categoriaExistente != null) {
                    novoProduto.setCategoria(categoriaExistente);
                } else {
                    categoriaDAO.salvar(novoProduto.getCategoria());
                }
            }
            return produtoDAO.atualizar(novoProduto); // Delega a operação de atualização ao ProdutoDAO
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    
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
        if (produto.getQuantidade() < 0) { // Quantidade pode ser 0, mas não negativa.
            throw new Exception("A quantidade não pode ser negativa.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().getId() == 0) {
            throw new Exception("Uma categoria válida deve ser selecionada para o produto.");
        }
    }

   
    public List<Produto> buscarPorNome(String nome) {
        return produtoDAO.buscarPorNome(nome); // Delega a busca ao ProdutoDAO
    }

   
    public List<Produto> buscarPorCategoria(Categoria categoria) {
        if (categoria != null) {
            return produtoDAO.buscarPorCategoria(categoria.getId()); // Delega a busca ao ProdutoDAO usando o ID da categoria
        }
        return new ArrayList<>(); // Retorna lista vazia se a categoria for nula
    }
}
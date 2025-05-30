package com.controladordeestoque.principal;

import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import com.controladordeestoque.view.FrmMenuPrincipal;
import javax.swing.SwingUtilities;
import java.util.Date;
import java.util.List;

public class Principal {

    public static void main(String[] args) {
        // Criando categoria
        Categoria categoria = new Categoria(1, "Bebidas");
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.salvar(categoria);
        categoriaDAO.salvar(new Categoria(2, "Alimentos"));
        categoriaDAO.salvar(new Categoria(3, "Limpeza"));
        categoriaDAO.salvar(new Categoria(4, "Higiene Pessoal"));
        categoriaDAO.salvar(new Categoria(5, "Frios e Laticínios"));
        categoriaDAO.salvar(new Categoria(6, "Padaria"));
        categoriaDAO.salvar(new Categoria(7, "Congelados"));

        // Criando produto
        Produto produto = new Produto(1, "Refrigerante", "Lata 350ml", 50, new Date(), categoria, 4.50, 10);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.salvar(produto);

        // Listando produtos
        System.out.println("=== Lista de Produtos ===");
        List<Produto> produtos = produtoDAO.listarTodos();
        for (Produto p : produtos) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Categoria: " + p.getCategoria().getNome());
            System.out.println("Quantidade: " + p.getQuantidade());
            System.out.println("Validade: " + p.getValidade());
            System.out.println("-----------------------------");
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FrmMenuPrincipal telaPrincipal = new FrmMenuPrincipal();
                telaPrincipal.setVisible(true);
            }
        });
    }
}

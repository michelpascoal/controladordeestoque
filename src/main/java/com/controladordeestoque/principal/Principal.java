package com.controladordeestoque.principal;

import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import com.controladordeestoque.view.FrmMenuPrincipal;
import javax.swing.SwingUtilities;
import java.util.Date;
import java.util.List;

/**
 * Classe principal que serve como ponto de entrada para a aplicação "Controlador de Estoque".
 * Suas responsabilidades incluem:
 * 1. Inicializar o banco de dados com dados de teste (se estiver vazio).
 * 2. Listar os dados existentes no console para verificação.
 * 3. Iniciar a interface gráfica do usuário (GUI).
 */
public class Principal {

    /**
     * O método principal que inicia a execução da aplicação.
     *
     * @param args Argumentos de linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Bloco para popular o banco de dados com dados iniciais se estiver vazio.
        // Ideal para a primeira execução do sistema ou para fins de demonstração.
        try {
            if (categoriaDAO.listarTodas().isEmpty()) {
                System.out.println("Inserindo categorias iniciais...");
                categoriaDAO.salvar(new Categoria(0, "Bebidas"));
                categoriaDAO.salvar(new Categoria(0, "Alimentos"));
                categoriaDAO.salvar(new Categoria(0, "Limpeza"));
                categoriaDAO.salvar(new Categoria(0, "Higiene Pessoal"));
                categoriaDAO.salvar(new Categoria(0, "Frios e Laticínios"));
                categoriaDAO.salvar(new Categoria(0, "Padaria"));
                categoriaDAO.salvar(new Categoria(0, "Congelados"));
                System.out.println("Categorias iniciais inseridas.");
            }

            if (produtoDAO.listarTodos().isEmpty()) { // Verifica se há produtos no banco
                System.out.println("Inserindo produtos iniciais...");
                Categoria categoriaBebidas = categoriaDAO.buscarPorNome("Bebidas"); // Busca a categoria recém-criada
                if (categoriaBebidas != null) {
                    produtoDAO.salvar(new Produto(0, "Refrigerante", "Lata 350ml", 50, new Date(), categoriaBebidas, 4.50, 10, 200));
                }
                Categoria categoriaAlimentos = categoriaDAO.buscarPorNome("Alimentos");
                if (categoriaAlimentos != null) {
                    // Validade de 1 ano a partir de hoje
                    produtoDAO.salvar(new Produto(0, "Arroz Tipo 1", "Pacote 5kg", 30, new Date(System.currentTimeMillis() + 86400000L * 365), categoriaAlimentos, 25.00, 5, 100));
                }
                System.out.println("Produtos iniciais inseridos.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados de teste: " + e.getMessage());
        }


        // Listando produtos do banco de dados para verificar (saída no console)
        System.out.println("=== Lista de Produtos do Banco de Dados ===");
        List<Produto> produtos = produtoDAO.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto no banco de dados.");
        } else {
            for (Produto p : produtos) {
                System.out.println("ID: " + p.getId());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Categoria: " + (p.getCategoria() != null ? p.getCategoria().getNome() : "N/A"));
                System.out.println("Quantidade: " + p.getQuantidade());
                System.out.println("Validade: " + p.getValidade());
                System.out.println("Preço Unitário: " + p.getPrecoUnitario());
                System.out.println("Quantidade Mínima: " + p.getQuantidadeMinima());
                System.out.println("Quantidade Máxima: " + p.getQuantidadeMaxima());
                System.out.println("-----------------------------");
            }
        }

        // Inicia a interface gráfica na Event Dispatch Thread (EDT) para garantir a segurança de threads do Swing.
        SwingUtilities.invokeLater(() -> {
            FrmMenuPrincipal telaPrincipal = new FrmMenuPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}
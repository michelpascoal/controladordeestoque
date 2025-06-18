package com.controladordeestoque.controller;

import com.controladordeestoque.dao.MovimentoEstoqueDAO; 
import com.controladordeestoque.dao.ProdutoDAO;         
import com.controladordeestoque.model.MovimentoEstoque; 
import com.controladordeestoque.model.Produto;          
import java.util.Date;                                  
import java.util.List;
import javax.swing.JOptionPane;                         

public class MovimentacaoEstoqueController {

    private final ProdutoDAO produtoDAO;
    private final MovimentoEstoqueDAO movimentoEstoqueDAO;

    // Construtor do Controller, responsável por instanciar os DAOs
    public MovimentacaoEstoqueController() {
        this.produtoDAO = new ProdutoDAO();
        this.movimentoEstoqueDAO = new MovimentoEstoqueDAO();
    }

    
    public boolean registrarEntrada(int idProduto, int quantidade) {
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(null, "A quantidade para entrada deve ser positiva.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Busca o produto no banco de dados usando o DAO
        Produto produto = produtoDAO.buscarPorId(idProduto);
        if (produto == null) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int novaQuantidade = produto.getQuantidade() + quantidade;

        // Verifica se a nova quantidade excede a quantidade máxima permitida (se definida)
        if (produto.getQuantidadeMaxima() > 0 && novaQuantidade > produto.getQuantidadeMaxima()) {
            int resposta = JOptionPane.showConfirmDialog(
                null, 
                "Atenção: Esta entrada fará o estoque exceder a quantidade máxima definida (" + produto.getQuantidadeMaxima() + ").\n" +
                "Estoque resultante: " + novaQuantidade + ".\n\nDeseja continuar mesmo assim?",
                "Estoque Excedendo o Máximo", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.NO_OPTION) {
                return false;
            }
        }
        
        // Atualiza a quantidade do produto no objeto e persiste a alteração no banco de dados
        produto.setQuantidade(novaQuantidade);
        boolean produtoAtualizado = produtoDAO.atualizar(produto); // Delega a atualização ao ProdutoDAO

        if (produtoAtualizado) {
            MovimentoEstoque movimento = new MovimentoEstoque(0, produto, quantidade, "ENTRADA", new Date());
            return movimentoEstoqueDAO.registrarMovimento(movimento); // Delega o registro ao MovimentoEstoqueDAO
        }
        return false; 
    }

    
    public boolean registrarSaida(int idProduto, int quantidade) {
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(null, "A quantidade para saída deve ser positiva.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Busca o produto no banco de dados usando o DAO
        Produto produto = produtoDAO.buscarPorId(idProduto);
        if (produto == null) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Verifica se há estoque suficiente para a saída
        if (produto.getQuantidade() < quantidade) {
            JOptionPane.showMessageDialog(null, "Estoque insuficiente para a saída de " + quantidade + " unidade(s) do produto '" + produto.getNome() + "'.\nEstoque atual: " + produto.getQuantidade(), "Estoque Insuficiente", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int novaQuantidade = produto.getQuantidade() - quantidade;

        // Verifica se a nova quantidade ficará abaixo da quantidade mínima (se definida)
        if (novaQuantidade < produto.getQuantidadeMinima()) {
            int resposta = JOptionPane.showConfirmDialog(
                null, 
                "Atenção: Esta saída fará o estoque do produto '" + produto.getNome() + "' ficar abaixo do mínimo (" + produto.getQuantidadeMinima() + ").\n" +
                "Estoque resultante: " + novaQuantidade + ".\n\nDeseja continuar com a operação?",
                "Estoque Abaixo do Mínimo", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
            
            if (resposta == JOptionPane.NO_OPTION) {
                return false;
            }
        }

        // Atualiza a quantidade do produto no objeto e persiste a alteração no banco de dados
        produto.setQuantidade(novaQuantidade);
        boolean produtoAtualizado = produtoDAO.atualizar(produto); // Delega a atualização ao ProdutoDAO

        if (produtoAtualizado) {         
            MovimentoEstoque movimento = new MovimentoEstoque(0, produto, quantidade, "SAIDA", new Date());
            return movimentoEstoqueDAO.registrarMovimento(movimento); // Delega o registro ao MovimentoEstoqueDAO
        }
        return false;
        
    }
    public List<MovimentoEstoque> listarTodasMovimentacoes() {
        return this.movimentoEstoqueDAO.listarTodos(); // Delega a chamada para o DAO
    }
}
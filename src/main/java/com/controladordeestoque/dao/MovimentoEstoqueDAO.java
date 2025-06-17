package com.controladordeestoque.dao;

import com.controladordeestoque.model.MovimentoEstoque;
import com.controladordeestoque.model.Produto; 
import com.controladordeestoque.util.DatabaseConnection; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.List;

public class MovimentoEstoqueDAO {

    

    public boolean registrarMovimento(MovimentoEstoque movimento) {
    String sql = "INSERT INTO movimentos_estoque (produto_id, quantidade, tipo, data) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, movimento.getProduto().getId());
        stmt.setInt(2, movimento.getQuantidade());
        stmt.setString(3, movimento.getTipo());
        stmt.setTimestamp(4, new Timestamp(movimento.getData().getTime())); // Converte java.util.Date para java.sql.Timestamp
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movimento.setId(rs.getInt(1)); // Define o ID gerado pelo banco
                }
            }
            return true;
        }
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao registrar movimento de estoque: " + e.getMessage());
        return false;
    }
}

    public List<MovimentoEstoque> listarTodos() {
    List<MovimentoEstoque> listaMovimentos = new ArrayList<>();
    // JOIN para obter o nome do produto associado ao movimento
    String sql = "SELECT m.*, p.nome as produto_nome FROM movimentos_estoque m JOIN produtos p ON m.produto_id = p.id ORDER BY m.data DESC";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            MovimentoEstoque movimento = new MovimentoEstoque();
            movimento.setId(rs.getInt("id"));
            movimento.setQuantidade(rs.getInt("quantidade"));
            movimento.setTipo(rs.getString("tipo"));
            movimento.setData(rs.getTimestamp("data")); // Retorna java.sql.Timestamp, subtipo de java.util.Date

            Produto produto = new Produto();
            produto.setId(rs.getInt("produto_id"));
            produto.setNome(rs.getString("produto_nome"));
            // Outros detalhes do produto (como quantidade, validade, categoria) não são estritamente necessários para um registro de movimento aqui,
            // mas você poderia buscá-los se necessário com JOINs mais complexos ou chamadas de DAO separadas.
            movimento.setProduto(produto);

            listaMovimentos.add(movimento);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar movimentos de estoque: " + e.getMessage());
    }
    return listaMovimentos;
}

    public List<MovimentoEstoque> listarPorTipo(String tipo) {
    List<MovimentoEstoque> filtrados = new ArrayList<>();
    String sql = "SELECT m.*, p.nome as produto_nome FROM movimentos_estoque m JOIN produtos p ON m.produto_id = p.id WHERE m.tipo = ? ORDER BY m.data DESC";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, tipo.toUpperCase()); // Garante que o tipo seja maiúsculo para corresponder ao DB
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovimentoEstoque movimento = new MovimentoEstoque();
                movimento.setId(rs.getInt("id"));
                movimento.setQuantidade(rs.getInt("quantidade"));
                movimento.setTipo(rs.getString("tipo"));
                movimento.setData(rs.getTimestamp("data"));

                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                movimento.setProduto(produto);

                filtrados.add(movimento);
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar movimentos por tipo: " + e.getMessage());
    }
    return filtrados;
}

    public List<MovimentoEstoque> listarPorProdutoId(int produtoId) {
    List<MovimentoEstoque> filtrados = new ArrayList<>();
    String sql = "SELECT m.*, p.nome as produto_nome FROM movimentos_estoque m JOIN produtos p ON m.produto_id = p.id WHERE m.produto_id = ? ORDER BY m.data DESC";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, produtoId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovimentoEstoque movimento = new MovimentoEstoque();
                movimento.setId(rs.getInt("id"));
                movimento.setQuantidade(rs.getInt("quantidade"));
                movimento.setTipo(rs.getString("tipo"));
                movimento.setData(rs.getTimestamp("data"));

                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                movimento.setProduto(produto);

                filtrados.add(movimento);
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar movimentos por ID do produto: " + e.getMessage());
    }
    return filtrados;
}
}

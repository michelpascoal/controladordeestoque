package com.controladordeestoque.dao;

import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import com.controladordeestoque.util.DatabaseConnection; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    

    public boolean salvar(Produto produto) {
    String sql = "INSERT INTO produtos (nome, descricao, quantidade, validade, categoria_id, preco_unitario, quantidade_minima, quantidade_maxima) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setInt(3, produto.getQuantidade());

        // Converte java.util.Date para java.sql.Date para armazenar no banco
        if (produto.getValidade() != null) {
            stmt.setDate(4, new Date(produto.getValidade().getTime())); 
        } else {
            stmt.setNull(4, java.sql.Types.DATE); // Permite validade nula no banco
        }

        stmt.setInt(5, produto.getCategoria().getId());
        stmt.setDouble(6, produto.getPrecoUnitario());
        stmt.setInt(7, produto.getQuantidadeMinima());
        stmt.setInt(8, produto.getQuantidadeMaxima());

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1)); // Define o ID gerado pelo banco
                }
            }
            return true;
        }
        return false;
    } catch (SQLException e) {
        System.err.println("Erro ao salvar produto: " + e.getMessage());
        return false;
    }
}

    public List<Produto> listarTodos() {
    List<Produto> listaProdutos = new ArrayList<>();
    // Note o JOIN para buscar o nome da categoria associada ao produto
    String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id ORDER BY p.nome";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setQuantidade(rs.getInt("quantidade"));
            produto.setValidade(rs.getDate("validade")); 
            produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
            produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
            produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));

            Categoria categoria = new Categoria();
            categoria.setId(rs.getInt("categoria_id"));
            categoria.setNome(rs.getString("categoria_nome")); // Obtém o nome da categoria do JOIN
            produto.setCategoria(categoria);

            listaProdutos.add(produto);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar produtos: " + e.getMessage());
    }
    return listaProdutos;
}
    public List<Produto> buscarPorCategoria(int categoriaId) {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.categoria_id = ? ORDER BY p.nome";
        try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setValidade(rs.getDate("validade"));
                    produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                    produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                    produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));

                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("categoria_id"));
                    categoria.setNome(rs.getString("categoria_nome"));
                    produto.setCategoria(categoria);

                    listaProdutos.add(produto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por categoria: " + e.getMessage());
        }
        return listaProdutos;
    }
    public Produto buscarPorId(int id) {
    
    String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ?";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValidade(rs.getDate("validade"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                produto.setCategoria(categoria);
                return produto;
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
    }
    return null;
}

    public boolean remover(int id) {
    String sql = "DELETE FROM produtos WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.err.println("Erro ao remover produto: " + e.getMessage());
        // Tratar caso haja movimentos de estoque associados (Foreign Key constraint no MySQL é erro 1451)
        if (e.getErrorCode() == 1451) {
            System.err.println("Não é possível remover o produto, pois existem movimentos de estoque associados a ele.");
        }
        return false;
    }
}
    
    public List<Produto> buscarPorNome(String nome) {
    List<Produto> listaProdutos = new ArrayList<>();
    String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.nome LIKE ? ORDER BY p.nome";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, "%" + nome + "%"); // Adiciona curingas para busca parcial
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setValidade(rs.getDate("validade"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                produto.setCategoria(categoria);

                listaProdutos.add(produto);
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar produtos por nome: " + e.getMessage());
    }
    return listaProdutos;
}

    public boolean atualizar(Produto novoProduto) {
    String sql = "UPDATE produtos SET nome = ?, descricao = ?, quantidade = ?, validade = ?, categoria_id = ?, preco_unitario = ?, quantidade_minima = ?, quantidade_maxima = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, novoProduto.getNome());
        stmt.setString(2, novoProduto.getDescricao());
        stmt.setInt(3, novoProduto.getQuantidade());

        if (novoProduto.getValidade() != null) {
            stmt.setDate(4, new Date(novoProduto.getValidade().getTime()));
        } else {
            stmt.setNull(4, java.sql.Types.DATE);
        }

        stmt.setInt(5, novoProduto.getCategoria().getId());
        stmt.setDouble(6, novoProduto.getPrecoUnitario());
        stmt.setInt(7, novoProduto.getQuantidadeMinima());
        stmt.setInt(8, novoProduto.getQuantidadeMaxima());
        stmt.setInt(9, novoProduto.getId());
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.err.println("Erro ao atualizar produto: " + e.getMessage());
        return false;
    }
}

    /**
     * Reajusta o preço de todos os produtos em um determinado percentual.
     * @param percentual Percentual a ser aplicado (ex: 10 para 10%).
     */
    public void reajustarPrecos(double percentual) {
    String sql = "UPDATE produtos SET preco_unitario = preco_unitario + (preco_unitario * ? / 100)";
    try (Connection conn = DatabaseConnection.getConnection(); // Chamada à classe de conexão
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setDouble(1, percentual);
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Erro ao reajustar preços: " + e.getMessage());
    }
}
    public List<String> gerarRelatorioListaPrecos() {
    List<String> relatorio = new ArrayList<>();
    List<Produto> ordenados = new ArrayList<>(listarTodos()); // CHAMA listarTodos() agora

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

        List<Produto> produtos = listarTodos(); // Busca do banco através do método já atualizado
        for (Produto p : produtos) {
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
        List<Produto> produtos = listarTodos(); // Busca do banco através do método já atualizado

        for (Produto p : produtos) {
            if (p.getQuantidade() < p.getQuantidadeMinima()) {
                String linha = String.format("Produto: %s | Quantidade: %d | Mínimo: %d",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMinima());
                relatorio.add(linha);
            }
        }
        return relatorio;
    }
    public List<String> gerarRelatorioAcimaMaximo() {
        List<String> relatorio = new ArrayList<>();
        List<Produto> produtos = listarTodos(); // Busca do banco através do método já atualizado

        for (Produto p : produtos) {
            if (p.getQuantidadeMaxima() > 0 && p.getQuantidade() > p.getQuantidadeMaxima()) {
                String linha = String.format("Produto: %s | Quantidade: %d | Máximo: %d",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMaxima());
                relatorio.add(linha);
            }
        }
        return relatorio;
    }
}

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

/**
 * DAO (Data Access Object) para a entidade Produto.
 * Esta classe centraliza todas as operações de banco de dados relacionadas a produtos,
 * incluindo CRUD, buscas e geração de relatórios.
 */
public class ProdutoDAO {

    /**
     * Salva um novo produto no banco de dados.
     * Após a inserção, o ID gerado pelo banco é atribuído de volta ao objeto Produto.
     *
     * @param produto O objeto {@link Produto} a ser salvo. O ID deve ser 0.
     * @return {@code true} se o produto foi salvo com sucesso, {@code false} caso contrário.
     */
    public boolean salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, quantidade, validade, categoria_id, preco_unitario, quantidade_minima, quantidade_maxima) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());

            if (produto.getValidade() != null) {
                stmt.setDate(4, new Date(produto.getValidade().getTime()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
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
    
    /**
     * Atualiza os dados de um produto existente no banco de dados.
     *
     * @param novoProduto O objeto {@link Produto} com as informações atualizadas. O ID do produto deve ser válido.
     * @return {@code true} se a atualização foi bem-sucedida, {@code false} caso contrário.
     */
    public boolean atualizar(Produto novoProduto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, quantidade = ?, validade = ?, categoria_id = ?, preco_unitario = ?, quantidade_minima = ?, quantidade_maxima = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
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
     * Lista todos os produtos do banco de dados, incluindo os dados da categoria associada.
     *
     * @return Uma {@link List} de objetos {@link Produto}.
     */
    public List<Produto> listarTodos() {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id ORDER BY p.nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = mapRowToProduto(rs);
                listaProdutos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return listaProdutos;
    }

    /**
     * Busca produtos por uma categoria específica.
     *
     * @param categoriaId O ID da categoria para filtrar os produtos.
     * @return Uma {@link List} de {@link Produto} pertencentes à categoria.
     */
    public List<Produto> buscarPorCategoria(int categoriaId) {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.categoria_id = ? ORDER BY p.nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoriaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = mapRowToProduto(rs);
                    listaProdutos.add(produto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por categoria: " + e.getMessage());
        }
        return listaProdutos;
    }

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return Um objeto {@link Produto} se encontrado, caso contrário {@code null}.
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Busca produtos por nome, utilizando uma busca parcial (LIKE).
     *
     * @param nome O termo de busca para o nome do produto.
     * @return Uma {@link List} de {@link Produto} cujos nomes contêm o termo de busca.
     */
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT p.*, c.nome as categoria_nome FROM produtos p JOIN categorias c ON p.categoria_id = c.id WHERE p.nome LIKE ? ORDER BY p.nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto produto = mapRowToProduto(rs);
                    listaProdutos.add(produto);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por nome: " + e.getMessage());
        }
        return listaProdutos;
    }

    /**
     * Remove um produto do banco de dados pelo seu ID.
     * A remoção é bloqueada caso existam movimentos de estoque associados a este produto.
     *
     * @param id O ID do produto a ser removido.
     * @return {@code true} se a remoção for bem-sucedida, {@code false} caso contrário.
     */
    public boolean remover(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) { // Erro de Foreign Key no MySQL
                System.err.println("Não é possível remover o produto, pois existem movimentos de estoque associados a ele.");
            } else {
                System.err.println("Erro ao remover produto: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Reajusta o preço de todos os produtos no banco de dados aplicando um percentual.
     *
     * @param percentual O percentual de reajuste a ser aplicado (ex: 10.0 para 10%).
     */
    public void reajustarPrecos(double percentual) {
        String sql = "UPDATE produtos SET preco_unitario = preco_unitario * (1 + ? / 100)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, percentual);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao reajustar preços: " + e.getMessage());
        }
    }

    /**
     * Gera um relatório formatado com a lista de preços de todos os produtos, ordenado por nome.
     *
     * @return Uma {@link List} de Strings, onde cada string é uma linha do relatório.
     */
    public List<String> gerarRelatorioListaPrecos() {
        List<String> relatorio = new ArrayList<>();
        List<Produto> produtos = listarTodos();
        produtos.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));

        for (Produto p : produtos) {
            String linha = String.format("Nome: %s | Preço: R$ %.2f | Quantidade: %d | Categoria: %s",
                    p.getNome(), p.getPrecoUnitario(), p.getQuantidade(), p.getCategoria().getNome());
            relatorio.add(linha);
        }
        return relatorio;
    }

    /**
     * Gera um relatório de balanço do estoque, com o valor total por produto e o valor total geral.
     *
     * @return Uma {@link List} de Strings com os dados do balanço e a soma total no final.
     */
    public List<String> gerarRelatorioBalanco() {
        List<String> relatorio = new ArrayList<>();
        double valorTotalEstoque = 0.0;

        List<Produto> produtos = listarTodos();
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

    /**
     * Gera um relatório de produtos que estão com a quantidade em estoque abaixo do mínimo definido.
     *
     * @return Uma {@link List} de Strings, onde cada string representa um produto abaixo do mínimo.
     */
    public List<String> gerarRelatorioAbaixoMinimo() {
        List<String> relatorio = new ArrayList<>();
        List<Produto> produtos = listarTodos();

        for (Produto p : produtos) {
            if (p.getQuantidadeMinima() > 0 && p.getQuantidade() < p.getQuantidadeMinima()) {
                String linha = String.format("Produto: %s | Quantidade: %d | Mínimo: %d",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMinima());
                relatorio.add(linha);
            }
        }
        return relatorio;
    }

    /**
     * Gera um relatório de produtos que estão com a quantidade em estoque acima do máximo definido.
     *
     * @return Uma {@link List} de Strings, onde cada string representa um produto acima do máximo.
     */
    public List<String> gerarRelatorioAcimaMaximo() {
        List<String> relatorio = new ArrayList<>();
        List<Produto> produtos = listarTodos();

        for (Produto p : produtos) {
            if (p.getQuantidadeMaxima() > 0 && p.getQuantidade() > p.getQuantidadeMaxima()) {
                String linha = String.format("Produto: %s | Quantidade: %d | Máximo: %d",
                        p.getNome(), p.getQuantidade(), p.getQuantidadeMaxima());
                relatorio.add(linha);
            }
        }
        return relatorio;
    }
    
    /**
     * Método auxiliar para mapear uma linha do ResultSet para um objeto Produto.
     * Reduz a duplicação de código nos métodos de busca.
     *
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto {@link Produto} preenchido com os dados da linha.
     * @throws SQLException se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Produto mapRowToProduto(ResultSet rs) throws SQLException {
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
package com.controladordeestoque.dao;

import com.controladordeestoque.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.controladordeestoque.model.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Categoria.
 * Esta classe encapsula todas as interações com o banco de dados (operações de CRUD)
 * para a tabela de categorias.
 */
public class CategoriaDAO {

    /**
     * Salva uma nova categoria no banco de dados.
     * Após a inserção, o ID gerado pelo banco de dados é atribuído de volta ao objeto Categoria.
     * Trata especificamente erros de duplicidade de nome (UNIQUE constraint).
     *
     * @param categoria O objeto {@link Categoria} a ser salvo. O ID deve ser 0.
     * @return {@code true} se a categoria foi salva com sucesso, {@code false} caso contrário.
     */
    public boolean salvar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nome) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNome());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setId(rs.getInt(1)); // Define o ID gerado pelo banco
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            // Captura erro de duplicidade de nome (UNIQUE constraint no MySQL é erro 1062)
            if (e.getErrorCode() == 1062) {
                System.err.println("Erro ao salvar categoria: Categoria com este nome já existe.");
            } else {
                System.err.println("Erro ao salvar categoria: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Lista todas as categorias cadastradas no banco de dados, ordenadas por nome.
     *
     * @return Uma {@link List} de objetos {@link Categoria}. A lista estará vazia se não houver categorias, mas nunca será nula.
     */
    public List<Categoria> listarTodas() {
        List<Categoria> listaCategorias = new ArrayList<>();
        String sql = "SELECT id, nome FROM categorias ORDER BY nome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                listaCategorias.add(categoria);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }
        return listaCategorias;
    }

    /**
     * Busca e retorna uma categoria específica pelo seu ID.
     *
     * @param id O ID da categoria a ser buscada.
     * @return O objeto {@link Categoria} encontrado, ou {@code null} se nenhuma categoria for encontrada.
     */
    public Categoria buscarPorId(int id) {
        String sql = "SELECT id, nome FROM categorias WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNome(rs.getString("nome"));
                    return categoria;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar categoria por ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Busca e retorna uma categoria específica pelo seu nome.
     *
     * @param nome O nome exato da categoria a ser buscada.
     * @return O objeto {@link Categoria} encontrado, ou {@code null} se nenhuma categoria for encontrada.
     */
    public Categoria buscarPorNome(String nome) {
        String sql = "SELECT id, nome FROM categorias WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNome(rs.getString("nome"));
                    return categoria;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar categoria por nome: " + e.getMessage());
        }
        return null;
    }

    /**
     * Remove uma categoria do banco de dados com base no seu ID.
     * A remoção é impedida se existirem produtos associados a esta categoria (erro de Foreign Key).
     *
     * @param id O ID da categoria a ser removida.
     * @return {@code true} se a remoção for bem-sucedida, {@code false} caso contrário.
     */
    public boolean remover(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Tratar caso haja produtos associados a esta categoria (erro de Foreign Key no MySQL é 1451)
            if (e.getErrorCode() == 1451) {
                System.err.println("Não é possível remover a categoria, pois existem produtos associados a ela.");
            } else {
                System.err.println("Erro ao remover categoria: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Atualiza o nome de uma categoria existente no banco de dados.
     *
     * @param categoria O objeto {@link Categoria} contendo o novo nome e o ID da categoria a ser atualizada.
     * @return {@code true} se a atualização foi bem-sucedida, {@code false} caso contrário.
     */
    public boolean atualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Captura erro de duplicidade de nome (UNIQUE constraint no MySQL é erro 1062)
            if (e.getErrorCode() == 1062) {
                System.err.println("Erro ao atualizar categoria: Categoria com este nome já existe.");
            } else {
                System.err.println("Erro ao atualizar categoria: " + e.getMessage());
            }
            return false;
        }
    }
}
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

public class CategoriaDAO {

    

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

    public boolean remover(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover categoria: " + e.getMessage());
            // Tratar caso haja produtos associados a esta categoria (erro de Foreign Key no MySQL é 1451)
            if (e.getErrorCode() == 1451) {
                System.err.println("Não é possível remover a categoria, pois existem produtos associados a ela.");
            }
            return false;
        }
    }
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
    public boolean atualizar(Categoria novaCategoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaCategoria.getNome());
            stmt.setInt(2, novaCategoria.getId());
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
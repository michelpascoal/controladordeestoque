package com.controladordeestoque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária responsável por gerenciar a conexão com o banco de dados.
 * Centraliza os detalhes da conexão (URL, usuário, senha) e fornece um método
 * estático para obter uma nova conexão.
 */
public class DatabaseConnection {

    /**
     * A URL de conexão JDBC para o banco de dados MySQL.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/control_estoque_db";
    
    /**
     * O nome de usuário para acesso ao banco de dados.
     */
    private static final String USER = "root";
    
    /**
     * A senha para acesso ao banco de dados.
     */
    private static final String PASSWORD = "";

    /**
     * Estabelece e retorna uma nova conexão com o banco de dados.
     * Este método carrega o driver JDBC do MySQL e utiliza o DriverManager para
     * criar a conexão com base nas credenciais definidas nesta classe.
     *
     * @return Um objeto {@link Connection} representando a conexão ativa com o banco.
     * @throws SQLException se o driver JDBC não for encontrado ou se houver um erro
     * ao tentar se conectar ao banco de dados.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Garante que o driver JDBC do MySQL seja carregado
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado. Verifique a dependência no pom.xml.");
            // Lança uma SQLException para que a camada de acesso a dados (DAO) possa tratá-la.
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }
    }

    /**
     * Fecha uma conexão ativa com o banco de dados de forma segura.
     * Verifica se a conexão não é nula antes de tentar fechá-la.
     * <p>
     * <b>Nota:</b> Embora este método seja útil, a prática moderna recomendada é o uso
     * de blocos {@code try-with-resources}, que gerenciam o fechamento automático
     * da conexão, tornando a chamada explícita a este método desnecessária na maioria
     * dos casos.
     *
     * @param connection A conexão a ser fechada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}
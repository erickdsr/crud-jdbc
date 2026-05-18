package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuração do banco de dados. Gerencia conexões MySQL.
 * Contém credenciais e URL de conexão.
 */
public class DatabaseConfig {
    
    // URL do banco de dados MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/crud_sql";
    // Usuário do banco
    private static final String USUARIO = "root";
    // Senha do banco
    private static final String SENHA = "2008";
    
    /**
     * Obtém conexão com o banco de dados.
     * @return nova conexão MySQL
     * @throws SQLException se houver erro de conexão
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

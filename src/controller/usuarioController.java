package controller;

import service.UsuarioService;
import dao.UsuarioDao;
import model.Usuario;
import db.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioController {
    
    private UsuarioService usuarioService;
    private Connection connection;
    
    public UsuarioController() throws SQLException {
        // Obter conexão do config
        this.connection = DatabaseConfig.getConnection();
        
        // Criar DAO e Service
        UsuarioDao usuarioDao = new UsuarioDao(this.connection);
        this.usuarioService = new UsuarioService(usuarioDao);
    }
    
    public void executar() {
        try {
            // Criar um usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("pedro");
            novoUsuario.setEmail("pedro@email.com");
            novoUsuario.setSenha("sV09mda");
            
            usuarioService.criarUsuario(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } finally {
            fecharConexao();
        }
    }
    
    private void fecharConexao() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}

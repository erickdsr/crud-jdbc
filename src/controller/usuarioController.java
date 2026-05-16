package controller;

import service.UsuarioService;
import dao.UsuarioDao;
import exceptions.DbException;
import model.Usuario;
import db.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Controlador de usuários. Orquestra a aplicação:
 * gerencia conexão, instancia serviço e executa operações.
 */
public class UsuarioController {
    
    // Serviço de negócio de usuários
    private UsuarioService usuarioService;
    // Conexão com o banco de dados
    private Connection connection;
    
    /**
     * Construtor. Inicializa conexão, DAO e serviço.
     * @throws SQLException se houver erro na conexão
     */
    public UsuarioController() throws SQLException {
        // Obter conexão do config
        this.connection = DatabaseConfig.getConnection();
        
        // Criar DAO e Service
        UsuarioDao usuarioDao = new UsuarioDao(this.connection);
        this.usuarioService = new UsuarioService(usuarioDao);
    }
    
    /**
     * Executa operações com usuários. Exemplo: cria um novo usuário.
     * Trata exceções e fecha conexão ao final.
     */
    public void executar() {
        try {
            // Criar um usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome("pedro");
            novoUsuario.setEmail("pedro@email.com");
            novoUsuario.setSenha("sV09mda");
            
            usuarioService.criarUsuario(novoUsuario);
            System.out.println("Usuário criado com sucesso!");
            
        } catch (DbException e) {
            System.err.println("Erro de banco de dados: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } finally {
            fecharConexao();
        }
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     * Verifica se conexão está aberta antes de fechar.
     */
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

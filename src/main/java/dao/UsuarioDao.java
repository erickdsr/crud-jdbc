package dao;

import exceptions.DbException;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object de usuários. Acessa dados no banco de dados.
 * Implementa operações CRUD e buscas específicas com soft delete.
 */
public class UsuarioDao {
    // Conexão com o banco de dados
    private Connection connection;

    
     //Construtor. Injeta a conexão com o banco.
    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

    
     //Insere novo usuário no banco. Define created_at e updated_at como NOW().
     
    public void create(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao criar usuário", e);
        }
    }
    /**
     * Atualiza usuário existente. Ignora deletados (soft delete).
     * Define updated_at como NOW().
     */
    public void update(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, updated_at = NOW() WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar usuário", e);
        }
    }
    /**
     * Marca usuário como deletado (soft delete). Define deleted_at como NOW().
     */
    public void softDelete(int id) {
        String sql = "UPDATE usuarios SET deleted_at = NOW() WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao excluir usuário", e);
        }
    }
    /**
     * Busca usuário por ID. Ignora deletados.
     * @return usuário encontrado ou null
     */
    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToModel(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar usuário por ID", e);
        }
        return null;
    }
    /**
     * Busca usuário por email. Ignora deletados.
     * @return usuário encontrado ou null
     */
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToModel(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar usuário por email", e);
        }
        return null;
    }
    /**
     * Verifica se email existe e não foi deletado.
     * @return true se existe
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (var rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao verificar email", e);
        }
    }

    /**
     * Busca todos os usuários não deletados.
     * @return lista de usuários
     */
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios WHERE deleted_at IS NULL ORDER BY id DESC";
        List<Usuario> usuarios = new ArrayList<>();

        try (var stmt = connection.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapRowToModel(rs));
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

    /**
     * Converte uma linha do ResultSet em objeto Usuario.
     */
    private Usuario mapRowToModel(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        usuario.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        usuario.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);
        return usuario;
    }

}

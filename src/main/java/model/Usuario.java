package model;
import java.time.LocalDateTime;

/**
 * Modelo de usuário. Representa um registro de usuário no banco de dados.
 * Contém informações: id, nome, email, senha e datas de auditoria.
 */
public class Usuario {

    // Identificador único
    private int id;
    // Nome do usuário
    private String nome;
    // Email do usuário (único)
    private String email;
    // Senha do usuário (hashada)
    private String senha;
    // Data de criação do registro
    private LocalDateTime createdAt;
    // Data da última atualização
    private LocalDateTime updatedAt;
    // Data de exclusão lógica (soft delete)
    private LocalDateTime deletedAt;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
    
}
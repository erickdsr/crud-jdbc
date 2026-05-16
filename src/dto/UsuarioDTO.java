package dto;

/**
 * Data Transfer Object. Transfere dados de usuário entre camadas.
 * Contém apenas campos essenciais: nome, email e senha.
 */
public class UsuarioDTO {

    // Nome do usuário
    private String nome;
    // Email do usuário
    private String email;
    // Senha do usuário
    private String senha;

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
   
    
}

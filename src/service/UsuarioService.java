package service;

import dao.UsuarioDao;
import model.UsuarioModel;
import security.passwordUtil;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    
    private UsuarioDao usuarioDao;
    
    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    public void criarUsuario(UsuarioModel usuario) throws IllegalArgumentException, SQLException {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (usuario.getNome().length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O email do usuário é obrigatório.");
        }
        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("O email deve conter o caractere @.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário é obrigatória.");
        }
        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
        
        // Verificar email duplicado
        List<UsuarioModel> usuarios = usuarioDao.findAll();
        for (UsuarioModel u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                throw new IllegalArgumentException("Este email já está cadastrado.");
            }
        }
        
        // Faz hash da senha antes de salvar
        String senhaHasheada = passwordUtil.hashPassword(usuario.getSenha());
        usuario.setSenha(senhaHasheada);
        
        usuarioDao.create(usuario);
    }
    
    public void atualizarUsuario(UsuarioModel usuario) throws IllegalArgumentException, SQLException {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID do usuário é obrigatório para atualização.");
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (usuario.getNome().length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O email do usuário é obrigatório.");
        }
        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("O email deve conter o caractere @.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário é obrigatória.");
        }
        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
        
        // Verificar se o usuário existe
        UsuarioModel usuarioExistente = usuarioDao.findById(usuario.getId());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        
        // Verificar email duplicado (ignorando o próprio usuário)
        List<UsuarioModel> usuarios = usuarioDao.findAll();
        for (UsuarioModel u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail()) && u.getId() != usuario.getId()) {
                throw new IllegalArgumentException("Este email já está cadastrado.");
            }
        }
        
        // Faz hash da senha antes de atualizar
        String senhaHasheada = passwordUtil.hashPassword(usuario.getSenha());
        usuario.setSenha(senhaHasheada);
        
        usuarioDao.update(usuario);
    }
    
    public UsuarioModel lerUsuario(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        UsuarioModel usuario = usuarioDao.findById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return usuario;
    }
    
    public List<UsuarioModel> lerTodosUsuarios() throws SQLException {
        return usuarioDao.findAll();
    }
    
    /**
     * Autentica um usuário verificando email e senha
     * @param email email do usuário
     * @param senha senha em texto plano
     * @return o objeto UsuarioModel se autenticado com sucesso
     * @throws IllegalArgumentException se email ou senha forem inválidos
     */
    public UsuarioModel autenticar(String email, String senha) throws IllegalArgumentException, SQLException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        
        // Busca o usuário pelo email
        List<UsuarioModel> usuarios = usuarioDao.findAll();
        UsuarioModel usuarioEncontrado = null;
        
        for (UsuarioModel u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                usuarioEncontrado = u;
                break;
            }
        }
        
        // Verifica se o usuário foi encontrado
        if (usuarioEncontrado == null) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }
        
        // Verifica se a senha corresponde ao hash armazenado
        if (!passwordUtil.verifyPassword(senha, usuarioEncontrado.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }
        
        return usuarioEncontrado;
    }
    
    public void deletarUsuario(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        UsuarioModel usuario = usuarioDao.findById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioDao.softDelete(id);
    }
    
}

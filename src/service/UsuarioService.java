package service;

import dao.UsuarioDao;
import model.Usuario;
import security.PasswordUtil;
import dto.UsuarioDTO;
import util.Validator;

import java.util.List;

public class UsuarioService {
    
    private UsuarioDao usuarioDao;
    
    public UsuarioService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    public void criarUsuario(Usuario usuario) throws IllegalArgumentException {
        // Validar usando o Validator
        Validator.validarNome(usuario.getNome());
        Validator.validarEmail(usuario.getEmail());
        Validator.validarSenha(usuario.getSenha());
        
        // Verificar email duplicado
        if (usuarioDao.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Este email já está cadastrado.");
        }
        
        // Faz hash da senha antes de salvar
        String senhaHasheada = PasswordUtil.hashPassword(usuario.getSenha());
        usuario.setSenha(senhaHasheada);
        
        usuarioDao.create(usuario);
    }
    
    public void atualizarUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID do usuário é obrigatório para atualização.");
        }
        
        // Validar usando o Validator
        Validator.validarNome(usuario.getNome());
        Validator.validarEmail(usuario.getEmail());
        Validator.validarSenha(usuario.getSenha());
        
        // Verificar se o usuário existe
        Usuario usuarioExistente = usuarioDao.findById(usuario.getId());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        
        // Verificar email duplicado (ignorando o próprio usuário)
        Usuario usuarioComMesmoEmail = usuarioDao.findByEmail(usuario.getEmail());
        if (usuarioComMesmoEmail != null && usuarioComMesmoEmail.getId() != usuario.getId()) {
            throw new IllegalArgumentException("Este email já está cadastrado.");
        }
        
        // Faz hash da senha antes de atualizar
        String senhaHasheada = PasswordUtil.hashPassword(usuario.getSenha());
        usuario.setSenha(senhaHasheada);
        
        usuarioDao.update(usuario);
    }
    
    public Usuario lerUsuario(int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        Usuario usuario = usuarioDao.findById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return usuario;
    }
    
    public List<Usuario> lerTodosUsuarios() {
        return usuarioDao.findAll();
    }
    
    /**
     * Autentica um usuário verificando email e senha
     * @param email email do usuário
     * @param senha senha em texto plano
     * @return o objeto UsuarioModel se autenticado com sucesso
     * @throws IllegalArgumentException se email ou senha forem inválidos
     */
    public Usuario autenticar(String email, String senha) throws IllegalArgumentException {
        Validator.validarEmail(email);
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }
        
        // Busca o usuário pelo email
        Usuario usuarioEncontrado = usuarioDao.findByEmail(email);
        
        // Verifica se o usuário foi encontrado
        if (usuarioEncontrado == null) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }
        
        // Verifica se a senha corresponde ao hash armazenado
        if (!PasswordUtil.verifyPassword(senha, usuarioEncontrado.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }
        
        return usuarioEncontrado;
    }
    
    public void deletarUsuario(int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        Usuario usuario = usuarioDao.findById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioDao.softDelete(id);
    }
    private Usuario paraEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }
    
    private UsuarioDTO paraDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        return dto;
    }
    public void criarUsuario(UsuarioDTO usuarioDTO) throws IllegalArgumentException {
        Usuario usuario = paraEntidade(usuarioDTO);
        this.criarUsuario(usuario);
    }
    public UsuarioDTO lerUsuarioComoDTO(int id) throws IllegalArgumentException {
        Usuario usuario = lerUsuario(id);
        return paraDTO(usuario);
    }
}

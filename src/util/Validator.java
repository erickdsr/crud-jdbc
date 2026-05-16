package util;

/**
 * Validador de dados. Valida nome, email e senha.
 * Lança exceção se dados forem inválidos.
 */
public class Validator {
    
    /**
     * Valida nome. Deve ter mínimo 3 caracteres.
     * @throws IllegalArgumentException se inválido
     */
    public static void validarNome(String nome) throws IllegalArgumentException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (nome.length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
    }
    /**
     * Valida email. Deve conter @.
     * @throws IllegalArgumentException se inválido
     */
    public static void validarEmail(String email) throws IllegalArgumentException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O email do usuário é obrigatório.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("O email deve conter o caractere @.");
        }
    }
    /**
     * Valida senha. Deve ter mínimo 6 caracteres.
     * @throws IllegalArgumentException se inválido
     */
    public static void validarSenha(String senha) throws IllegalArgumentException {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário é obrigatória.");
        }
        if (senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }
    }
}

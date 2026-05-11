package security;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class passwordUtil {
    
    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";
    
    /**
     * Faz hash da senha com um salt aleatório
     * @param password senha em texto plano
     * @return senha hashada com salt (formato: salt:hash)
     */
    public static String hashPassword(String password) {
        try {
            // Gera um salt aleatório
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Faz o hash combinando senha e salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(password.getBytes());
            md.update(salt);
            byte[] hashedPassword = md.digest();
            
            // Retorna salt + hash codificados em Base64 (separados por :)
            String hash = Base64.getEncoder().encodeToString(hashedPassword);
            String saltEncoded = Base64.getEncoder().encodeToString(salt);
            
            return saltEncoded + ":" + hash;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer hash da senha", e);
        }
    }
    
    /**
     * Verifica se a senha fornecida corresponde ao hash armazenado
     * @param password senha em texto plano
     * @param hashedPassword hash armazenado no formato (salt:hash)
     * @return true se a senha corresponde ao hash
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        try {
            // Separa o salt do hash
            String[] parts = hashedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            String saltEncoded = parts[0];
            String storedHash = parts[1];
            
            // Decodifica o salt
            byte[] salt = Base64.getDecoder().decode(saltEncoded);
            
            // Faz o hash da senha fornecida com o salt
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(password.getBytes());
            md.update(salt);
            byte[] hashedPasswordBytes = md.digest();
            String computedHash = Base64.getEncoder().encodeToString(hashedPasswordBytes);
            
            // Compara com o hash armazenado
            return computedHash.equals(storedHash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar a senha", e);
        }
    }
    
}

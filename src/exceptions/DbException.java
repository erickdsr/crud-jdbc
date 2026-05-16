package exceptions;


 //Exceção de banco de dados. Lançada quando ocorre erro em operações SQL.
public class DbException extends RuntimeException {
    
    
     //Construtor com mensagem.    
    public DbException(String message) {
        super(message);
    }

    
     //Construtor com mensagem e causa.
    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}

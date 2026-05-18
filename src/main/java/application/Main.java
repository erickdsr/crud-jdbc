package application;

import controller.UsuarioController;
import java.sql.SQLException;


  //Classe principal que inicia a aplicação.
 //Responsável por criar o controller e executar operações.
 
public class Main { 
    
    
      //Método principal. Instancia o controller e executa operações.
      //Trata exceções de SQL e encerra graciosamente.
     
    public static void main(String[] args) {
        try {
            UsuarioController controller = new UsuarioController();
            controller.executar();
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        }
    }
}

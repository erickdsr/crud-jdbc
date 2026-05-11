package application;

import controller.UsuarioController;
import java.sql.SQLException;

public class Main { 
    
    public static void main(String[] args) {
        try {
            UsuarioController controller = new UsuarioController();
            controller.executar();
            
        } catch (SQLException e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        }
    }
}

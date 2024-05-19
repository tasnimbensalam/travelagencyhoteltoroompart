package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	  private static final String URL = "jdbc:mysql://localhost:3306/travelagency";
	    
	      
	    public static Connection obtenirConnexion() {
	    	Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection( URL ,"root","");
	            System.out.println("Connexion établie avec succès !");
	        } catch (ClassNotFoundException e) {
	            System.err.println("Erreur de chargement du driver MySQL : " + e.getMessage());
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
	        }
	        return connection;
	    }


	    public static void close(Connection connection) {
	        if (connection != null) {
	            try {
	                connection.close();
	                System.out.println("Connection closed successfully!");
	            } catch (SQLException e) {
	                System.err.println("Error closing the connection: " + e.getMessage());
	            }
	        }
	    }
		  
}

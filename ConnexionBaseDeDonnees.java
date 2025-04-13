import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBaseDeDonnees {

    // Modifiez l'URL de connexion avec les bonnes informations (nom de la base, utilisateur, mot de passe)
    private final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
    private final String USER = "root"; // Remplacez par votre utilisateur MySQL
    private final String PASSWORD = "asterix2002"; // Remplacez par votre mot de passe MySQL

    // Méthode pour obtenir la connexion
    public Connection obtenirConnexion() throws SQLException {
        try {
            // Chargez le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");  // Important pour charger le driver JDBC
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Le driver MySQL n'a pas pu être trouvé.");
        }
    }
}

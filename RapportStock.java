import java.sql.*;

public class RapportStock {

    public String genererRapportStock() {
        StringBuilder rapport = new StringBuilder();
        rapport.append("Rapport sur l'état du stock:\n\n");

        // Connexion à la base de données pour récupérer les informations complètes des produits
        try (Connection connection = new ConnexionBaseDeDonnees().obtenirConnexion()) {
            if (connection != null) {
                String query = "SELECT id_produit, nom_produit, description, prix, quantite, date_ajout FROM produits";
                try (PreparedStatement stmt = connection.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        int idProduit = rs.getInt("id_produit");
                        String nomProduit = rs.getString("nom_produit");
                        String description = rs.getString("description");
                        double prix = rs.getDouble("prix");
                        int quantite = rs.getInt("quantite");
                        String dateAjout = rs.getString("date_ajout");

                        rapport.append("ID Produit: ").append(idProduit)
                                .append("\nNom du Produit: ").append(nomProduit)
                                .append("\nDescription: ").append(description)
                                .append("\nPrix: ").append(prix)
                                .append("\nQuantité en Stock: ").append(quantite)
                                .append("\nDate d'Ajout: ").append(dateAjout)
                                .append("\n---------------------------------------------\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    rapport.append("Erreur lors de la récupération des données de la base de données.");
                }
            } else {
                rapport.append("Connexion à la base de données échouée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rapport.append("Erreur de connexion à la base de données.");
        }

        return rapport.toString();
    }
}

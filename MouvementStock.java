import java.sql.*;

public class MouvementStock {
    private ConnexionBaseDeDonnees connexionBD;

    public MouvementStock() {
        this.connexionBD = new ConnexionBaseDeDonnees();
    }

    // Enregistrer un mouvement de stock
    public boolean ajouterMouvement(int idProduit, int quantiteMouvement, String typeMouvement) {
        String requete = "INSERT INTO mouvements (id_produit, quantite_mouvement, type_mouvement, date_mouvement) VALUES (?, ?, ?, CURDATE())";
        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, idProduit);
            statement.setInt(2, quantiteMouvement);
            statement.setString(3, typeMouvement);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mettre à jour la quantité de stock après un mouvement
    public boolean mettreAJourStock(int idProduit, int quantiteMouvement, String typeMouvement) {
        String requete = "";
        if (typeMouvement.equals("entrée")) {
            requete = "UPDATE produits SET quantite = quantite + ? WHERE id_produit = ?";
        } else if (typeMouvement.equals("sortie")) {
            requete = "UPDATE produits SET quantite = quantite - ? WHERE id_produit = ?";
        }

        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, quantiteMouvement);
            statement.setInt(2, idProduit);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionStock {
    private ConnexionBaseDeDonnees connexionBD;

    public GestionStock() {
        this.connexionBD = new ConnexionBaseDeDonnees();
    }

    // Ajouter un produit
    public boolean ajouterProduit(String nom, String description, double prix, int quantite) {
        if (nom.isEmpty() || prix <= 0 || quantite <= 0) {
            System.out.println("Erreur : les données du produit sont invalides.");
            return false;
        }

        String requete = "INSERT INTO produits (nom_produit, description, prix, quantite, date_ajout) VALUES (?, ?, ?, ?, CURDATE())";
        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setDouble(3, prix);
            statement.setInt(4, quantite);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Modifier un produit
    public boolean modifierProduit(int idProduit, String nom, String description, double prix, int quantite) {
        if (idProduit <= 0 || prix <= 0 || quantite <= 0) {
            System.out.println("Erreur : les données sont invalides.");
            return false;
        }

        // Vérifier si le produit existe
        Produit produit = obtenirProduitParId(idProduit);
        if (produit == null) {
            System.out.println("Produit non trouvé.");
            return false;
        }

        String requete = "UPDATE produits SET nom_produit = ?, description = ?, prix = ?, quantite = ? WHERE id_produit = ?";
        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setDouble(3, prix);
            statement.setInt(4, quantite);
            statement.setInt(5, idProduit);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Supprimer un produit en fonction de l'ID et de la quantité à supprimer
    public boolean supprimerProduit(int idProduit, int quantiteASupprimer) {
        // Vérifier si la quantité à supprimer est valide
        String requeteProduit = "SELECT quantite FROM produits WHERE id_produit = ?";
        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requeteProduit)) {
            statement.setInt(1, idProduit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int quantiteDisponible = resultSet.getInt("quantite");

                if (quantiteASupprimer > quantiteDisponible) {
                    System.out.println("Quantité à supprimer est supérieure à la quantité en stock.");
                    return false;
                }

                // Si la quantité à supprimer est valide, procéder à la mise à jour de la quantité
                String requeteSuppression = "UPDATE produits SET quantite = quantite - ? WHERE id_produit = ?";
                try (PreparedStatement updateStatement = connexion.prepareStatement(requeteSuppression)) {
                    updateStatement.setInt(1, quantiteASupprimer);
                    updateStatement.setInt(2, idProduit);
                    int result = updateStatement.executeUpdate();
                    if (result > 0) {
                        System.out.println("Produit mis à jour (quantité réduite) avec succès.");
                        return true;
                    }
                }
            } else {
                System.out.println("Produit non trouvé.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Ajouter un mouvement de stock (entrée/sortie)
    public boolean ajouterMouvement(int idProduit, int quantiteMouvement, String typeMouvement) {
        // Vérifier si le produit existe
        Produit produit = obtenirProduitParId(idProduit);
        if (produit == null) {
            System.out.println("Produit non trouvé.");
            return false;
        }

        // Vérifier que la quantité est valide
        if (quantiteMouvement <= 0) {
            System.out.println("Erreur : Quantité invalide.");
            return false;
        }

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

    // Générer un rapport sur l'état du stock
    public String genererRapportStock() {
        StringBuilder rapport = new StringBuilder();
        rapport.append("Rapport sur l'état du stock:\n\n");

        try (Connection connection = new ConnexionBaseDeDonnees().obtenirConnexion()) {
            if (connection != null) {
                String query = "SELECT nom_produit, quantite FROM produits";
                try (PreparedStatement stmt = connection.prepareStatement(query);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        String nomProduit = rs.getString("nom_produit");
                        int quantite = rs.getInt("quantite");
                        rapport.append("Produit: ").append(nomProduit)
                                .append(", Quantité en stock: ").append(quantite).append("\n");
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

    // Méthode pour obtenir un produit par ID
    public Produit obtenirProduitParId(int idProduit) {
        String requete = "SELECT * FROM produits WHERE id_produit = ?";
        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, idProduit);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Produit(
                            rs.getInt("id_produit"),
                            rs.getString("nom_produit"),
                            rs.getString("description"),
                            rs.getDouble("prix"),
                            rs.getInt("quantite"),
                            rs.getString("date_ajout")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si aucun produit n'est trouvé
    }

    // Méthode pour obtenir tous les produits
    public List<Produit> obtenirTousLesProduits() {
        List<Produit> produits = new ArrayList<>();
        String requete = "SELECT * FROM produits";

        try (Connection connexion = connexionBD.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int idProduit = rs.getInt("id_produit");
                String nomProduit = rs.getString("nom_produit");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("quantite");
                String dateAjout = rs.getString("date_ajout");

                Produit produit = new Produit(idProduit, nomProduit, description, prix, quantite, dateAjout);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }
}

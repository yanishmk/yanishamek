public class Produit {
    private int idProduit;
    private String nomProduit;
    private String description;
    private double prix;
    private int quantite;
    private String dateAjout; // Date d'ajout

    // Constructeur avec la date d'ajout
    public Produit(int idProduit, String nomProduit, String description, double prix, int quantite, String dateAjout) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    @Override
    public String toString() {
        return "Produit{idProduit=" + idProduit + ", nomProduit='" + nomProduit + "', description='" + description + "', prix=" + prix + ", quantite=" + quantite + ", dateAjout='" + dateAjout + "'}";
    }
}

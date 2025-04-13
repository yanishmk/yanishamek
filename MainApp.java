import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionStock gestionStock = new GestionStock();
        MouvementStock mouvementStock = new MouvementStock();
        RapportStock rapportStock = new RapportStock();

        while (true) {
            System.out.println("\nMenu de gestion de stock:");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Modifier un produit");
            System.out.println("3. Supprimer un produit");
            System.out.println("4. Générer un rapport");
            System.out.println("5. Enregistrer un mouvement de stock");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option: ");

            // Boucle pour gérer l'entrée incorrecte du choix
            int choix = -1;
            while (choix < 1 || choix > 6) {
                try {
                    choix = scanner.nextInt();
                    if (choix < 1 || choix > 6) {
                        System.out.println("Option invalide. Essayez encore.");
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Erreur : Veuillez entrer un nombre valide.");
                    scanner.nextLine(); // Consomme l'entrée incorrecte
                }
            }

            scanner.nextLine(); // Consomme la nouvelle ligne

            switch (choix) {
                case 1:
                    // Ajouter un produit
                    System.out.print("Nom du produit: ");
                    String nomProduit = scanner.nextLine();
                    System.out.print("Description du produit: ");
                    String description = scanner.nextLine();

                    double prix = 0;
                    while (prix <= 0) {
                        try {
                            System.out.print("Prix du produit: ");
                            prix = scanner.nextDouble();
                            if (prix <= 0) {
                                System.out.println("Le prix doit être supérieur à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer un prix valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    int quantite = 0;
                    while (quantite <= 0) {
                        try {
                            System.out.print("Quantité du produit: ");
                            quantite = scanner.nextInt();
                            if (quantite <= 0) {
                                System.out.println("La quantité doit être supérieure à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer une quantité valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    gestionStock.ajouterProduit(nomProduit, description, prix, quantite);
                    System.out.println("Produit ajouté avec succès!");
                    break;

                case 2:
                    // Modifier un produit
                    int idModifier = -1;
                    while (idModifier <= 0) {
                        try {
                            System.out.print("Entrez l'ID du produit à modifier: ");
                            idModifier = scanner.nextInt();
                            if (idModifier <= 0) {
                                System.out.println("L'ID doit être supérieur à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer un ID valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    scanner.nextLine(); // Consomme la nouvelle ligne
                    Produit produitModifie = gestionStock.obtenirProduitParId(idModifier);
                    if (produitModifie != null) {
                        System.out.println("Produit actuel: " + produitModifie);
                        System.out.print("Nouveau nom (laisser vide pour conserver): ");
                        String nouveauNom = scanner.nextLine();
                        if (!nouveauNom.isEmpty()) produitModifie.setNomProduit(nouveauNom);

                        System.out.print("Nouvelle description (laisser vide pour conserver): ");
                        String nouvelleDescription = scanner.nextLine();
                        if (!nouvelleDescription.isEmpty()) produitModifie.setDescription(nouvelleDescription);

                        double nouveauPrix = produitModifie.getPrix();
                        while (nouveauPrix <= 0) {
                            System.out.print("Nouveau prix (laisser vide pour conserver): ");
                            try {
                                String prixInput = scanner.nextLine();
                                if (!prixInput.isEmpty()) {
                                    nouveauPrix = Double.parseDouble(prixInput);
                                    if (nouveauPrix <= 0) {
                                        System.out.println("Le prix doit être supérieur à 0.");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Erreur : Veuillez entrer un prix valide.");
                            }
                        }

                        int nouvelleQuantite = produitModifie.getQuantite();
                        while (nouvelleQuantite <= 0) {
                            System.out.print("Nouvelle quantité (laisser vide pour conserver): ");
                            try {
                                String quantiteInput = scanner.nextLine();
                                if (!quantiteInput.isEmpty()) {
                                    nouvelleQuantite = Integer.parseInt(quantiteInput);
                                    if (nouvelleQuantite <= 0) {
                                        System.out.println("La quantité doit être supérieure à 0.");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Erreur : Veuillez entrer une quantité valide.");
                            }
                        }

                        gestionStock.modifierProduit(produitModifie.getIdProduit(), produitModifie.getNomProduit(),
                                produitModifie.getDescription(), nouveauPrix, nouvelleQuantite);
                        System.out.println("Produit modifié avec succès!");
                    } else {
                        System.out.println("Produit non trouvé!");
                    }
                    break;

                case 3:
                    // Supprimer un produit
                    int idSupprimer = -1;
                    while (idSupprimer <= 0) {
                        try {
                            System.out.print("Entrez l'ID du produit à supprimer: ");
                            idSupprimer = scanner.nextInt();
                            if (idSupprimer <= 0) {
                                System.out.println("L'ID doit être supérieur à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer un ID valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    scanner.nextLine(); // Consomme la nouvelle ligne
                    Produit produitASupprimer = gestionStock.obtenirProduitParId(idSupprimer);
                    if (produitASupprimer != null) {
                        System.out.println("Produit à supprimer: " + produitASupprimer);

                        int quantiteASupprimer = -1;
                        while (quantiteASupprimer <= 0 || quantiteASupprimer > produitASupprimer.getQuantite()) {
                            System.out.print("Combien de produits voulez-vous supprimer? ");
                            try {
                                quantiteASupprimer = scanner.nextInt();
                                if (quantiteASupprimer <= 0) {
                                    System.out.println("La quantité doit être supérieure à 0.");
                                } else if (quantiteASupprimer > produitASupprimer.getQuantite()) {
                                    System.out.println("Quantité à supprimer supérieure à la quantité en stock.");
                                }
                            } catch (Exception e) {
                                System.out.println("Erreur : Veuillez entrer une quantité valide.");
                                scanner.nextLine(); // Consomme l'entrée non valide
                            }
                        }

                        boolean suppressionReussie = gestionStock.supprimerProduit(idSupprimer, quantiteASupprimer);
                        if (suppressionReussie) {
                            System.out.println("Produit supprimé avec succès!");
                        } else {
                            System.out.println("Erreur lors de la suppression du produit.");
                        }
                    } else {
                        System.out.println("Produit non trouvé!");
                    }
                    break;

                case 4:
                    // Générer un rapport
                    List<Produit> produits = gestionStock.obtenirTousLesProduits();
                    if (!produits.isEmpty()) {
                        System.out.println("Rapport sur l'état du stock :");
                        for (Produit produit : produits) {
                            System.out.println("Produit : " + produit.getNomProduit() +
                                    ", Quantité en stock : " + produit.getQuantite());
                        }
                    } else {
                        System.out.println("Aucun produit dans le stock.");
                    }
                    break;

                case 5:
                    // Enregistrer un mouvement de stock
                    int idProduit = -1;
                    while (idProduit <= 0) {
                        try {
                            System.out.print("Entrez l'ID du produit: ");
                            idProduit = scanner.nextInt();
                            if (idProduit <= 0) {
                                System.out.println("L'ID doit être supérieur à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer un ID valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    int quantiteMouvement = -1;
                    while (quantiteMouvement <= 0) {
                        try {
                            System.out.print("Quantité du mouvement: ");
                            quantiteMouvement = scanner.nextInt();
                            if (quantiteMouvement <= 0) {
                                System.out.println("La quantité doit être supérieure à 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur : Veuillez entrer une quantité valide.");
                            scanner.nextLine(); // Consomme l'entrée non valide
                        }
                    }

                    scanner.nextLine(); // Consomme la nouvelle ligne
                    String typeMouvement = "";
                    while (!typeMouvement.equals("entrée") && !typeMouvement.equals("sortie")) {
                        System.out.print("Type de mouvement (entrée/sortie): ");
                        typeMouvement = scanner.nextLine().toLowerCase();
                        if (!typeMouvement.equals("entrée") && !typeMouvement.equals("sortie")) {
                            System.out.println("Erreur : Veuillez entrer 'entrée' ou 'sortie'.");
                        }
                    }

                    boolean mouvementReussi = mouvementStock.ajouterMouvement(idProduit, quantiteMouvement, typeMouvement);
                    boolean miseAJourReussie = mouvementStock.mettreAJourStock(idProduit, quantiteMouvement, typeMouvement);
                    if (mouvementReussi && miseAJourReussie) {
                        System.out.println("Mouvement de stock enregistré avec succès!");
                    } else {
                        System.out.println("Erreur lors de l'enregistrement du mouvement.");
                    }
                    break;

                case 6:
                    // Quitter
                    System.out.println("Au revoir!");
                    return;

                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }
}

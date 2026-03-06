package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

public class Test {

    public static void main(String[] args) {

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        try {
           Categorie catInfo = new Categorie("INFO", "Informatique");
            categorieService.create(catInfo);

          Produit p1 = new Produit("ES12", 120.0f, catInfo);
            Produit p2 = new Produit("ZR85", 100.0f, catInfo);
            Produit p3 = new Produit("EE85", 200.0f, catInfo);
            
            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
            Date dateCommande = sdf.parse("14 mars 2013");
            Commande commande = new Commande(dateCommande);
            commandeService.create(commande);

            ligneService.create(new LigneCommandeProduit(7, p1, commande));
            ligneService.create(new LigneCommandeProduit(14, p2, commande));
            ligneService.create(new LigneCommandeProduit(5, p3, commande));

            
            System.out.println("\n--- TEST 1 : Affichage de la commande ---");
            Commande commandeEnBase = commandeService.findById(commande.getId());
            commandeService.afficherProduitsParCommande(commandeEnBase);

            System.out.println("\n--- TEST 2 : Produits dont le prix est > 100 DH ---");
            for(Produit p : produitService.findProduitsChers()) {
                System.out.println("- Référence : " + p.getReference() + " | Prix : " + p.getPrix() + " DH");
            }

        } catch (Exception e) {
            System.err.println("Erreur durant le test : " + e.getMessage());
            e.printStackTrace();
        }
        
        System.exit(0);
    }
}
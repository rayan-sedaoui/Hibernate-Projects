package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

// VOICI LES IMPORTS QUI TE MANQUAIENT !
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.Etat; // Import de l'énumération
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

public class Test {
    public static void main(String[] args) {
        
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMoisLettre = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE); 

        try {
            // 1. Création de l'employé
            Employe emp = new Employe("Alaoui", "Ahmed", "0600000000");
            es.create(emp);

            // 2. Création du projet
            Projet projet = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("14/06/2013"), emp);
            ps.create(projet);

            // 3. Création des tâches avec l'énumération Etat (Correction du t1 en double)
            Tache t1 = new Tache("Analyse", sdf.parse("01/02/2013"), sdf.parse("25/02/2013"), 1500, projet);
            t1.setEtat(Etat.TERMINEE); // On utilise l'état ici proprement !
            
            Tache t2 = new Tache("Conception", sdf.parse("01/03/2013"), sdf.parse("20/03/2013"), 2000, projet);
            t2.setEtat(Etat.EN_COURS);
            
            Tache t3 = new Tache("Développement", sdf.parse("01/04/2013"), sdf.parse("30/04/2013"), 3500, projet);
            t3.setEtat(Etat.PLANIFIEE);
            
            ts.create(t1);
            ts.create(t2);
            ts.create(t3);

            // 4. L'employé réalise les tâches
            EmployeTache et1 = new EmployeTache(sdf.parse("10/02/2013"), sdf.parse("20/02/2013"), emp, t1);
            EmployeTache et2 = new EmployeTache(sdf.parse("10/03/2013"), sdf.parse("15/03/2013"), emp, t2);
            EmployeTache et3 = new EmployeTache(sdf.parse("10/04/2013"), sdf.parse("25/04/2013"), emp, t3);
            ets.create(et1);
            ets.create(et2);
            ets.create(et3);

            // =========================================================
            // AFFICHAGE DU RÉSULTAT
            // =========================================================
            System.out.println("Projet : " + projet.getId() + "\tNom : " + projet.getNom() + "\tDate début : " + sdfMoisLettre.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            
            System.out.printf("%-5s %-18s %-20s %-20s\n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");

            List<EmployeTache> listeTachesRealisees = ps.getTachesRealiseesAvecDates(projet);
            
            if (listeTachesRealisees != null) {
                for (EmployeTache et : listeTachesRealisees) {
                    System.out.printf("%-5d %-18s %-20s %-20s\n", 
                        et.getTache().getId(), 
                        et.getTache().getNom(), 
                        sdf.format(et.getDateDebutReelle()), 
                        sdf.format(et.getDateFinReelle())
                    );
                }
            }

        } catch (ParseException e) {
            System.out.println("Erreur dans le format des dates !");
            e.printStackTrace();
        }
    }
}
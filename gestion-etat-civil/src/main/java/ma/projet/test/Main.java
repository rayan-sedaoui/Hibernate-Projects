package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Femme f1 = new Femme("SAADI", "Amina", "0600000001", "Rabat", sdf.parse("01/01/1985"));
        Femme f2 = new Femme("RAMI", "Ranya", "0600000002", "Casa", sdf.parse("15/05/1988"));
        Femme f3 = new Femme("TALEB", "Sanae", "0600000003", "Fes", sdf.parse("20/07/1990"));
        Femme f4 = new Femme("KADIRI", "Maha", "0600000004", "Tanger", sdf.parse("11/11/1992"));
        Femme f5 = new Femme("LOUDIY", "Najat", "0600000005", "Agadir", sdf.parse("05/09/1980"));
        Femme f6 = new Femme("CHRAIBI", "Kenza", "0600000006", "Marrakech", sdf.parse("30/03/1995"));
        Femme f7 = new Femme("BENNIS", "Hiba", "0600000007", "Rabat", sdf.parse("14/02/1991"));
        Femme f8 = new Femme("TAZI", "Salma", "0600000008", "Casa", sdf.parse("19/08/1989"));
        Femme f9 = new Femme("EL FASSI", "Zineb", "0600000009", "Fes", sdf.parse("25/12/1994"));
        Femme f10 = new Femme("JAZOULI", "Fatima", "0600000010", "Tanger", sdf.parse("08/10/1987"));

        fs.create(f1); fs.create(f2); fs.create(f3); fs.create(f4); fs.create(f5);
        fs.create(f6); fs.create(f7); fs.create(f8); fs.create(f9); fs.create(f10);

        Homme h1 = new Homme("ALAMI", "Ali", "0611111111", "Rabat", sdf.parse("10/10/1980"));
        Homme h2 = new Homme("BOUZID", "Karim", "0622222222", "Casa", sdf.parse("05/05/1975"));
        Homme h3 = new Homme("SABRI", "Omar", "0633333333", "Fes", sdf.parse("12/12/1982"));
        Homme h4 = new Homme("NAJI", "Youssef", "0644444444", "Tanger", sdf.parse("22/04/1988"));
        Homme h5 = new Homme("HILALI", "Said", "0655555555", "Agadir", sdf.parse("18/06/1990"));

        hs.create(h1); hs.create(h2); hs.create(h3); hs.create(h4); hs.create(h5);
       
        Mariage m1 = new Mariage(sdf.parse("12/03/1990"), sdf.parse("12/03/1996"), 2, h1, f2);
        Mariage m2 = new Mariage(sdf.parse("12/03/2005"), null, 3, h1, f1);
        
        Mariage m3 = new Mariage(sdf.parse("01/01/2010"), null, 1, h2, f3);
        Mariage m4 = new Mariage(sdf.parse("15/06/2012"), sdf.parse("20/12/2015"), 0, h2, f4);
        Mariage m5 = new Mariage(sdf.parse("05/05/2016"), null, 2, h2, f5);
        Mariage m6 = new Mariage(sdf.parse("10/10/2020"), null, 1, h2, f6); // h2 a eu 4 femmes au total
        
        Mariage m7 = new Mariage(sdf.parse("10/05/2000"), null, 2, h3, f2);

        ms.create(m1); ms.create(m2); ms.create(m3); ms.create(m4); ms.create(m5); ms.create(m6); ms.create(m7);

        System.out.println("==========================================");
        
        System.out.println("a. Épouses de " + h1.getNom() + " entre 1980 et 2000 :");
        List<Femme> epouses = hs.getEpousesEntreDeuxDates(h1.getId(), sdf.parse("01/01/1980"), sdf.parse("31/12/2000"));
        for (Femme f : epouses) {
            System.out.println("- " + f.getNom() + " " + f.getPrenom());
        }

        System.out.println("\n------------------------------------------");
        
        int nbEnfants = fs.countEnfantsBetweenDates(f1.getId(), sdf.parse("01/01/2000"), sdf.parse("31/12/2023"));
        System.out.println("b. Nombre d'enfants de " + f1.getNom() + " " + f1.getPrenom() + " : " + nbEnfants);

        System.out.println("\n------------------------------------------");

        System.out.println("c. Femmes mariées 2 fois ou plus :");
        List<Femme> femmesMarieesPlusieursFois = fs.getFemmesMarieesDeuxFoisOuPlus();
        for (Femme f : femmesMarieesPlusieursFois) {
            System.out.println("- " + f.getNom() + " " + f.getPrenom());
        }

        System.out.println("\n------------------------------------------");

        int nbHommes4Femmes = hs.countHommesMariesAQuatreFemmes(sdf.parse("01/01/2000"), sdf.parse("31/12/2023"));
        System.out.println("d. Hommes mariés à 4 femmes : " + nbHommes4Femmes);

        System.out.println("\n------------------------------------------");

        System.out.println("e. Affichage détaillé des mariages :");
        hs.afficherMariages(h1.getId());
        
        System.out.println("==========================================");
        
        System.exit(0);
    }
}
package ma.projet.beans;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@NamedNativeQuery(
	    name = "Femme.countEnfantsBetweenDates",
	    query = "SELECT COALESCE(SUM(nbrEnfant), 0) FROM mariage WHERE femme_id = :femmeId AND dateDebut BETWEEN :d1 AND :d2"
	)
	@NamedQuery(
	    name = "Femme.mariedAtLeastTwice",
	    query = "SELECT m.femme FROM Mariage m GROUP BY m.femme HAVING COUNT(m.id) >= 2"
	)
public class Femme extends Personne {

    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
}
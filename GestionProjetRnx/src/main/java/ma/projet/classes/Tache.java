package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

@Entity
@NamedQuery(name = "Tache.prixSuperieur", query = "from Tache t where t.prix > :prix")
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private double prix;

    @Enumerated(EnumType.STRING)
    private Etat etat;
    
    // Relation : Plusieurs tâches appartiennent à un Projet
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    public Tache() {}

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    // Génère les Getters et Setters !
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
    public Etat getEtat() {
        return etat;
    }

    // Le Setter (c'est lui qui manquait !)
    public void setEtat(Etat etat) {
        this.etat = etat;
    }
}
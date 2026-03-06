package ma.projet.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

public class CommandeService implements IDao<Commande> {
    @Override
    public boolean create(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Commande findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Commande c = (Commande) session.get(Commande.class, id);
        session.close();
        return (Commande) c;
    }

    @Override
    public List<Commande> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Commande> cmds = session.createQuery("from Commande").list();
        session.close();
        return cmds;
    }

    public void afficherProduitsParCommande(Commande c) {
        System.out.println("Commande : " + c.getId() + "    Date : " + c.getDate());
        System.out.println("Liste des produits :");
        System.out.printf("%-12s %-8s %-10s\n", "Référence", "Prix", "Quantité");
        for (LigneCommandeProduit l : c.getLignes()) {
            System.out.printf("%-12s %-8s %-10s\n", 
                l.getProduit().getReference(), 
                l.getProduit().getPrix() + " DH", 
                l.getQuantite());
        }
    }
}
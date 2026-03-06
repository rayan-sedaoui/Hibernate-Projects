package ma.projet.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

public class ProduitService implements IDao<Produit> {
    @Override
    public boolean create(Produit o) {
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
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("from Produit").list();
        session.close();
        return produits;
    }

    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit p = (Produit) session.get(Produit.class, id);
        session.close();
        return p;
    }

    public List<Produit> findProduitsChers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.getNamedQuery("findPriceGreaterThan100").list();
        session.close();
        return produits;
    }
}
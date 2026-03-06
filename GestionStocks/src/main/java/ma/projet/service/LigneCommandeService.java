package ma.projet.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    @Override
    public boolean create(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        LigneCommandeProduit l = (LigneCommandeProduit) session.get(LigneCommandeProduit.class, id);
        session.close();
        return l;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommandeProduit> lignes = session.createQuery("from LigneCommandeProduit").list();
        session.close();
        return lignes;
    }
}
package ma.projet.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

public class CategorieService implements IDao<Categorie> {
    @Override
    public boolean create(Categorie o) {
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

    public Categorie findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categorie c = (Categorie) session.get(Categorie.class, id);
        session.close();
        return c;
    }

    @Override
    public List<Categorie> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> cats = session.createQuery("from Categorie").list();
        session.close();
        return cats;
    }
}

package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
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
    public boolean update(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
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
    public boolean delete(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
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
    public Femme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Femme femme = session.get(Femme.class, id);
        session.close();
        return femme;
    }

    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Femme> femmes = session.createQuery("from Femme", Femme.class).list();
        session.close();
        return femmes;
    }
    public int countEnfantsBetweenDates(int femmeId, java.util.Date d1, java.util.Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Object result = session.getNamedNativeQuery("Femme.countEnfantsBetweenDates")
                .setParameter("femmeId", femmeId)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .uniqueResult();
        session.close();
        return result != null ? ((Number) result).intValue() : 0;
    }

    public List<Femme> getFemmesMarieesDeuxFoisOuPlus() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Femme> femmes = session.getNamedQuery("Femme.mariedAtLeastTwice").list();
        session.close();
        return femmes;
    }
}
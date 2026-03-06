package ma.projet.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(Projet o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(Projet o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Projet findById(int id) {
        Session session = null;
        Projet e = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Projet) session.get(Projet.class, id);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List findAll() {
        Session session = null;
        List<Projet> liste = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            liste = session.createQuery("from Projet").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return liste;
    }

    @SuppressWarnings("unchecked")
    public List<Tache> getTachesPlanifiees(Projet p) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            taches = session.createQuery("from Tache t where t.projet = :proj")
                            .setParameter("proj", p)
                            .list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    @SuppressWarnings("unchecked")
    public List<EmployeTache> getTachesRealiseesAvecDates(Projet p) {
        Session session = null;
        List<EmployeTache> employeTaches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            employeTaches = session.createQuery("from EmployeTache et where et.tache.projet = :proj")
                                   .setParameter("proj", p)
                                   .list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return employeTaches;
    }

	@Override
	public Projet getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Projet> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
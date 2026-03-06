package ma.projet.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.EmployeTache;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache o) {
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
    public boolean update(EmployeTache o) {
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
    public boolean delete(EmployeTache o) {
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
    public EmployeTache findById(int id) {
        Session session = null;
        EmployeTache e = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (EmployeTache) session.get(EmployeTache.class, id);
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
    public List<EmployeTache> findAll() {
        Session session = null;
        List<EmployeTache> liste = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            liste = session.createQuery("from EmployeTache").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return liste;
    }

	@Override
	public EmployeTache getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeTache> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
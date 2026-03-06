package ma.projet.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        Session session = null;
        Employe e = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Employe) session.get(Employe.class, id);
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
    public List<Employe> findAll() {
        Session session = null;
        List<Employe> liste = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            liste = session.createQuery("from Employe").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return liste;
    }

    @SuppressWarnings("unchecked")
    public List<Tache> getTachesRealiseesParEmploye(Employe e) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            taches = session.createQuery("select et.tache from EmployeTache et where et.employe = :emp")
                            .setParameter("emp", e)
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
    public List<Projet> getProjetsGeresParEmploye(Employe e) {
        Session session = null;
        List<Projet> projets = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            projets = session.createQuery("from Projet p where p.chefProjet = :emp")
                             .setParameter("emp", e)
                             .list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return projets;
    }

	@Override
	public Employe getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employe> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
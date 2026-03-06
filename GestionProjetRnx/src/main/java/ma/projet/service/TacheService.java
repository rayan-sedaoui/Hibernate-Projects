package ma.projet.service;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Tache;

// Remarque bien le <Tache> ici, c'est ça qui corrige ton erreur !
public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(Tache o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(Tache o) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Tache findById(int id) {
        Session session = null;
        Tache e = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            e = (Tache) session.get(Tache.class, id);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tache> findAll() {
        Session session = null;
        List<Tache> liste = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            liste = session.createQuery("from Tache").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return liste;
    }

    // ==========================================
    // MÉTHODES SPÉCIFIQUES POUR L'EXERCICE
    // ==========================================

    @SuppressWarnings("unchecked")
    public List<Tache> getTachesPrixSuperieur(double montant) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            // Utilisation de la NamedQuery définie dans la classe Tache
            taches = session.getNamedQuery("Tache.prixSuperieur")
                            .setParameter("prix", montant)
                            .list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    @SuppressWarnings("unchecked")
    public List<Tache> getTachesRealiseesEntreDeuxDates(Date d1, Date d2) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            taches = session.createQuery("select et.tache from EmployeTache et where et.dateDebutReelle between :d1 and :d2")
                            .setParameter("d1", d1)
                            .setParameter("d2", d2)
                            .list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

	@Override
	public Tache getById(int id) {
		return null;
	}

	@Override
	public List<Tache> getAll() {
		return null;
	}
}
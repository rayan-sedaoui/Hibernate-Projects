package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Homme homme = session.get(Homme.class, id);
        session.close();
        return homme;
    }

    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Homme> hommes = session.createQuery("from Homme", Homme.class).list();
        session.close();
        return hommes;
    }
 // 1. Afficher les épouses d'un homme entre deux dates
    public List<ma.projet.beans.Femme> getEpousesEntreDeuxDates(int hommeId, java.util.Date d1, java.util.Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ma.projet.beans.Femme> epouses = session.createQuery(
                "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateDebut BETWEEN :d1 AND :d2", 
                ma.projet.beans.Femme.class)
                .setParameter("hommeId", hommeId)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .list();
        session.close();
        return epouses;
    }

    // 2. API Criteria : Nombre d'hommes mariés à 4 femmes entre deux dates
    public int countHommesMariesAQuatreFemmes(java.util.Date d1, java.util.Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        javax.persistence.criteria.CriteriaBuilder cb = session.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Homme> cq = cb.createQuery(Homme.class);
        javax.persistence.criteria.Root<ma.projet.beans.Mariage> root = cq.from(ma.projet.beans.Mariage.class);

        cq.select(root.get("homme"))
          .where(cb.between(root.get("dateDebut"), d1, d2))
          .groupBy(root.get("homme"))
          .having(cb.equal(cb.count(root.get("femme")), 4));

        List<Homme> hommes = session.createQuery(cq).getResultList();
        session.close();
        return hommes.size();
    }

    // 3. Afficher les mariages d'un homme donné (avec l'affichage demandé dans l'exemple)
    public void afficherMariages(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Homme homme = session.get(Homme.class, hommeId);
        
        if (homme == null) {
            System.out.println("Homme introuvable.");
            session.close();
            return;
        }

        List<ma.projet.beans.Mariage> mariages = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId", ma.projet.beans.Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();

        System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
        System.out.println("\nMariages En Cours :");
        int cptEnCours = 1;
        int cptEchoues = 1;
        
        StringBuilder echoues = new StringBuilder("\nMariages échoués :\n");

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        for (ma.projet.beans.Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %-15s Date Début : %-15s Nbr Enfants : %d\n",
                        cptEnCours++, m.getFemme().getNom() + " " + m.getFemme().getPrenom(),
                        sdf.format(m.getDateDebut()), m.getNbrEnfant());
            } else {
                echoues.append(String.format("%d. Femme : %-15s Date Début : %-15s\nDate Fin : %-15s Nbr Enfants : %d\n",
                        cptEchoues++, m.getFemme().getNom() + " " + m.getFemme().getPrenom(),
                        sdf.format(m.getDateDebut()), sdf.format(m.getDateFin()), m.getNbrEnfant()));
            }
        }
        
        System.out.println(echoues.toString());
        session.close();
    }
}
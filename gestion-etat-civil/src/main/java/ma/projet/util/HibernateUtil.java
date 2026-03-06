package ma.projet.util;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Personne;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

               Properties settings = new Properties();
                try (InputStream is = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (is != null) {
                        settings.load(is);
                    } else {
                        System.err.println("Fichier application.properties introuvable !");
                    }
                }

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Personne.class);
                configuration.addAnnotatedClass(Homme.class);
                configuration.addAnnotatedClass(Femme.class);
                configuration.addAnnotatedClass(Mariage.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
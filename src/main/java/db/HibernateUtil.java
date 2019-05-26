package db;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
 
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();

        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

/*Hibernate 4?
private static SessionFactory sessionFactory;

public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry
            = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
         
        // builds a session factory from the service registry
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
    }
     
    return sessionFactory;
} */
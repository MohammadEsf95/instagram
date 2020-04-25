package main;

import config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Welcome {
    public static void start(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("Welcome to Instagram!");

        session.getTransaction().commit();
        session.close();
    }
}

package crudrepositories;

import config.HibernateUtil;
import entities.Comment;
import entities.Post;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class CommentCrud {
    SessionFactory sessionFactory;
    Session session;

    public void addComment(User user, String text, Long postId){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        Post post = session.load(Post.class,postId);
        Comment comment = Comment.builder().writer(user).text(text).build();
        session.save(comment);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Long id){
        sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query =session.createQuery("delete from Comment where id=:id")
                .setParameter("id",id);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();

    }
}

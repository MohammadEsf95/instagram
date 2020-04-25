package crudrepositories;

import config.HibernateUtil;
import entities.Post;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PostCrud {
    SessionFactory sessionFactory;
    Session session;

    public void newPost(User user){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        Scanner scanner = new Scanner(System.in);
        String title = scanner.next();
        String tag = scanner.next();
        Post post = Post.builder().user(user).title(title).tag(tag).build();
        session.save(post);
        session.getTransaction().commit();
        session.close();
    }

    public void showAllPosts(User user) {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.load(User.class, user);
        if(user.getPosts().size()==0){
            System.out.println("No posts");
        }else {
            for (Post post:user.getPosts()){
                System.out.println(post.toString());
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Long id){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Post where id=:id")
                .setParameter("id",id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void edit(Long id,String editedItem, String newValue){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("update Post set " + editedItem + "=: value where id =:id")
                .setParameter("value",newValue)
                .setParameter("id",id);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    public void like(Long postOwnerId, Long postLikerId, Long postId){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        Post post = session.load(Post.class,postId);
        User liker = session.load(User.class,postLikerId);
        User owner = session.load(User.class,postOwnerId);
        Set<User> likers = post.getLikerUsers();
        likers.add(liker);
        post.setLikerUsers(likers);
        post.setLikes(post.getLikes()+1);
        System.out.println(post.getLikes());

        session.getTransaction().commit();
        session.close();
    }

    public void getMostLikedPosts(Long postNumber){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        List<Post> posts = session.createQuery("from Post ").list();
        posts.sort(Post::compareTo);
        posts.stream().limit(postNumber).forEach(System.out::println);

        session.getTransaction().commit();
        session.close();
    }
}

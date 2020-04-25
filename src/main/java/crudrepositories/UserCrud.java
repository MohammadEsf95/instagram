package crudrepositories;

import config.HibernateUtil;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UserCrud {
    SessionFactory sessionFactory;
    Session session;

    public void signUp(String username, String password){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        User user = User.builder().username(username).password(password).build();

        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public User login(String username, String password){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        User user = null;
        List<User> users = session.createQuery("from User where username =: username")
                .setParameter("username",username)
                .list();
        if(users.size()>0){
            if(users.get(0).getPassword().equals(password)){
                user = users.get(0);
            }else {
                System.out.println("incorrect password!");
            }
        }else {
            System.out.println("user does not exist");
        }

        session.getTransaction().commit();
        session.close();
        return user;
    }

    public void changePassword(User user){
        Scanner scanner = new Scanner(System.in);
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        System.out.println("Enter new password: ");
        String newPass = scanner.nextLine();
        Query query = session.createQuery("update User set password =: password where username =: username")
                .setParameter("username",user.getUsername())
                .setParameter("password",newPass);

        query.executeUpdate();
        System.out.println("password has successfully changed!");
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Long id){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("delete User where id =: id")
                .setParameter("id",id);

        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void search(String username){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> searched = session.createQuery("from User where username like '%" + username +"%'")
                .list();
        if(searched.size()>0){
            System.out.println(searched.get(0).getUsername());
        }else {
            System.out.println("not found");
        }
        session.getTransaction().commit();
        session.close();
    }

    public void follow(Long id, Long followerId){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        User follower = session.load(User.class,followerId);
        User userToFollow = session.load(User.class,id);
        follower.getFollowings().add(userToFollow);

        Set<User> followings = follower.getFollowings();
        followings.add(userToFollow);
        follower.setFollowings(followings);
        System.out.println("user has been added to your followings");

        session.getTransaction().commit();
        session.close();
    }

    public void unfollow(Long id, Long followerId){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        User follower = session.load(User.class,followerId);
        User toBeUnfollowed = session.load(User.class,id);
        toBeUnfollowed.getFollowers().remove(follower);

        Set<User> followings = follower.getFollowings();
        followings.remove(toBeUnfollowed);
        follower.setFollowings(followings);

        session.getTransaction().commit();
        session.close();
    }

}

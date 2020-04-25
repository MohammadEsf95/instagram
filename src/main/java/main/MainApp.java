package main;

import config.HibernateUtil;
import crudrepositories.CommentCrud;
import crudrepositories.PostCrud;
import crudrepositories.UserCrud;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Welcome.start();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session;
        UserCrud userCrud = new UserCrud();
        PostCrud postCrud = new PostCrud();
        CommentCrud commentCrud = new CommentCrud();
        User user = null;
        Scanner scanner = new Scanner(System.in);
        String command = null;
        while (true){
            //sign up or login
            System.out.println("sign up or login?");
            command = scanner.nextLine();
            if(command.equalsIgnoreCase("sign up")){
                System.out.println("enter your username: ");
                String username = scanner.nextLine();
                System.out.println("enter your password: ");
                String password = scanner.nextLine();
                userCrud.signUp(username,password);
                System.out.println("sign up has successfully done!");
                user = userCrud.login(username,password);
                //login
            }else if(command.equalsIgnoreCase("login")){
                System.out.println("enter your username: ");
                String username = scanner.nextLine();
                System.out.println("enter your password: ");
                String password = scanner.nextLine();
                user = userCrud.login(username,password);
                System.out.println("login is successfully done!");
            }else {
                System.out.println("wrong command!");
            }
            if(user != null){
                while (true) {
                    System.out.println("change password | delete account | search user | follow | unfollow \n"
                    + "new post | show all posts | delete post | edit post | like post | top posts \n" +
                            "add comment | delete comment \n" + "logout");
                    command = scanner.nextLine();
                    if(command.equalsIgnoreCase("change password")){
                        userCrud.changePassword(user);
                    }else if(command.equalsIgnoreCase("delete account")){
                        userCrud.delete(user.getId());
                        System.out.println("your account is deleted :((");
                        break;
                    }else if(command.equalsIgnoreCase("search user")){
                        String username = scanner.nextLine();
                        userCrud.search(username);
                        //TODO get searched user id
                    }else if(command.equalsIgnoreCase("follow")){
                        System.out.println("enter user id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        userCrud.follow(user.getId(),id);
                    }else if (command.equalsIgnoreCase("unfollow")){
                        Long followerId = user.getId();
                        System.out.println("enter user id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        userCrud.unfollow(id,followerId);
                    }else if (command.equalsIgnoreCase("new post")){
                        postCrud.newPost(user);
                    }else if(command.equalsIgnoreCase("show all posts")){
                        postCrud.showAllPosts(user);
                    }else if(command.equalsIgnoreCase("delete post")){
                        System.out.println("enter post id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        postCrud.delete(id);
                    }else if(command.equalsIgnoreCase("edit post")){
                        System.out.println("enter post id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        System.out.println("which item to edit? (title, tag): ");
                        String editedItem = scanner.nextLine();
                        if(editedItem.equalsIgnoreCase("title") || editedItem.equalsIgnoreCase("tag")){
                            System.out.println("enter new value: ");
                            String newValue = scanner.nextLine();
                            postCrud.edit(id,editedItem,newValue);
                        }else {
                            System.out.println("item does not exist!");
                        }
                    }else if(command.equalsIgnoreCase("like post")){
                        System.out.println("enter post owner id: ");
                        Long postOwnerId = Long.parseLong(scanner.nextLine());
                        Long postLikerId = user.getId();
                        System.out.println("enter post id: ");
                        Long postId = Long.parseLong(scanner.nextLine());
                        postCrud.like(postOwnerId,postLikerId,postId);
                    }else if(command.equalsIgnoreCase("top posts")){
                        System.out.println("enter number of top posts: ");
                        Long postNumber = Long.parseLong(scanner.nextLine());
                        postCrud.getMostLikedPosts(postNumber);
                    }else if(command.equalsIgnoreCase("add comment")){
                        System.out.println("enter post id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        System.out.println("enter text: ");
                        String text = scanner.nextLine();
                        commentCrud.addComment(user,text,id);
                    }else if(command.equalsIgnoreCase("delete comment: ")){
                        System.out.println("enter comment id: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        commentCrud.delete(id);
                    }else if (command.equalsIgnoreCase("logout")){
                        user = null;
//                        break;
                    }
                }
            }
        }
    }
}

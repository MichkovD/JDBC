package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.cfg.Configuration;
import jm.task.core.jdbc.model.User;
public class Main {
    public static void main(String[] args) {
        //MyTestJDBC();
        MyTestHibernate();
    }

    public static void MyTestJDBC(){
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Maven", "Christ", (byte)27);
        userService.saveUser("Jack", "Black", (byte)33);
        userService.saveUser("Posty", "Gres", (byte)69);
        userService.saveUser("Hyber", "Nate", (byte)100);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(1);
        userService.removeUserById(1);
        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }

    public static void MyTestHibernate(){
        UserService userService = new UserServiceImpl();
        //userService.dropUsersTable();
        //userService.createUsersTable();
        //userService.saveUser("Anna", "Black", (byte)32);
        //userService.saveUser("Jack", "Black", (byte)33);
//        userService.saveUser("Posty", "Gres", (byte)69);
        //System.out.println(userService.getAllUsers());
//        userService.removeUserById(122);
        userService.cleanUsersTable();
        //System.out.println(userService.getAllUsers());
        //userService.dropUsersTable();
    }
}

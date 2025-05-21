package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS "user"(
            id SERIAL PRIMARY KEY,
            name VARCHAR(50),
            last_name VARCHAR(50),
            age smallint);
            """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS "user";
            """;

    private static final String GET_USERS_SQL = """
             select (*) from user
            """;

    /// /// for singleton pattern
    public static final UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }

    /// ///all of the above for singleton pattern
    ///

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (var session = Util.getSessionFactory().openSession()) {
            System.out.println("OK. Создаю табличку Hiber");
            session.beginTransaction();//begin
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit(); //end
            System.out.println("Создал");
        }
    }
    @Override
    public void dropUsersTable() {
        try (var session = Util.getSessionFactory().openSession()){
            System.out.println("OK. Drop DB Hiber");
            session.beginTransaction();//begin
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            session.getTransaction().commit(); //end
        } catch (RuntimeException e) {
            throw new DaoException(e);
        }
    }


    //Configuration cfg = new Configuration();
    //cfg.configure();
    //cfg.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    // еще один способ побороть разницу названий java и db
    //cfg.addAnnotatedClass(User.class);
    //или в hib.cfg.xml добавь     <mapping class="entity.User"/>
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (var session = Util.getSessionFactory().openSession()){
            System.out.println("OK. Сохраняю юзера в DB Hiber");
            session.beginTransaction();//begin
            session.save(User.builder()
                    .name(name)
                    .lastName(lastName)
                    .age(age)
                    .build());
            session.getTransaction().commit(); //end
        } catch (RuntimeException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (var session = Util.getSessionFactory().openSession()) {
            System.out.println("Ищу (и удаляю) по id Hiber" + id);
            session.beginTransaction();//begin
            session.delete(User.builder().id(id).build());
            session.getTransaction().commit(); //end
        } catch (RuntimeException e) {
            System.out.println("user with this id wasnt here");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList;
        var session = Util.getSessionFactory().openSession();
        System.out.println("OK. Выдаю всех юзеров Hiber");
        session.beginTransaction();//begin
                            //  чувствительно к регистру, не обос... опять
        Query<User> query = session.createQuery("FROM User", User.class);
        System.out.println(query.list());
        usersList = query.list();
        session.getTransaction().commit(); //end
        session.close();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (var session = Util.getSessionFactory().openSession()){
            System.out.println("удаляю всех HIBER");
            session.beginTransaction();//begin
            session.createQuery("DELETE FROM User")//создал запрос
            .executeUpdate(); //выполнил
            session.getTransaction().commit(); //end
        } catch (RuntimeException e) {
            throw new DaoException(e);
        }
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public static final String CREATE_USER_TABLE_SQL = """ 
               create table if not exists "user"(
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       last_name VARCHAR(255),
                       age smallint
               );
               """;
    public static final String SAVE_USER_SQL = """
                insert into "user" (name, last_name, age)
                values (?, ?, ?)
               """;

    public static final String GET_ALL_USERS_SQL = """
               select * from "user";
               """;

    /// /// for singleton pattern
    public static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    /// ///all of the above for singleton pattern
    ///

    public void createUsersTable() {
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement(CREATE_USER_TABLE_SQL))
        {
            System.out.println("Connect есть. Создаю таблицу JDBC");
            st.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void dropUsersTable() {
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement("drop table if exists public.user"))
        {
            System.out.println("Connect есть. Удаляю саму табличку JDBC");
            st.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement(SAVE_USER_SQL))
        {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.execute();
            System.out.println(" User с именем – " + name + " добавлен в базу данных JDBC)");
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public void removeUserById(long id) {
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement("delete from public.user where id = ?"))
        {
            System.out.println("Connect есть. Удаляю JDBC");
            st.setLong(1, id);
            st.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<User>();
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement(GET_ALL_USERS_SQL))
        {
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return usersList;
    }

    public void cleanUsersTable() {
        try(Connection con = Util.open();
            PreparedStatement st = con.prepareStatement("delete from public.user"))
        {
            System.out.println("Connect есть. Удаляю всех user JDBC");
            st.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

/*
        try(Connection con = Util.open();
PreparedStatement st = con.prepareStatement(CREATE_USER_TABLE_SQL))
        {
        System.out.println("Connerct есть");
            st.execute();
        } catch (SQLException e) {
        throw new DaoException(e);
        }
*/

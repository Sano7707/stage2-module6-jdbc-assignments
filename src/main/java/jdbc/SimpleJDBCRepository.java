package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "";
    private static final String updateUserSQL = "";
    private static final String deleteUser = "";
    private static final String findUserByIdSQL = "";
    private static final String findUserByNameSQL = "";
    private static final String findAllUserSQL = "";

    public Long createUser(User user) {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Long a = null;
        try {
            a = (long) st.executeUpdate("insert into myuser values (" + user.getId() + ", " +
                    user.getFirstName() + "," + user.getLastName() + "," + user.getAge() + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }

    public User findUserById(Long userId){
       CustomConnector cn = new CustomConnector();
       connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select * from mysuer where id = " + userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Long id = null;
        try {
            id = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String firstName = null;
        String lastName = null;
        int age = 0;
        try {
            firstName = rs.getString(2);
             lastName = rs.getString(3);
             age = rs.getInt(4);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new User(id,firstName,lastName,age);
    }

    public User findUserByName(String userName) {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select * from mysuer where firstName = " + userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String firstName = null;
        String lastName = null;
        int age = 0;
        Long id;
        try {
            rs.next();
             id = rs.getLong(1);
             firstName = rs.getString(2);
             lastName = rs.getString(3);
             age = rs.getInt(4);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new User(id,firstName,lastName,age);
    }

    public List<User> findAllUser() {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select * from mysuer");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<User> list = new ArrayList<User>();

        try {
            while (rs.next()) {
                Long id = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int age = rs.getInt(4);
                list.add(new User(id, firstName, lastName, age));
            }
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public User updateUser(User user)  {
        CustomConnector cn = new CustomConnector();
        try {
            connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
            st = connection.createStatement();
            st.executeUpdate("update myuser set id =  " + user.getId() + ",firstname = " +
                    user.getFirstName() + ",lastname = " + user.getLastName() + ",age = " +
                    user.getAge() + " where id = 1");
            st.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new User((long)1,"aaaa","bbbb",2);
    }

    public void deleteUser(Long userId)  {
        try {
            CustomConnector cn = new CustomConnector();
            connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
            st = connection.createStatement();
            st.executeUpdate("delete from where id = " + userId);
            st.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

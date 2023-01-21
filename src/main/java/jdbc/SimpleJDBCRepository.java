package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public String propertiesUrl(){
        InputStream input;
        try {
            input = new FileInputStream("src/main/resources/app.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty("postgres.url");
    }

    public Long createUser(User user) {
        InputStream input;
        try {
            input = new FileInputStream("src/main/resources/app.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection(propertiesUrl());
        try {
            ps = connection.prepareStatement("insert into myusers" + " (id,firstname,lastname,age) values" +
                    " (?,?,?,?);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Long a = null;
        try {
            ps.setLong(1,user.getId());
            ps.setString(2,user.getFirstName());
            ps.setString(3,user.getLastName());
            ps.setInt(4,user.getAge());
            a = (long)ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }

    public User findUserById(Long userId){
       CustomConnector cn = new CustomConnector();
       connection = cn.getConnection(propertiesUrl());
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select * from myusers where id = " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Long id = null;
        try {
            id = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String firstName = null;
        String lastName = null;
        int age = 0;
        try {
            firstName = rs.getString(2);
             lastName = rs.getString(3);
             age = rs.getInt(4);
            st.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new User(id,firstName,lastName,age);
    }

    public User findUserByName(String userName) {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection(propertiesUrl());
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select* from myusers where firstname = "  + "'" +  userName + "'" + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String firstName ;
        String lastName ;
        int age = 0;
        long id;
        try {
            assert rs != null;
            rs.next();
             id = rs.getLong(1);
             firstName = rs.getString(2);
             lastName = rs.getString(3);
             age = rs.getInt(4);
            st.close();
            connection.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new User(id,firstName,lastName,age);
    }

    public List<User> findAllUser() {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection(propertiesUrl());
        try {
            st = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("select * from myusers");
        } catch (SQLException e) {
            e.printStackTrace();
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
            connection.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public User updateUser(User user)  {
        CustomConnector cn = new CustomConnector();
        try {
            connection = cn.getConnection(propertiesUrl());
            st = connection.createStatement();
            st.executeUpdate("update myusers set id =  " + user.getId() + ",firstname = " +
                  "'" + user.getFirstName() + "'" + ",lastname = " + "'" + user.getLastName() + "'"  + ",age = " +
                    user.getAge() + " where id = "  + user.getId());
            st.close();

            connection.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new User((long)1,"aaaa","bbbb",2);
    }

    public void deleteUser(Long userId)  {
        try {
            CustomConnector cn = new CustomConnector();
            connection = cn.getConnection(propertiesUrl());
            st = connection.createStatement();
            st.executeUpdate("delete from myusers where id = " + userId);
            st.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

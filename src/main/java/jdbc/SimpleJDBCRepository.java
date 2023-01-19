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

    public Long createUser() throws SQLException, IOException, ClassNotFoundException {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        st = connection.createStatement();
        Long a = (long) st.executeUpdate("insert into myuser values (1,'Sano','Hambardzumyan',20)");
        st.close();
        return a;
    }

    public User findUserById(Long userId) throws SQLException, ClassNotFoundException, IOException {
       CustomConnector cn = new CustomConnector();
       connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from mysuer where id = " + userId);
        rs.next();
        Long id = rs.getLong(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        int age = rs.getInt(4);
        st.close();
        return new User(id,firstName,lastName,age);
    }

    public User findUserByName(String userName) throws SQLException, IOException, ClassNotFoundException {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
         st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from mysuer where firstName = " + userName);
        rs.next();
        Long id = rs.getLong(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        int age = rs.getInt(4);
        st.close();
        return new User(id,firstName,lastName,age);
    }

    public List<User> findAllUser() throws SQLException, IOException, ClassNotFoundException {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from mysuer");
        List<User> list = new ArrayList<User>();

        while(rs.next()){
            Long id = rs.getLong(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            int age = rs.getInt(4);
            list.add(new User(id,firstName,lastName,age));
        }
        st.close();
        return list;
    }

    public User updateUser() throws SQLException, IOException, ClassNotFoundException {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        st = connection.createStatement();
        st.executeUpdate("update myuser set firstname = 'aaaa',lastname = 'bbbb',age = 2 where id = " + 1);
        st.close();
        return new User((long)1,"aaaa","bbbb",2);
    }

    private void deleteUser(Long userId) throws SQLException, IOException, ClassNotFoundException {
        CustomConnector cn = new CustomConnector();
        connection = cn.getConnection("postgres://macbook@localhost:5432/macbook");
        st = connection.createStatement();
        st.executeUpdate("delete from where id = " + userId);
        st.close();
    }
}

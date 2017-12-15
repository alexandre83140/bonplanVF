package hei.devweb.bonplan.daos;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class UserDao {

    protected DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("hei");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }

    public List<User> listUsers(){
        List<User> users = new ArrayList<>();
        try(Connection connection = getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userBP")){
            while(resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("nameU")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addUser(String nameU) {
        String query = "INSERT INTO userBP (nameU) VALUES(?)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nameU);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String nameU){
        String query = "SELECT * FROM userBP WHERE nameU=?";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nameU);
            statement.executeQuery();
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getInt("user_id"), resultSet.getString("nameU"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

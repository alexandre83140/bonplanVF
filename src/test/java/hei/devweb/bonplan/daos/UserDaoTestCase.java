package hei.devweb.bonplan.daos;

import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class UserDaoTestCase {

    private UserDao userDao = new UserDao();

    @Before
    public void initDatabase() throws Exception{
        try (Connection connection = userDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()){
            stmt.executeUpdate("DELETE FROM bonplan");
            stmt.executeUpdate("DELETE FROM userBP");
            stmt.executeUpdate("INSERT INTO `userBP`(user_id, nameU) VALUES (1,'Name #1')");
            stmt.executeUpdate("INSERT INTO `userBP`(user_id, nameU) VALUES (2,'Name #2')");
            stmt.executeUpdate("INSERT INTO `userBP`(user_id, nameU) VALUES (3,'Name #3')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListUser(){
        //WHEN
        List<User> users = userDao.listUsers();
        //THEN
        assertThat(users).hasSize(3);
        assertThat(users).extracting("user_id","nameU").containsOnly(
                tuple(1, "Name #1"),
                tuple(2, "Name #2"),
                tuple(3, "Name #3")
        );
    }

    @Test
    public void shouldGetUser() {
        // WHEN
        User user = userDao.getUser("Name #1");
        // THEN
        assertThat(user).isNotNull();
        assertThat(user.getUser_id()).isEqualTo(1);
        assertThat(user.getNameU()).isEqualTo("Name #1");
    }

    @Test
    public void shouldAddUser() throws SQLException {
        //WHEN
        userDao.addUser("test");
        //THEN
        try (Connection connection = userDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM userBP WHERE nameU = 'test'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("user_id")).isGreaterThan(0);
                assertThat(rs.getString("nameU")).isEqualTo("test");
                assertThat(rs.next()).isFalse();
            }
        }
    }
}

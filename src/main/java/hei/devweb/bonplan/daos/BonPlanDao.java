package hei.devweb.bonplan.daos;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class BonPlanDao {

    protected DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("hei");
        dataSource.setUser("root");
        dataSource.setPassword("");
        return dataSource;
    }

    public List<BonPlan> listBonPlans(){
        String query = "SELECT * FROM bonplan JOIN userBP ON bonplan.user_id = userBP.user_id WHERE bonplan.deleted=false";
        List<BonPlan> bonplans = new ArrayList<>();
        try(
                Connection connection = getDataSource().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ){
            while(resultSet.next()) {
                bonplans.add(
                        new BonPlan(
                                resultSet.getInt("BP_id"),
                                resultSet.getString("nomBP"),
                                resultSet.getString("descriBP"),
                                resultSet.getString("lieuBP"),
                                resultSet.getInt("prixBP"),
                                resultSet.getDate("dateBP").toLocalDate(),
                                new User(resultSet.getInt("user_id"), resultSet.getString("nameU"))
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bonplans;
    }

    public BonPlan getBonPlan(Integer idBP) {
        String query = "SELECT * FROM bonplan JOIN userBP ON bonplan.user_id = userBP.user_id WHERE BP_id=? AND deleted=false";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idBP);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new BonPlan(
                            resultSet.getInt("BP_id"),
                            resultSet.getString("nomBP"),
                            resultSet.getString("descriBP"),
                            resultSet.getString("lieuBP"),
                            resultSet.getInt("prixBP"),
                            resultSet.getDate("dateBP").toLocalDate(),
                            new User(resultSet.getInt("user_id"), resultSet.getString("nameU")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public BonPlan addBonPlan(BonPlan bonPlan){
        String query = "INSERT INTO bonplan(nomBP, descriBP, lieuBP, prixBP, dateBP, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,bonPlan.getNomBP());
            statement.setString(2,bonPlan.getDescriptifBP());
            statement.setString(3,bonPlan.getLieuBP());
            statement.setInt(4,bonPlan.getPrixBP());
            statement.setDate(5,Date.valueOf(bonPlan.getDateBP()));
            statement.setInt(6, bonPlan.getUser().getUser_id());
            statement.executeUpdate();

            try (ResultSet ids = statement.getGeneratedKeys()) {
                if (ids.next()) {
                    int generatedId = ids.getInt(1);
                    bonPlan.setIdBP(generatedId);
                    return bonPlan;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBonPlan(Integer idBP) {
        String query = "UPDATE bonplan SET deleted=true WHERE BP_id=?";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,idBP);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllBonPlan(){
        String query = "DELETE FROM bonplan";
        try(Connection connection = getDataSource().getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

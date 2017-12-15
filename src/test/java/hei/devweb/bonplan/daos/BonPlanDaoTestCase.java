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

public class BonPlanDaoTestCase {

    private BonPlanDao bonPlanDao = new BonPlanDao();

    @Before
    public void initDatabase() {
        try (Connection connection = bonPlanDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM bonplan");
            stmt.executeUpdate("DELETE FROM userBP");
            stmt.executeUpdate("INSERT INTO `userBP`(`user_id`,`nameU`) VALUES (1,'Name #1')");
            stmt.executeUpdate("INSERT INTO `userBP`(`user_id`,`nameU`) VALUES (2,'Name #2')");
            stmt.executeUpdate("INSERT INTO bonplan(BP_id, nomBP, descriBP, lieuBP, prixBP, dateBP, user_id, deleted) VALUES (1,'Nom #1', 'Description #1', 'Lieu #1', 10,'2017-12-15', 1, 0)");
            stmt.executeUpdate("INSERT INTO bonplan(BP_id, nomBP, descriBP, lieuBP, prixBP, dateBP, user_id, deleted) VALUES (2,'Nom #2', 'Description #2', 'Lieu #2', 20,'2017-12-16', 2, 1)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulListBonPlans() {
        //WHEN
        List<BonPlan> bonPlans = bonPlanDao.listBonPlans();
        //THEN
        assertThat(bonPlans).hasSize(1);
        assertThat(bonPlans).extracting("idBP", "nomBP", "descriptifBP", "lieuBP", "prixBP", "dateBP", "user.user_id", "user.nameU").containsOnly(
                tuple(1, "Nom #1", "Description #1", "Lieu #1", 10, LocalDate.of(2017, 12, 15), 1, "Name #1")
        );
    }

    @Test
    public void shouldGetBonPlan() {
        // WHEN
        BonPlan bonPlan = bonPlanDao.getBonPlan(1);
        // THEN
        assertThat(bonPlan).isNotNull();
        assertThat(bonPlan.getIdBP()).isEqualTo(1);
        assertThat(bonPlan.getNomBP()).isEqualTo("Nom #1");
        assertThat(bonPlan.getDescriptifBP()).isEqualTo("Description #1");
        assertThat(bonPlan.getLieuBP()).isEqualTo("Lieu #1");
        assertThat(bonPlan.getPrixBP()).isEqualTo(10);
        assertThat(bonPlan.getDateBP()).isEqualTo(LocalDate.of(2017, 12, 15));
        assertThat(bonPlan.getUser().getUser_id()).isEqualTo(1);
        assertThat(bonPlan.getUser().getNameU()).isEqualTo("Name #1");
    }

    @Test
    public void shouldNotGetDeletedBonPlan(){
        //WHEN
        BonPlan bonPlan = bonPlanDao.getBonPlan(2);
        //THEN
        assertThat(bonPlan).isNull();
    }

    @Test
    public void shouldAddBonPlan() throws SQLException {
        //GIVEN
        BonPlan newBonPlan = new BonPlan(null, "New Name", "New Description", "New Place", 15, LocalDate.of(2017, 12, 15), new User(1, "Name #1"));
        //WHEN
        BonPlan createdBonPlan = bonPlanDao.addBonPlan(newBonPlan);
        // THEN
        assertThat(createdBonPlan).isNotNull();
        assertThat(createdBonPlan.getIdBP()).isNotNull();
        assertThat(createdBonPlan.getIdBP()).isGreaterThan(0);
        assertThat(createdBonPlan.getNomBP()).isEqualTo("New Name");
        assertThat(createdBonPlan.getDescriptifBP()).isEqualTo("New Description");
        assertThat(createdBonPlan.getLieuBP()).isEqualTo("New Place");
        assertThat(createdBonPlan.getPrixBP()).isEqualTo(15);
        assertThat(createdBonPlan.getDateBP()).isEqualTo(LocalDate.of(2017, 12, 15));
        assertThat(createdBonPlan.getUser().getUser_id()).isEqualTo(1);
        assertThat(createdBonPlan.getUser().getNameU()).isEqualTo("Name #1");
        try (Connection connection = bonPlanDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM bonplan WHERE nomBP = 'New Name'")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt("BP_id")).isEqualTo(createdBonPlan.getIdBP());
                assertThat(rs.getString("nomBP")).isEqualTo("New Name");
                assertThat(rs.getString("descriBP")).isEqualTo("New Description");
                assertThat(rs.getString("lieuBP")).isEqualTo("New Place");
                assertThat(rs.getInt("prixBP")).isEqualTo(15);
                assertThat(rs.getDate("dateBP").toLocalDate()).isEqualTo(LocalDate.of(2017, 12, 15));
                assertThat(rs.getInt("user_id")).isEqualTo(1);

                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shouldDeleteBonPlan() {
        //WHEN
        bonPlanDao.deleteBonPlan(2);
        //THEN
        try (Connection connection = bonPlanDao.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM bonplan WHERE BP_id = 2")) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getBoolean("deleted")).isTrue();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
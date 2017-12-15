package hei.devweb.bonplan.managers;

import hei.devweb.bonplan.daos.BonPlanDao;
import hei.devweb.bonplan.daos.UserDao;
import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;

import java.util.List;

public class BonPlanLibrary {

    private static class BonPlanLibraryHolder {
        private final static BonPlanLibrary instance = new BonPlanLibrary();
    }

    public static BonPlanLibrary getInstance() {
        return BonPlanLibraryHolder.instance;
    }

    private BonPlanDao bonPlanDao = new BonPlanDao();
    private UserDao userDao = new UserDao();

    private BonPlanLibrary() {
    }

    public List<BonPlan> listBonPlans() {
        return bonPlanDao.listBonPlans();
    }

    public BonPlan getBonPlan(Integer idBP) {
        return bonPlanDao.getBonPlan(idBP);
    }

    public BonPlan addBonPlan(BonPlan bonPlan) {
        if (bonPlan == null) {
            throw new IllegalArgumentException("The film should not be null.");
        }
        if (bonPlan.getNomBP() == null || "".equals(bonPlan.getNomBP())) {
            throw new IllegalArgumentException("The ad's name should not be empty.");
        }
        if (bonPlan.getDescriptifBP() == null || "".equals(bonPlan.getDescriptifBP())) {
            throw new IllegalArgumentException("The description should not be empty.");
        }
        if (bonPlan.getLieuBP()== null || "".equals(bonPlan.getLieuBP())) {
            throw new IllegalArgumentException("The place should not be empty.");
        }
        if (bonPlan.getPrixBP() == null && bonPlan.getPrixBP() > 0) {
            throw new IllegalArgumentException("The price should not be null or equal to 0.");
        }
        if (bonPlan.getDateBP() == null) {
            throw new IllegalArgumentException("The  date should not be null.");
        }
        if (bonPlan.getUser() == null) {
            throw new IllegalArgumentException("The ad' user should not be null.");
        }

        return bonPlanDao.addBonPlan(bonPlan);
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User getUser(String nameU) {
        return userDao.getUser(nameU);
    }

    public void addUser(String nameU) {
        if (nameU == null || "".equals(nameU)) {
            throw new IllegalArgumentException("The name of the user should not be empty.");
        }
        userDao.addUser(nameU);
    }

    public void deleteBonPlan(Integer idBP){
        bonPlanDao.deleteBonPlan(idBP);
    }
}

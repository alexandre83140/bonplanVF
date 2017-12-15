package hei.devweb.bonplan.services;

import hei.devweb.bonplan.daos.BonPlanDao;
import hei.devweb.bonplan.daos.UserDao;
import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;


import java.util.List;

public class BonPlanService {

    private static class BonPlanServiceHolder{
        private static BonPlanService instance = new BonPlanService();
    }

    public static BonPlanService getInstance(){
        return BonPlanServiceHolder.instance;
    }

    private BonPlanService(){
    }

    private BonPlanDao bonPlanDao = new BonPlanDao();

    private UserDao userDao = new UserDao();

    public List<BonPlan> listBonPlans(){
        return bonPlanDao.listBonPlans();
    }

    public void getBonPlan(Integer idBP) { bonPlanDao.getBonPlan(idBP);}

    public void addBonPlan(BonPlan bonPlan){
        bonPlanDao.addBonPlan(bonPlan);
    }

    public List<User> listUsers(){
        return userDao.listUsers();
    }

    public void getUser(String nameU) { userDao.getUser(nameU);}

    public void addUser(String nameU) {
        if (nameU == null || "".equals(nameU)){
            throw new IllegalArgumentException("The name of the user should not be empty");
        }
        userDao.addUser(nameU);
    }

    public void deleteAllBonplan(){bonPlanDao.deleteAllBonPlan();}
}

package hei.devweb.bonplan.pojos;


import java.time.LocalDate;

public class BonPlan {

    private Integer idBP;
    private String nomBP;
    private String descriptifBP;
    private String lieuBP;
    private Integer prixBP;
    private LocalDate dateBP;
    private User userBP;


    public BonPlan(Integer idBP, String nomBP, String descriptifBP, String lieuBP, Integer prixBP, LocalDate dateBP, User userBP ) {
        this.idBP = idBP;
        this.nomBP = nomBP;
        this.descriptifBP = descriptifBP;
        this.lieuBP = lieuBP;
        this.prixBP = prixBP;
        this.dateBP = dateBP;
        this.userBP = userBP;
        ;
    }

    public Integer getIdBP() {

        return idBP;
    }

    public void setIdBP(Integer idBP) {
        this.idBP = idBP;
    }

    public String getNomBP() {
        return nomBP;
    }

    public void setNomBP(String nomBP) {
        this.nomBP = nomBP;
    }

    public String getDescriptifBP() {
        return descriptifBP;
    }

    public void setDescriptifBP(String descriptifBP) {
        this.descriptifBP = descriptifBP;
    }

    public String getLieuBP() {
        return lieuBP;
    }

    public void setLieuBP(String lieuBP) {
        this.lieuBP = lieuBP;
    }

    public Integer getPrixBP() {
        return prixBP;
    }

    public void setPrixBP(Integer prixBP) {
        this.prixBP = prixBP;
    }

    public LocalDate getDateBP() {
     return dateBP;
    }

   public void setDateBP(LocalDate dateBP) {
       this.dateBP = dateBP;
   }

   public User getUser(){return userBP;}

   public void setUser(User userBP){this.userBP=userBP;}

}

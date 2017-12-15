package hei.devweb.bonplan.pojos;

public class User {

    private Integer user_id;
    private String nameU;

    public User(Integer user_id, String nameU) {
        this.user_id = user_id;
        this.nameU = nameU;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getNameU() {
        return nameU;
    }

    public void setNameU(String nameU) {
        this.nameU = nameU;
    }
}

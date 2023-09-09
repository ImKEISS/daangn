package daangn.daangnspring.domain;

public class User {
    private String id;
    private String nickname;
    private float mannerTemperature;
    private Integer residenceId;
    private String[] residence = new String[3];

    public User(String id) {
        this.id = id;
    }

    public User(String[] residence) {
        this.residence = residence;
    }

    public User(String id, String nickname, float mannerTemperature, String[] residence) {
        this.id = id;
        this.nickname = nickname;
        this.mannerTemperature = mannerTemperature;
        this.residence = residence;
    }

    public User(String id, String nickname, float mannerTemperature, Integer residenceId, String[] residence) {
        this.id = id;
        this.nickname = nickname;
        this.mannerTemperature = mannerTemperature;
        this.residenceId = residenceId;
        this.residence = residence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public float getMannerTemperature() {
        return mannerTemperature;
    }

    public void setMannerTemperature(float mannerTemperature) {
        this.mannerTemperature = mannerTemperature;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    public String[] getResidence() {
        return residence;
    }

    public void setResidence(String[] residence) {
        this.residence = residence;
    }
}

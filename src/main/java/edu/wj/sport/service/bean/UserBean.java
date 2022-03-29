package edu.wj.sport.service.bean;

public class UserBean {

    private String id;

    private String nickname;

    private String phoneNumber;

    private String pwd;

    private int sex;

    private long birthDate;

    private String avatar;

    private long createTime;

    private int status;

    public UserBean(String id, String nickname, String phoneNumber, String pwd, int sex, long birthDate, String avatar, long createTime, int status) {
        this.id = id;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
        this.sex = sex;
        this.birthDate = birthDate;
        this.avatar = avatar;
        this.createTime = createTime;
        this.status = status;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex=" + sex +
                ", birthDate=" + birthDate +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}

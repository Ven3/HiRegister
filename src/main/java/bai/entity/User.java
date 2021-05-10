package bai.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;

@EntityScan
public class User {
    
    private String id;          //用户id
    private String loginid;     //登录名
    private String password;    //密码
    private String state;       //帐号状态
    private String infoid;      //信息id

    public User() {
    }

    public User(String loginid, String password) {
        this.loginid = loginid;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginid='" + loginid + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                ", infoid='" + infoid + '\'' +
                '}';
    }
}

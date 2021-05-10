package bai.entity;

public class Role {
    private String roleid;
    private String rolename;
    private boolean user = false;
    private boolean doctor = false;
    private boolean admin = false;
    private boolean root = false;

    private Role(String roleid, String rolename, boolean user, boolean doctor, boolean admin, boolean root) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.user = user;
        this.doctor = doctor;
        this.admin = admin;
        this.root = root;
    }

    public static Role User(){
        return new Role("1","普通用户",true,false,false,false);
    }

    public static Role Doctor(){
        return new Role("2","医生",true,true,false,false);
    }

    public static Role DoctorAdmin(){
        return new Role("4","医生管理员",true,true,true,false);
    }

    public static Role Admin(){
        return new Role("3","管理员",true,false,true,false);
    }

    public static Role Root(){
        return new Role("5","超级管理员",true,false,true,true);
    }
    public static Role DoctorRoot(){
        return new Role("6","医生超级管理员",true,true,true,true);
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isDoctor() {
        return doctor;
    }

    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}

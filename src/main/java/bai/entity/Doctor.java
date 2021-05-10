package bai.entity;

public class Doctor {

    private String id;          //医生id
    private String drinfoid;    //医生信息id
    private String jobage;      //医龄
    private String hiredate;    //雇佣日期
    private String deptid;      //部门id
    private UserInfo drinfo;    //医生信息
    private Dept dept;          //部门信息

    public Doctor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrinfoid() {
        return drinfoid;
    }

    public void setDrinfoid(String drinfoid) {
        this.drinfoid = drinfoid;
    }

    public String getJobage() {
        return jobage;
    }

    public void setJobage(String jobage) {
        this.jobage = jobage;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public UserInfo getDrinfo() {
        return drinfo;
    }

    public void setDrinfo(UserInfo drinfo) {
        this.drinfo = drinfo;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}

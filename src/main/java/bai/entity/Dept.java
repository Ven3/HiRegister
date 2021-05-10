package bai.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;

@EntityScan
public class Dept {

    private String id;      //流水号
    private String deptid;  //科室id
    private String dname;   //科室名
    private String pareid;  //父级id

    public Dept() {

    }

    public Dept(String id, String deptid, String dname, String pareid) {
        this.id = id;
        this.deptid = deptid;
        this.dname = dname;
        this.pareid = pareid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPareid() {
        return pareid;
    }

    public void setPareid(String pareid) {
        this.pareid = pareid;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", deptid='" + deptid + '\'' +
                ", dname='" + dname + '\'' +
                ", pareid='" + pareid + '\'' +
                '}';
    }
}

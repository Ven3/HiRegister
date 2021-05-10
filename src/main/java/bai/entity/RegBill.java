package bai.entity;

import java.sql.Date;
import java.util.List;

public class RegBill {

    private String rbid;        //挂号单流水号
    private String regbillid;   //挂号单标识
    private Date createtime;    //创建时间
    private Date aptdate;      //预约日期
    private Integer time;       //预约时间段
    private String deptid;      //科室
    private String userid;      //用户ID
    private String drid;        //医生ID
    private String state;       //挂号单状态
    private Double total;       //总费用
    private String extra;       //说明
    private String ptinfoid;      //患者信息


    private List<RegBillInfo> rginfos;  //挂号单详细信息
    private List<RegBillEp> expences;   //挂号单费用详单

    private UserInfo userinfo;  //用户信息
    private UserInfo ptinfo;    //患者信息
    private Doctor doctor;      //医生信息

    public RegBill() {

    }


    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRbid() {
        return rbid;
    }

    public void setRbid(String rbid) {
        this.rbid = rbid;
    }

    public String getRegbillid() {
        return regbillid;
    }

    public void setRegbillid(String regbillid) {
        this.regbillid = regbillid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDrid() {
        return drid;
    }

    public void setDrid(String drid) {
        this.drid = drid;
    }

    public List<RegBillInfo> getRginfos() {
        return rginfos;
    }

    public void setRginfos(List<RegBillInfo> rginfos) {
        this.rginfos = rginfos;
    }

    public List<RegBillEp> getExpences() {
        return expences;
    }

    public void setExpences(List<RegBillEp> expences) {
        this.expences = expences;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getAptdate() {
        return aptdate;
    }

    public void setAptdate(Date aptdate) {
        this.aptdate = aptdate;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPtinfoid() {
        return ptinfoid;
    }

    public void setPtinfoid(String ptinfoid) {
        this.ptinfoid = ptinfoid;
    }

    public UserInfo getPtinfo() {
        return ptinfo;
    }

    public void setPtinfo(UserInfo ptinfo) {
        this.ptinfo = ptinfo;
    }
}

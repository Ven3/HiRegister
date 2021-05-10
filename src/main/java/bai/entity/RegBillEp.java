package bai.entity;

import java.sql.Date;

public class RegBillEp {

    private String epid;        //费用单id
    private String rbid;        //挂号单id
    private Date createtime;    //创建时间
    private String epname;      //费用项名称
    private Double epmoney;     //费用
    private String extra;       //备注

    public RegBillEp() {
    }

    public String getEpid() {
        return epid;
    }

    public void setEpid(String epid) {
        this.epid = epid;
    }

    public String getRbid() {
        return rbid;
    }

    public void setRbid(String rbid) {
        this.rbid = rbid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getEpname() {
        return epname;
    }

    public void setEpname(String epname) {
        this.epname = epname;
    }

    public Double getEpmoney() {
        return epmoney;
    }

    public void setEpmoney(Double epmoney) {
        this.epmoney = epmoney;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

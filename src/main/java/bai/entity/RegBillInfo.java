package bai.entity;

import java.sql.Date;

public class RegBillInfo {
    private String rbinfoid;    //就诊信息单id
    private String content;     //内容
    private String rbid;        //挂号单id
    private Date createtime;    //创建时间

    public RegBillInfo() {
    }

    public String getRbinfoid() {
        return rbinfoid;
    }

    public void setRbinfoid(String rbinfoid) {
        this.rbinfoid = rbinfoid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}

package bai.entity;

public class TaskItem {
    private String id;          //标识
    private String taskid;      //任务id
    private String approver;    //申请人
    private String charger;     //处理人
    private String apdate;      //申请日期
    private String tasktime;    //任务日期
    private String extra;       //备注
    private String state;       //任务状态

    public TaskItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApdate() {
        return apdate;
    }

    public void setApdate(String apdate) {
        this.apdate = apdate;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

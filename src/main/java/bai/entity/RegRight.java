package bai.entity;

public class RegRight {
    private String rrid;
    private String userid;
    private String handler;
    private String fromrole;
    private String torole;
    private String state;
    private String extra;

    private UserInfo fromuser;
    private UserInfo handlerinfo;

    public RegRight() {
    }

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }


    public String getTorole() {
        return torole;
    }

    public void setTorole(String torole) {
        this.torole = torole;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UserInfo getFromuser() {
        return fromuser;
    }

    public void setFromuser(UserInfo fromuser) {
        this.fromuser = fromuser;
    }

    public UserInfo getHandlerinfo() {
        return handlerinfo;
    }

    public void setHandlerinfo(UserInfo handlerinfo) {
        this.handlerinfo = handlerinfo;
    }

    public String getFromrole() {
        return fromrole;
    }

    public void setFromrole(String fromrole) {
        this.fromrole = fromrole;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}


package bai.entity;

import java.util.HashMap;
import java.util.Map;

public class FormState {

    //User{A:已注册，信息不完整；B：帐号正常；C：黑名单}

    public static final String USERREG = "A";
    public static final String USEROK = "B";
    public static final String USERBLACK = "C";

    //RegBill{ A:已失效；B:已提交；C:已预约；D:诊断中；E:诊断完成；F: 已结算；G:待取药；H:已完成}
    public static final String RBSAVE = "A";
    public static final String RBCOMMIT = "B";
    public static final String RBAPPOINTED = "C";
    public static final String RBZDing = "D";
    public static final String RBZDWanCheng = "E";
    public static final String RBYiJieSuan = "F";
    public static final String RBQuYao= "G";
    public static final String RBDone = "H";


    public static final String AUCreate = "A";
    public static final String AUCommit= "B";
    public static final String AUDone= "C";

    public static String decodeAuState(String state) {
        String dcode = "";
        Map<String, String> types = new HashMap<String, String>();
        types.put("A","已失效");
        types.put("B","已提交");
        types.put("C","已完成");
        return types.get(state);
    }

    public static String decodeRbState(String state) {
        String dcode = "";
        Map<String, String> types = new HashMap<String, String>();
        types.put("A","已失效");
        types.put("B","已提交");
        types.put("C","已预约");
        types.put("D","诊断中");
        types.put("E","诊断完成");
        types.put("F","已结算");
        types.put("G","待取药");
        types.put("H","已完成");
        return types.get(state);

    }

}

package bai.utils;


import java.util.Calendar;
import java.util.UUID;

public class IDGenerator {

    /**
     * UUID生成器
     * @return 32位字符串
     */
    public static String getUUID(){
        String str = "" ;
        str = UUID.randomUUID().toString().replace("-","");
        return str ;
    }


    /**
     * 产生有时间的字符串，长度12+1+16=29位
     * @return uuid后12位-年月日时分秒
     */
    public static String getTimeId(String type){
        String uuid = getUUID();
        int length = uuid.length();
        String subuuid = uuid.substring(length-12,length);
        String timeId = subuuid + "-" + DateTool.getDate(Calendar.getInstance()).replaceAll("-","") + DateTool.getTime(Calendar.getInstance()).replaceAll(":","");
        return type+"-"+timeId;
    }

}

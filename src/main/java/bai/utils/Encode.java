package bai.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Han on 2017/10/19.
 */

/**
 * MD5 & SHA-?
 */
public class Encode {

    /****************************** M D 5 ******************************/
    /**
     * MD5 Encode
     * @param message
     * @return
     */
    public String getMD5Code(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }
    // 二进制转十六进制
    protected static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            System.out.println(num);
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString();
    }
    /****************************** S H A Start ******************************/
    /**
     * SHA-1
     *
     * @param strText
     * @return
     */
    public String SHA_1(final String strText) {
        return SHA(strText, "SHA-1");
    }
    /**
     * SHA-224
     *
     * @param strText
     * @return
     */
    public String SHA_224(final String strText) {
        return SHA(strText, "SHA-224");
    }
    /**
     * SHA-256
     *
     * @param strText
     * @return
     */
    public String SHA_256(final String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * SHA-384
     *
     * @param strText
     * @return
     */
    public String SHA_384(final String strText) {
        return SHA(strText, "SHA-384");
    }
    /**
     * SHA-512
     *
     * @param strText
     * @return
     */
    public String SHA_512(final String strText) {
        return SHA(strText, "SHA-512");
    }
    /**
     * Base SHA Encode
     *
     * @param strText
     * @param strType
     * @return
     */
    protected String SHA(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                //创建加密对象并传入加密类型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到byte类型结果
                byte byteBuffer[] = messageDigest.digest();
                // 将byte转换为string
                StringBuffer strHexString = new StringBuffer();
                // 遍历 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回结果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
    /****************************** MD5 SubString(16) + SHA SubString(48) ******************************/
    public String mixEncode(String message){
        String left=getMD5Code(message).substring(0,16);
        System.out.println(left.length());
        String shamessage=SHA_256(message);
        String right=shamessage.substring(16,shamessage.length());
        System.out.println(shamessage.length());
        System.out.println(right.length());
        return  left+right;
    }

}

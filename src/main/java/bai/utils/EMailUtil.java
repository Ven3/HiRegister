package bai.utils;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EMailUtil {


    protected static final String from = "ven1020@163.com";
    protected static final String host = "smtp.163.com";
    /**
     * 发送邮件工具类
     * @param email 目的邮箱地址
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public static void sendEmail(String email,String subject, String content){
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host",host);
        prop.put("mail.smtp.auth","true");
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,"mima4321");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject(subject);

            message.setText(content);

            Transport transport = session.getTransport();
            transport.connect(host, from, "mima4321");
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

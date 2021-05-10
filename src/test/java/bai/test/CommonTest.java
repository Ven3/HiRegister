package bai.test;

import bai.entity.R;
import bai.service.UserService;
import bai.utils.DBManager;
import bai.utils.IDGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.Calendar;

public class CommonTest {

    Connection connection = null;

    public void init() throws SQLException {
        connection = DBManager.getConnection();
        connection.setAutoCommit(false);
    }

    @Test
    public void initUser() throws SQLException {
        init();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from users");
        while (rs.next()){
            String loginid = rs.getString("loginid");
            String username = rs.getString("username");
            String gender = rs.getString("usergender");
            String userid = rs.getString("userid");

            System.out.println(loginid + "\t" + username + "\t" + gender + "\t" +userid);

            initInfo(loginid,username,gender,userid);
        }

        connection.close();

    }

    private void initInfo(String loginid, String username, String gender, String userid) throws SQLException {
        String sql = "INSERT into userinfo(id,infoid,username,realname,idcard,gender,regtime,updatetime) VALUES(?,?,?,?,?,?,?,?) ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,IDGenerator.getUUID());
        String infoid = IDGenerator.getTimeId("info");
        statement.setString(2,infoid);
        statement.setString(3,username);
        statement.setString(4,username);
        statement.setString(5,userid);
        statement.setString(6,gender);
        Date date = new Date(new java.util.Date().getTime());
        statement.setDate(7, date);
        statement.setDate(8, date);
        statement.execute();
        connection.commit();
        insertValue(loginid,username,userid,infoid);
        System.err.println("Info: " + infoid);


    }

    private void insertValue(String loginid, String username, String userid,String infoid) throws SQLException {

        String sql = "INSERT into user(id,loginid,password,state,infoid) VALUES(?,?,?,?,?) ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,IDGenerator.getTimeId("user"));
        statement.setString(2,loginid);
        statement.setString(3,loginid);
        statement.setString(4,"B");
        statement.setString(5,infoid);
        statement.execute();
        connection.commit();

        System.err.println("User: " + loginid);
    }


}

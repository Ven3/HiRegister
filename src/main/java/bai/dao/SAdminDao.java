package bai.dao;

import bai.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SAdminDao {

    /**
     * 查找所有用户
     * @return
     */
    List<UserInfo> getUsers(Integer form, Integer number);

    /**
     * 获取用户数
     * @return
     */
    int getUserCount();

    /**
     * 删除用户
     * @param userid
     */
    void deleteUser(String userid);

    void udUserInfo(UserInfo userInfo);

    void udUserPwd(String pwd, String userid);
}

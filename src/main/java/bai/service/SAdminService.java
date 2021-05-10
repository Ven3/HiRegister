package bai.service;

import bai.dao.SAdminDao;
import bai.entity.Pages;
import bai.entity.R;
import bai.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SAdminService {

    @Autowired
    private SAdminDao sAdminDao;

    /**
     * 查询所有用户
     * @param c
     * @param n
     * @return
     */
    public Pages getUsers(Integer c, Integer n) {

        if (c == null || c == 0) {
            c = 1;
        }
        if (n == null || n == 0) {
            n = 10;
        }
        Pages page = new Pages(c, n);
        page.setData(sAdminDao.getUsers((c - 1) * n, n));
        page.setT(this.getCount());
        return page;

    }
    public int getCount() {
        return sAdminDao.getUserCount();
    }


    /**
     * 删除用户
     *
     * @param infoid
     * @return
     */
    public R delUser(String infoid) {
        try {
            sAdminDao.deleteUser(infoid);
            return new R(100, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "删除失败", e);
        }
    }

    public R udUser(UserInfo userInfo){

        try{
            sAdminDao.udUserInfo(userInfo);
            return new R(100,"更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new R(200,"更新失败");
        }
    }

    public R udUserPwd(String userid, String pwd, String pwdc) {
        try {
            sAdminDao.udUserPwd(pwd,userid);
            return new R(100,"密码更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new R(200,"密码更新失败");
        }
    }
}

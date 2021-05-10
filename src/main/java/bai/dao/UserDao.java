package bai.dao;

import bai.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    /**
     *用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 根据用户infoid查找用户信息
     * @param infoid
     * @return
     */
    UserInfo getUserInfo(String infoid);

    /**
     * 根据ID返回数据库记录条数
     * @param loginid
     * @return
     */
    Integer checkId(String loginid);

    /**
     * 用户注册
     * @param user
     */
    void regUser(User user);


    /**
     * 添加用户信息
     * @param userInfo
     */
    void addUserInfo(UserInfo userInfo);

    void addPtUser(UserInfo ptuser);

    /**
     * 更新用户信息
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);


    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 获取科室列表
     */
    List<Dept> getDeptList();

    /**
     * 获取类别下的所有科室
     * @param deptid
     * @return
     */
    List<Dept> getDeptList(String deptid);

    /**
     * 获取医生列表
     * @param deptid
     * @return
     */
    List<Doctor> listDoctors(String deptid);

    /**
     * 根据部门id获取部门信息
     * @param deptid
     * @return
     */
    Dept getDept(String deptid);

    /**
     * 添加挂号单
     * @param regBill
     */
    void addAppointment(RegBill regBill);

    /**
     * 添加费用
     * @param ep
     */
    public void addExpence(RegBillEp ep);


    /**
     * 查询挂号记录
     * @param infoid
     * @return
     */
    List<RegBill> getRecords(String infoid);

    /**
     * 查询医生信息
     * @param drid
     * @return
     */
    Doctor getDrinfo(String drid);

    /**
     * 根据infoid查询医生信息
     * @param infoid
     * @return
     */
    Doctor getDrUserinfo(String infoid);

    /**
     * 删除挂号单
     * @param rbid
     */
    void delRegBill(String rbid);



    /**
     * 根据挂号单查询挂号单信息
     * @param rbid
     * @return
     */
    RegBill getRegBill(String rbid);


    /**
     * 查询用户挂号总记录数
     * @param infoid
     * @return
     */

    Integer getRegCount(String infoid);
    /**
     * 获取费用列表
     * @param regbillid
     * @return
     */
    List<RegBillEp> getregBillEps(String regbillid);

    /**
     * 获取诊断记录
     * @param regbillid
     * @return
     */
    List<RegBillInfo> getRegBillInfos(String regbillid);

    /**
     * 更新挂号单状态
     * @param rbid
     */
    void changeState(String rbid, String state);

    /**
     * 重置账号前的查询用户信息
     * @param email
     * @param username
     * @param realname
     * @return
     */
    UserInfo queryUserInfo(String email,String username,String realname);


    /**
     * 更改密码
     * @param password
     * @param infoid
     */
    void changePassword(String password,String infoid);


    /**
     * 更新总费用
     * @param rbid
     */
    void udTotal(String rbid);


    /**
     *  获取医疗类任务数
     * @param drid
     * @return
     */
    Integer getAptCount(String drid);

    /**
     * 获取管理员列表
     * @return
     */
    List<UserInfo> getAdmins();
    List<UserInfo> getSAdmins();

    void regRight(RegRight regRight);

    /**
     * 添加原有
     * @param taskid
     * @param reason
     */
    void addReason(String taskid, String reason);

    List<RegRight> getRegRights(String infoid);

    Integer getMdc(String rbid);

    List<Medicine> getRbMdList(String rbid);
}

package bai.service;

import bai.dao.UserDao;
import bai.entity.*;
import bai.utils.DateTool;
import bai.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User login(User user) {
        return userDao.login(user);

    }

    /**
     * 根据用户id获取用户信息
     *
     * @param infoid
     * @return
     */
    public UserInfo getInfo(String infoid) {
        UserInfo userInfo = userDao.getUserInfo(infoid);
        switch (userInfo.getRoleid()) {
            case "1":
                userInfo.setRole(Role.User());
                break;
            case "2":
                userInfo.setRole(Role.Doctor());
                break;
            case "3":
                userInfo.setRole(Role.Admin());
                break;
            case "4":
                userInfo.setRole(Role.DoctorAdmin());
                break;
            case "5":
                userInfo.setRole(Role.Root());
                break;
            case "6":
                userInfo.setRole(Role.DoctorRoot());
                break;
            default:
                userInfo.setRole(Role.User());
                break;
        }
        return userInfo;
    }

    /**
     * 存在loginid返回true
     *
     * @param loginid
     * @return
     */
    public boolean checkLoginId(String loginid) {
        if (userDao.checkId(loginid) == 0) {
            return false;
        } else
            return true;
    }

    /**
     * 注册服务
     *
     * @param param
     * @return
     */
    public R regService(Map<String, Object> param) {
        if (checkLoginId(param.get("loginid").toString())) {
            return new R(200, "帐号已存在!!");
        } else if (!param.get("password").toString().equals(param.get("cpassword").toString())) {
            return new R(200, "两次密码不一样!!");
        } else {
            User user = new User();
            user.setId(IDGenerator.getTimeId("user"));
            user.setLoginid(param.get("loginid").toString());
            user.setState(FormState.USERREG);
            user.setPassword(param.get("password").toString());
            System.out.println(user);
            userDao.regUser(user);
            return new R(100, "注册成功", user);
        }

    }

    /**
     * 添加用户信息
     *
     * @param userInfo
     * @param user
     * @return
     */
    public R saveUserInfo(UserInfo userInfo, User user) {

        String email = user.getLoginid();
        userInfo.setId(IDGenerator.getUUID());
        userInfo.setInfoid(IDGenerator.getTimeId("info"));
        userInfo.setEmail(email);
        userInfo.setPicurl("assets/img/defaultpic.jpg");
        userInfo.setRegtime(new Date(new java.util.Date().getTime()));
        userInfo.setUpdatetime(new Date(new java.util.Date().getTime()));
        userInfo.setRoleid("1");

        try {
            userDao.addUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "信息不完整，请重新检查！！");
        }
        try {
            user.setInfoid(userInfo.getInfoid());
            user.setState(FormState.USEROK);
            userDao.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "更新用户错误");
        }

        return new R(100, "保存成功", userInfo);

    }

    public boolean saveUserInfo(UserInfo otUser) {
        try {
            userDao.addPtUser(otUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @return
     */
    public R updateUserInfo(UserInfo userInfo) {

        try {
            userDao.updateUserInfo(userInfo);
            return new R(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new R(200, "信息更新失败!");
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    public R updatePWD(User user) {

        try {
            userDao.updateUser(user);
            return new R(100);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new R(200, "密码更新失败");
    }

    /**
     * 重置账号
     *
     * @param params
     * @return
     */
    public R resetPassword(Map<String, String> params) {
        String[] keys = {"email", "username", "realname", "password", "cpassword"};
        for (String key : keys) {
            if (params.get(key) == null || "".equals(params.get(key).trim()))
                return new R(200, "重置失败!信息不完整！");
        }
        UserInfo userInfo = userDao.queryUserInfo(params.get("email"), params.get("username"), params.get("realname"));
        if (userInfo == null)
            return new R(200, "不存在此账户！！");
        try {
            userDao.changePassword(params.get("password"), userInfo.getInfoid());
            return new R(100, "重置完成");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "重置失败");
        }


    }

    /**
     * 获取科室
     *
     * @param deptid
     * @return
     */
    public R getDeptList(String deptid) {

        List<Dept> depts = null;
        if (deptid == null) {
            depts = userDao.getDeptList();
        } else {
            depts = userDao.getDeptList(deptid);
        }

        if (depts == null) {
            return new R(200, "获取科室失败");
        } else {
            return new R(100, "获取科室成功", depts);
        }

    }

    /**
     * 查询医生列表
     *
     * @param deptid
     * @return
     */
    public R getDoctors(String deptid) {
        List<Doctor> doctors = null;
        if (deptid == null) {
            doctors = userDao.listDoctors("%");
        } else {
            doctors = userDao.listDoctors(deptid + "%");
        }
        if (doctors != null) {
            doctors.forEach(doctor -> {
                doctor.setDrinfo(getInfo(doctor.getDrinfoid()));
                doctor.setDept(userDao.getDept(doctor.getDeptid()));
            });
            return new R(100, "获取成功", doctors);
        }
        return new R(200, "数据获取失败");
    }

    /**
     * 更具drid查询医生信息
     *
     * @param drid
     * @return
     */
    public Doctor getDoctor(String drid) {
        Doctor doctor = userDao.getDrinfo(drid);
        if (doctor == null) {
            return null;
        }
        try {
            doctor.setDrinfo(getInfo(doctor.getDrinfoid()));
            doctor.setDept(userDao.getDept(doctor.getDeptid()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doctor;
    }

    /**
     * 根据infoid查询医生信息
     *
     * @param infoid
     * @return
     */
    public Doctor sessionDoctor(String infoid) {

        Doctor doctor = userDao.getDrUserinfo(infoid);
        if (doctor == null) {
            return null;
        }
        return this.getDoctor(doctor.getId());
    }

    /**
     * 添加挂号单
     *
     * @param regBill
     * @return
     */
    public R makeApot(RegBill regBill) {

        regBill.setRbid(IDGenerator.getUUID());
        regBill.setRegbillid(IDGenerator.getTimeId("rb"));
        regBill.setCreatetime(new Date(new java.util.Date().getTime()));

        try {
            userDao.addAppointment(regBill);
        } catch (Exception e) {
            return new R(200, "挂号失败");
        }
        return new R(100, "挂号成功");
    }

    /**
     * 获取挂号记录
     *
     * @param userInfo
     * @return
     */
    public R getRecords(UserInfo userInfo) {

        List<RegBill> regBills = null;
        try {
            regBills = userDao.getRecords(userInfo.getInfoid());
            for (RegBill regBill : regBills) {
                regBill.setDoctor(this.getDoctor(regBill.getDrid()));
                regBill.setUserinfo(userInfo);
                regBill.setPtinfo(userDao.getUserInfo(regBill.getPtinfoid()));
                regBill.setState(FormState.decodeRbState(regBill.getState()));
                userDao.udTotal(regBill.getRegbillid());
            }
            return new R(100, "获取成功", regBills);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, e.getMessage());
        }
    }


    /**
     * 获取总挂号 数
     *
     * @param infoid
     * @return
     */
    public Integer getRegCount(String infoid) {
        return userDao.getRegCount(infoid);
    }

    /**
     * 删除挂号单
     *
     * @param rbid
     */
    public R delRecord(String rbid) {

        try {
            userDao.delRegBill(rbid);
            return new R(100, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "删除失败！", e);
        }


    }


    /**
     * 查看挂号单
     *
     * @param rbid
     * @return
     */
    public R getRegBill(String rbid) {
        try {
            userDao.udTotal(rbid);
            RegBill regBill = userDao.getRegBill(rbid);
            List<RegBillEp> rbeps = userDao.getregBillEps(regBill.getRegbillid());
            List<RegBillInfo> rbinfos = userDao.getRegBillInfos(regBill.getRegbillid());
            regBill.setExpences(rbeps);
            regBill.setRginfos(rbinfos);
            regBill.setDoctor(getDoctor(regBill.getDrid()));
            regBill.setUserinfo(userDao.getUserInfo(regBill.getUserid()));
            regBill.setPtinfo(userDao.getUserInfo(regBill.getPtinfoid()));
            return new R(100, "获取成功", regBill);
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "获取失败!!", e);
        }

    }

    /**
     * 更改挂号单状态
     *
     * @param rbid
     * @param state
     * @return
     */
    public R changeRbState(String rbid, String state) {
        try {
            userDao.changeState(rbid, state);
            return new R(100, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "操作失败");
        }

    }

    /**
     * 获取医疗类任务数
     *
     * @param drid
     * @return
     */
    public Integer getAptCount(String drid) {
        return userDao.getAptCount(drid);
    }


    /**
     * 获取管理员
     *
     * @return
     */
    public List<UserInfo> getAdmins() {
        List<UserInfo> users = null;
        try {
            users = userDao.getAdmins();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 获取所有超级管理员
     *
     * @return
     */
    public List<UserInfo> getSAdmins() {
        List<UserInfo> users = null;
        try {
            users = userDao.getSAdmins();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 申请权限
     *
     * @param regRight
     * @return
     */
    public boolean regRight(RegRight regRight) {
        try {
            userDao.regRight(regRight);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addReason(String taskid, String reason) {
        userDao.addReason(taskid, reason);
    }

    /**
     * 获取权限申请列表
     *
     * @param infoid
     * @return
     */
    public List<RegRight> getRegList(String infoid) {
        try {
            List<RegRight> regs = userDao.getRegRights(infoid);
            for (RegRight regRight : regs) {
                regRight.setFromuser(userDao.getUserInfo(regRight.getUserid()));
                regRight.setHandlerinfo(userDao.getUserInfo(regRight.getHandler()));
                regRight.setState(FormState.decodeAuState(regRight.getState()));
            }
            return regs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查是否有处方药
     *
     * @param rbid
     * @return
     */
    public boolean checkMd(String rbid) {
        Integer mdc = null;
        try {
            mdc = userDao.getMdc(rbid);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (mdc == null || mdc == 0) {
            return false;
        } else {
            return true;
        }
    }
}

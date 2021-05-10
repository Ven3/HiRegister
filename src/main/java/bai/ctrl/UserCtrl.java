package bai.ctrl;

import bai.entity.*;
import bai.service.UserService;
import bai.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserCtrl {
    @Autowired
    private UserService userService;

    /**
     * 注销登录
     *
     * @return
     */
    @RequestMapping("/clogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "/login";
    }

    /**
     * 用户登录
     *
     * @param loginid
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/clogin")
    public String login(String loginid, String password, HttpSession session, Model model) {

        if ("".equals(loginid) || loginid == null) {
            model.addAttribute("checkname", "请填写用户名");
            return "/login";
        }
        if ("".equals(password) || password == null) {
            model.addAttribute("checkpwd", "密码不能为空");
            return "/login";
        }

        if (!userService.checkLoginId(loginid)) {
            model.addAttribute("msg", "用户未注册");
            return "/login";
        }

        User user = userService.login(new User(loginid, password));

        if (user == null) {
            model.addAttribute("msg", "用户名或密码不正确");
            return "/login";
        }

        //用户资料未完善
        if (FormState.USERREG.equals(user.getState())) {
            session.setAttribute("user", user);
            return "redirect:/addinfo";
        } else if (FormState.USEROK.equals(user.getState())) {
            UserInfo loginuser = userService.getInfo(user.getInfoid());
            session.setAttribute("user", user);
            session.setAttribute("loginuser", loginuser);
            session.setAttribute("regcount", userService.getRegCount(loginuser.getInfoid()));
            Doctor doctor = userService.sessionDoctor(loginuser.getInfoid());
            if (doctor != null) {
                session.setAttribute("drinfo", doctor);
                session.setAttribute("aptcount", userService.getAptCount(doctor.getId()));
            }
            return "redirect:/index";
        } else {
            return "redirect:/login";
        }


    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/cregister")
    public String register(@RequestParam Map<String, Object> reqMap, Model model, HttpSession session) {

        R rdata = userService.regService(reqMap);
        if (rdata.getState() == 100) {
            User user = (User) rdata.getData();
            if (user != null) {
                session.setAttribute("user", user);
                return "/addinfo";
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/register";
        }

    }

    /**
     * 添加用户信息
     *
     * @param userInfo
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/caddinfo")
    public String addInfo(UserInfo userInfo, HttpSession session, Model model) {
        if (session.getAttribute("loginuser") != null) {
            return "/profile";
        }
        User user = (User) session.getAttribute("user");
        if (userInfo != null && user != null) {
            R r = userService.saveUserInfo(userInfo, user);
            if (r.getState() == 100) {
                return "/login";
            } else {
                model.addAttribute("errinfo", r.getMessage());
                return "/addinfo";
            }
        }
        System.out.println(userInfo);
        return "/login";
    }

    @RequestMapping("/reset")
    public String resetAccount(Model model, @RequestParam Map<String, String> params) {
        if (params.size() == 0) {
            return "/reset";
        }
        if (!params.get("password").equals(params.get("cpassword"))) {
            model.addAttribute("msg", "重置失败！确认密码与密码不相同!");
            return "/info";
        }

        R r = userService.resetPassword(params);

        model.addAttribute("msg", r.getMessage());
        return "/info";
    }

    /**
     * 查询用户id
     *
     * @param loginid
     * @return
     */
    @RequestMapping("/checkid")
    @ResponseBody
    public Map<String, String> checkId(String loginid) {
        Map<String, String> msg = new HashMap<String, String>();
        String state = "true";
        String message = "";

        if (userService.checkLoginId(loginid)) {
            state = "false";
            message = "帐号已存在";
        }
        msg.put("state", state);
        msg.put("message", message);

        return msg;
    }

    /**
     * 更新用户信息
     *
     * @return
     */
    @PostMapping("/infoupdate")
    @ResponseBody
    public R updateInfo(String ctype, @RequestParam Map<String, Object> param, HttpSession session) {

        if ("pwd".equals(ctype)) {
            User user = (User) session.getAttribute("user");
            if (user.getPassword().equals(param.get("password"))) {
                if (param.get("npassword") != null && param.get("cnpassword") != null && param.get("npassword").toString().equals(param.get("cnpassword").toString())) {
                    user.setPassword(param.get("npassword").toString());
                    return userService.updatePWD(user);
                } else {
                    return new R(200, "两次新密码不相同");
                }
            } else {
                return new R(200, "原密码不正确");
            }

        } else if ("info".equals(ctype)) {
            UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
            userInfo.setUsername(param.get("username").toString());
            userInfo.setRealname(param.get("realname").toString());
            userInfo.setIdcard(param.get("idcard").toString());
            userInfo.setPhone(param.get("phone").toString());
            userInfo.setAge(param.get("age").toString());

            return userService.updateUserInfo(userInfo);
        } else {
            return new R(200, "请求错误");
        }
    }

    /**
     * 获取科室
     *
     * @return
     */
    @RequestMapping("/cgetdept")
    @ResponseBody
    public R getDept(String deptid) {
        return userService.getDeptList(deptid);
    }

    /**
     * 根据科室id获取医生
     *
     * @param deptid
     * @return
     */
    @RequestMapping("/cgetdoctors")
    @ResponseBody
    public R getDoctors(String deptid) {
        return userService.getDoctors(deptid);
    }


    /**
     * 挂号
     *
     * @param params
     * @param regBill
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/cappoint")
    @ResponseBody
    public R makeAppoint(@RequestParam Map<String, Object> params, RegBill regBill, Model model, HttpSession session) {

        String type = params.get("rgtype").toString().trim();
        if (type == null || "".equals(type)) {
            return new R(200, "错误的操作");
        }
        String[] keys = {"deptid", "drid", "aptdate", "time"};
        for (String key : keys) {
            if (params.get(key) == null || "".equals(params.get(key).toString().trim())) {
                return new R(200, "请完善挂号信息！");
            }
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        regBill.setUserid(userInfo.getInfoid());
        regBill.setUserinfo(userInfo);
        regBill.setState(FormState.RBCOMMIT);
        switch (type) {
            case "personal":
                regBill.setPtinfoid(userInfo.getInfoid());
                regBill.setPtinfo(userInfo);
                R r = userService.makeApot(regBill);
                return r;
            case "otpeople":
                String[] otp = {"ptname","ptgender","ptage","ptbirthday"};
                for (String key : otp) {
                    if (params.get(key) == null || "".equals(params.get(key).toString().trim())) {
                        return new R(200, "请完善患者信息！");
                    }
                }
                UserInfo otUser = new UserInfo();
                otUser.setId(IDGenerator.getUUID());
                otUser.setInfoid(IDGenerator.getTimeId("info"));
                otUser.setUsername(params.get("ptname").toString());
                otUser.setRealname(params.get("ptname").toString());
                otUser.setAge(params.get("ptage").toString());
                otUser.setGender(params.get("ptgender").toString());
                otUser.setBirthday(params.get("ptbirthday").toString());
                otUser.setPicurl("assets/img/defaultpic.jpg");

                if(userService.saveUserInfo(otUser)){
                    regBill.setPtinfoid(otUser.getInfoid());
                    regBill.setPtinfo(otUser);
                    r = userService.makeApot(regBill);
                }else {
                    r = new R(200,"患者信息保存失败");
                }
                return r;
            default:
                return new R(200, "错误的操作");
        }
    }

    /**
     * 查询记录
     *
     * @return
     */
    @RequestMapping("/records")
    public String viewReords(HttpSession session, Model model, String c, String o) {

        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        R r = userService.getRecords(userInfo);
        model.addAttribute("records", r.getData());
        return "/records";
    }

    /**
     * 获取挂号记录
     *
     * @param session
     * @return
     */
    @RequestMapping("/cgetrecords")
    @ResponseBody
    public R getRecords(HttpSession session, Integer c, Integer n, String o) {
        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        if ("pre".equals(o)) {
            return userService.getRecords(userInfo);
        } else if ("nxt".equals(o)) {
            return userService.getRecords(userInfo);
        } else {
            try {
                int pagenum = Integer.parseInt(o);
                return userService.getRecords(userInfo);

            } catch (Exception e) {
                return userService.getRecords(userInfo);
            }
        }
    }


    /**
     * 删除挂号单
     *
     * @param rbid
     * @param model
     * @return
     */
    @RequestMapping("/delrecord")
    public String delRegBill(String rbid, Model model) {

        R r = userService.delRecord(rbid);
        if (r.getState() == 100) {
            return "redirect:/records";
        } else {
            model.addAttribute("msg", r.getData());
            return "/info";
        }
    }

    /**
     * 查看挂号单
     *
     * @param rbid
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/viewrecord")
    public String viewRegBill(String rbid, HttpSession session, Model model) {

//        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        R r = userService.getRegBill(rbid);
        if (r.getState() == 100) {
            model.addAttribute("regbill", r.getData());
            return "/rginfo";
        } else {
            model.addAttribute("msg", r.getData());
            return "/info";
        }
    }


    @RequestMapping("/settlement")
    public String settlement(String rbid, Model model) {
        R r = userService.getRegBill(rbid);
        if (r.getState() == 200) {
            model.addAttribute("msg", "操作失败");
            return "/info";
        }
        RegBill regBill = (RegBill) r.getData();
        List<RegBillEp> eps = regBill.getExpences();
        regBill.setExpences(eps);
        model.addAttribute("regbill",regBill);
        return "/settlement";
    }


    @RequestMapping("/finishrg")
    @ResponseBody
    public R finishRegBill(String rbid, HttpSession session){
        if(userService.checkMd(rbid)){
            userService.changeRbState(rbid,"F");
            return new R(100,"结算完成，请前往取药");
        }else {
            userService.changeRbState(rbid,"H");
            return new R(100,"结算完成");
        }
    }

    /**
     * 取药申请
     * @param rbid
     * @return
     */
    @RequestMapping("/getmdc")
    @ResponseBody
    public R getMedicine(String rbid){
        return userService.changeRbState(rbid,"G");
    }

    @RequestMapping("/listhander")
    @ResponseBody
    public Map<String, String> getAdmins(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        if (userInfo == null | userInfo.getRole().isRoot()) {
            return null;
        }

        List<UserInfo> users = new ArrayList<UserInfo>();
        if (userInfo.getRole().isAdmin()) {
            users = userService.getSAdmins();
        }
        if (!userInfo.getRole().isAdmin()) {
            users = userService.getAdmins();
        }

        Map<String, String> handlers = new HashMap<String, String>();
        if (users != null) {
            for (UserInfo user : users) {
                handlers.put(user.getInfoid(), user.getRealname());
            }
        }
        return handlers;
    }

    @RequestMapping("/printep")
    public String printEp(String rbid, Model model) {
        R r = userService.getRegBill(rbid);
        model.addAttribute("rbinfo", r.getData());
        return "/fiprint";
    }

    @RequestMapping("/authority")
    public String listRegRights(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        if (userInfo == null) {
            model.addAttribute("msg", "操作失败");
            return "/info";
        } else {
            List<RegRight> regRights = userService.getRegList(userInfo.getInfoid());
            model.addAttribute("regrights", regRights);
            return "/authority";
        }

    }

    @PostMapping("/regright")
    @ResponseBody
    public R regRight(String type, String handler, HttpSession session) {

        if (type == null | handler == null) {
            return new R(200, "错误的操作");
        }

        RegRight regRight = new RegRight();
        UserInfo user = (UserInfo) session.getAttribute("loginuser");
        if (user == null) {
            return new R(200, "请先登录");
        }
        switch (type) {
            case "doc":
                if ("1".equals(user.getRoleid()))
                    type = "2";
                if ("3".equals(user.getRoleid()))
                    type = "4";
                if ("5".equals(user.getRoleid()))
                    type = "6";
                break;
            case "adm":
                if ("2".equals(user.getRoleid()))
                    type = "4";
                else
                    type = "3";
                break;
            case "sadm":
                if ("3".equals(user.getRoleid()))
                    type = "5";
                if ("4".equals(user.getRoleid()))
                    type = "6";
                break;
            default:
                return new R(200, "申请失败");
        }
        regRight.setUserid(user.getInfoid());
        regRight.setHandler(handler.trim());
        regRight.setFromrole(user.getRoleid());
        regRight.setTorole(type.trim());
        regRight.setRrid(IDGenerator.getTimeId("rr"));
        regRight.setState("B");

        if (userService.regRight(regRight)) {
            return new R(100, "申请成功");
        }
        return new R(200, "申请失败");
    }
}

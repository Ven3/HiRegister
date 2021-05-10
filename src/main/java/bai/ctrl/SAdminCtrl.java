package bai.ctrl;

import bai.entity.Pages;
import bai.entity.R;
import bai.entity.UserInfo;
import bai.service.SAdminService;
import bai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class SAdminCtrl {

    @Autowired
    private SAdminService sAdminService;

    @Autowired
    private UserService userService;


    @RequestMapping("/users")
    public String getUsers(Model model, Integer c, String o) {
        Pages page = null;
        if (o == null) {
            page = this.getUsers(1, 16, "0");
        } else {
            page = this.getUsers(c, 16, o);
        }
        model.addAttribute("page", page);
        int totalpg = page.getT() / page.getN();
        if (page.getT() % page.getN() > 0) {
            totalpg++;
        }
        model.addAttribute("totalpg", totalpg);
        return "/users";
    }

    @RequestMapping("/cgetusers")
    @ResponseBody
    public Pages getUsers(Integer c, Integer n, String o) {
        if ("pre".equals(o)) {
            return sAdminService.getUsers(c - 1, n);
        } else if ("nxt".equals(o)) {
            return sAdminService.getUsers(c + 1, n);
        } else {
            try {
                int pagenum = Integer.parseInt(o);
                return sAdminService.getUsers(pagenum, n);

            } catch (Exception e) {
                return sAdminService.getUsers(c, n);
            }
        }
    }


    @RequestMapping("/viewuser")
    public String viewUser(String userid, HttpSession session, Model model) {
        UserInfo userInfo = userService.getInfo(userid);
        if (userInfo == null) {
            model.addAttribute("msg", "查看用户失败！");
            return "/info";
        }
        session.setAttribute("viewuser", userInfo);
        model.addAttribute("viewuser", userInfo);
        return "/userinfo";
    }

    @RequestMapping("/deluser")
    @ResponseBody
    public R deleteUser(Model model, String userid) {

        R r = sAdminService.delUser(userid);
        return r;
    }

    @RequestMapping("/uduserinfo")
    @ResponseBody
    public R udUserInfo(Model model, UserInfo userInfo, @RequestParam Map<String, Object> params) {
        R r = sAdminService.udUser(userInfo);
        return r;
    }

    @RequestMapping("/uduserpwd")
    @ResponseBody
    public R udUserPwd(String userid, String pwd, String pwdc) {
        if (pwd == null || "".equals(pwd) || !pwd.equals(pwdc)) {
            return new R(200, "数据有误");
        }
        R r = sAdminService.udUserPwd(userid, pwd, pwdc);
        return r;
    }
}

package bai.ctrl;

import bai.entity.User;
import bai.entity.UserInfo;
import bai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewCtrl {

    @Autowired
    UserService userService;

    @GetMapping("/{view}")
    public String getView(@PathVariable String view){
        String[] views={
                "login","index",
                "profile","addinfo",
                "appointment","re"
                ,"register"
        };
        for(String s:views){
            if(view.equals(s)){
                return view;
            }
        }
        return "/error/404";
    }

    @RequestMapping("/info")
    public String getInfo(Model model){
        try{
            throw new NullPointerException();
        }catch (Exception e){
            String msg = e.getStackTrace().toString();
            model.addAttribute("msg",msg);
        }
        return "/info";
    }


}

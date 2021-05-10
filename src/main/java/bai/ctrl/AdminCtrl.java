package bai.ctrl;

import bai.entity.*;
import bai.service.AdminService;
import bai.service.DoctorService;
import bai.service.UserService;
import bai.utils.DateTool;
import bai.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminCtrl {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;


    @RequestMapping("/tasks")
    public String getTasks(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("loginuser");
        if (userInfo == null) {
            model.addAttribute("msg", "请先登录");
            return "/info";
        }

        model.addAttribute("titel", "权限类任务");
        List<RegRight> regRights = adminService.getRegRights(userInfo.getInfoid());
        if (regRights == null) {
            model.addAttribute("tasks", null);
            return "/tasks";
        }

        List<TaskItem> tasks = new ArrayList<TaskItem>();
        for (RegRight regRight : regRights) {
            TaskItem task = new TaskItem();
            task.setId(regRight.getRrid());
            task.setTaskid(regRight.getRrid());
            task.setApprover(regRight.getFromuser().getRealname());
            task.setCharger(regRight.getHandlerinfo().getRealname());
            task.setExtra(regRight.getExtra());
            task.setState(regRight.getState());
            tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        return "/tasks";
    }

    @RequestMapping("/handletask")
    @ResponseBody
    public R acceptTask(String taskid, String opt, String reason) {
        if (taskid == null || opt == null) {
            return new R(200, "异常操作！！");
        }
        taskid = taskid.trim();
        String[] keys = taskid.split("-");
        switch (keys[0].trim()) {
            case "rb":
                if ("act".equals(opt)) {
                    userService.changeRbState(taskid, FormState.RBAPPOINTED);
                    userService.addReason(taskid, reason);
                    RegBillEp ep = new RegBillEp();
                    ep.setEpid(IDGenerator.getTimeId("ep"));
                    ep.setRbid(taskid);
                    ep.setCreatetime(new Date(new java.util.Date().getTime()));
                    ep.setEpname("挂号费");
                    ep.setEpmoney(6.00);
                    doctorService.saveRbEp(ep);
                    return new R(100, "操作成功!!");
                }
                if ("rjt".equals(opt)) {
                    userService.changeRbState(taskid, FormState.RBSAVE);
                    userService.addReason(taskid, reason);
                    return new R(100, "操作成功!!");
                }
                if ("done".equals(opt)) {
                    userService.changeRbState(taskid, FormState.RBZDWanCheng);
                    userService.addReason(taskid, "已完成");
                    return new R(100, "操作成功!!");
                }
                break;
            case "rr":
                if ("act".equals(opt)) {
                    if (adminService.giveRight(taskid)) {
                        return new R(100, "操作成功!!");
                    }
                }
                if ("rjt".equals(opt)) {
                    adminService.handleRegRight(taskid, FormState.RBSAVE);
                    adminService.addRegRightReason(taskid, reason);
                    return new R(100, "操作成功!!");
                }
                break;

            default:
                return new R(200, "操作失败!!");
        }

        return new R(200, "操作失败!!");
    }

    @RequestMapping("/medicine")
    public String listMedicine() {
        List<Storage> items = doctorService.getStorage();
        items.forEach(item -> {
            item.getMedicine();
        });
        return "/medicine";
    }

    @RequestMapping("/purchase")
    public String buyMedicine(Model model) {

        return "/purchase";
    }

    @RequestMapping("/listproducer")
    @ResponseBody
    public R listProducer() {
        List<Producer> producers = null;
        try {
            producers = adminService.getProducers();
            return new R(100, "获取成功", producers);
        } catch (Exception e) {
            return new R(200, "获取失败");
        }
    }

    @RequestMapping("/getmdlist")
    @ResponseBody
    public R getMedicine(String pdid) {
        List<Medicine> mds = null;
        if(pdid == null){
            mds = adminService.getMedicineList();
            return new R(100,"获取成功",mds);
        }

        mds = adminService.getMedicineByPdId(pdid);
        if (mds == null) {
            return new R(200, "没有记录");
        }
        return new R(100,"获取成功",mds);

    }

    @RequestMapping("/viewmedicine")
    public String viewMedicine(String mid, Model model) {
        Medicine md = doctorService.getMedicineByMid(mid);
        if (md == null) {
            model.addAttribute("msg", "查看失败");
            return "/info";
        }
        Storage sd = adminService.getStorage(mid);
        sd.setMedicine(md);
        if(sd == null){
            model.addAttribute("md",md);
            return "/mdinfo";
        }
        model.addAttribute("md", md);
        model.addAttribute("sd", sd);
        return "/mdinfo";
    }

    @RequestMapping("/udmedicine")
    @ResponseBody
    public R updateMdc(@RequestParam Map<String, Object> params) {
        R r = new R(200, "更新失败");
        String mid = params.get("mid").toString();
        Medicine medicine = null;
        Storage storage = null;
        try {
            medicine = doctorService.getMedicineByMid(mid);
            storage = adminService.getStorage(mid);
        } catch (Exception e) {
            return new R(200, "药品查找失败");
        }
        medicine.setName(params.get("name").toString());
        medicine.setType(params.get("type").toString());
        medicine.setPrice(new Double(Double.parseDouble(params.get("price").toString())));
        medicine.setSpecification(params.get("specification").toString());
        medicine.setFunction(params.get("function").toString());
        medicine.setIngredient(params.get("ingredient").toString());
        medicine.setDetails(params.get("details").toString());
        storage.setAmount(new Integer(Integer.parseInt(params.get("amount").toString())));

        if (adminService.updateMedicine(medicine) && adminService.updateStorage(storage)) {
            r = new R(100, "药品信息更新成功");
        }
        return r;
    }

    /**
     * 取药申请列表
     * @return
     */
    @RequestMapping("/reqmd")
    public String reqMdc(Model model){
        List<RegBill> regbills = adminService.getReqMdcRegbill();
        List<TaskItem> tasks = new ArrayList<TaskItem>();
        regbills.forEach(regBill -> {

            TaskItem task = new TaskItem();
            task.setTasktime(DateTool.getDate(regBill.getAptdate()));
            task.setState(FormState.decodeRbState(regBill.getState()));
            task.setTaskid(regBill.getRegbillid());
            task.setApprover(regBill.getUserinfo().getUsername());
            task.setApdate(DateTool.getDate(regBill.getCreatetime()));
            tasks.add(task);

        });
        model.addAttribute("titel","取药申请");
        model.addAttribute("tasks",tasks);
        return "/tasks";
    }

    /**
     * 取药申请
     * @param rbid
     * @return
     */
    @RequestMapping("/finreqmd")
    @ResponseBody
    public R finishMd(String rbid){
        if("".equals(rbid) || rbid == null){
            return new R(200,"操作失败");
        }
        try{
            userService.changeRbState(rbid,FormState.RBDone);
            return new R(100,"操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return new R(200,"取药失败");
        }
    }

    @RequestMapping("/buymdc")
    @ResponseBody
    public R buyMedicine(String mid,String amount){
        if(mid == null || "" .equals(mid))
        {
            return new R(200,"操作失败");
        }
        try{
            Integer num = Integer.parseInt(amount);
        }catch (Exception e){
            e.printStackTrace();
            return new R(200,"输入数量有误");
        }
        if(adminService.buyMedicine(mid,amount)){
            return new R(100,"操作成功");
        }

        return new R(200,"操作失败");
    }

    @RequestMapping("/mdinfo")
    @ResponseBody
    public Medicine mdInfo(String mid){
        Medicine medicine = adminService.getMedicineByMid(mid);
        return medicine;
    }


}

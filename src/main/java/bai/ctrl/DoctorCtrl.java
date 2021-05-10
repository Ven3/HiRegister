package bai.ctrl;

import bai.entity.*;
import bai.service.DoctorService;
import bai.service.UserService;
import bai.utils.DateTool;
import bai.utils.IDGenerator;
import org.hibernate.mapping.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DoctorCtrl {

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @RequestMapping("/clinic")
    public String getClick(Model model, HttpSession session) {

        Doctor doctor = (Doctor) session.getAttribute("drinfo");

        List<RegBill> regbills = doctorService.getAppoingtment(doctor);
        if (doctor == null || regbills == null) {
            return "/tasks";
        }

        model.addAttribute("titel", "待诊申请记录");

        List<TaskItem> tasks = new ArrayList<TaskItem>();
        regbills.forEach(regBill -> {
            TaskItem task = new TaskItem();
            task.setTaskid(regBill.getRegbillid());
            task.setApprover(regBill.getUserinfo().getUsername());
            task.setApdate(DateTool.getDate(regBill.getCreatetime()));
            task.setTasktime(DateTool.getDate(regBill.getAptdate()));
            task.setState(FormState.decodeRbState(regBill.getState()));
            tasks.add(task);
        });

        model.addAttribute("tasks", tasks);

        return "/tasks";
    }

    @RequestMapping("/findmedicine")
    @ResponseBody
    public R getStorage(String type, String value) {

        List<Storage> items = null;
        try {
            if (type == null || value == null) {
                items = doctorService.getStorage();
            } else {
                switch (type) {
                    case "name":
                        items = doctorService.getItemsByName(value);
                        break;
                    case "type":
                        items = doctorService.getItemsByType(value);
                        break;
                    case "mid":
                        items = doctorService.getItemsByMid(value);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new R(200, "获取错误");
        }
        return new R(100, "获取成功", items);

    }

    @PostMapping("/saveContent")
    @ResponseBody
    public R saveContent(String rbid, String rbcontent) {
        if (rbid == null || rbcontent == null || rbcontent.trim().length() == 0) {
            return new R(200, "操作失败");
        }

        doctorService.saveContent(rbid, rbcontent);
        if (true) {
            return new R(100, "添加成功");
        }

        return new R(200, "保存失败");

    }

    @PostMapping("/getRbContent")
    @ResponseBody
    public R getRbInfoContent(String rbid) {
        if (rbid == null || "".equals(rbid.trim()))
            return new R(100, "获取失败");
        List<RegBillInfo> infos = doctorService.getRbInfo(rbid);
        if (infos == null) {
            return new R(200, "没有数据", null);
        }
        return new R(100, "获取功能", infos);
    }


    /**
     * 完成诊断
     *
     * @param rbid
     * @return
     */
    @RequestMapping("/donerb")
    @ResponseBody
    public R doneRegBill(String rbid) {
        R r = userService.getRegBill(rbid);
        RegBill regBill = null;
        if (r.getState() == 100) {
            regBill = (RegBill) r.getData();
        } else {
            return new R(200, "操作失败");
        }
        R r2 = userService.changeRbState(rbid, FormState.RBZDWanCheng);
        return r2;

    }

    /**
     * 会诊诊断
     *
     * @return
     */
    @RequestMapping("/getmeet")
    @ResponseBody
    public R meet(String rbid, HttpSession session) {
        return userService.changeRbState(rbid, FormState.RBZDing);
    }

    @PostMapping("/deleteRbInfo")
    @ResponseBody
    public R deleteRbInfo(String rbinfoid) {
        R r = new R(200, "操作失败!");
        if (doctorService.delRbInfo(rbinfoid)) {
            r = new R(100, "删除成功!");
        }
        return r;
    }


    @RequestMapping("/getrbeps")
    @ResponseBody
    public R getRbEp(String rbid) {
        R r = new R(200, "获取失败");
        List<RegBillEp> rbeps = doctorService.getRbEps(rbid);
        if (rbeps == null) {
            r = new R(100, "记录为空");
        } else {
            r = new R(100, "获取成功", rbeps);
        }
        return r;
    }

    @RequestMapping("/saverbep")
    @ResponseBody
    public R saveRbEp(@RequestParam Map<String, String> params) {
        R r = new R(200, "添加失败");
        String type = params.get("type");
        Map keys = params;
        if (!("1".equals(type) || "2".equals(type))) {
            return r;
        }
        switch (type) {
            case "1":
                if (params.get("price") != null && params.get("name") != null) {
                    RegBillEp ep = new RegBillEp();
                    ep.setEpid(IDGenerator.getTimeId("ep"));
                    ep.setEpname(params.get("name"));
                    ep.setEpmoney(Double.parseDouble(params.get("price")));
                    ep.setRbid(params.get("rbid"));
                    ep.setCreatetime(new Date(new java.util.Date().getTime()));
                    ep.setExtra(params.get("extra"));
                    if (doctorService.saveRbEp(ep)) {
                        r = new R(100, "新增成功");
                    }
                }
                break;
            case "2":
                if (params.get("name") != null) {
                    Medicine md = doctorService.getMedicineByMid(params.get("name"));
                    RegBillEp ep = new RegBillEp();
                    ep.setEpid(IDGenerator.getTimeId("ep"));
                    ep.setEpname("医药费-" + md.getName());
                    ep.setRbid(params.get("rbid"));
                    ep.setExtra(md.getMid());
                    ep.setEpmoney(md.getPrice());
                    ep.setCreatetime(new Date(new java.util.Date().getTime()));
                    if (doctorService.saveRbEp(ep)) {
                        r = new R(100, "医药费添加成功");
                    }
                }
                break;
        }
        return r;
    }

    @RequestMapping("/deleteep")
    @ResponseBody
    public R deleteEp(String epid) {
        R r = new R(200, "删除失败");
        if (doctorService.delRbEp(epid)) {
            r = new R(100, "删除成功");
        }
        return r;
    }

    @RequestMapping("/viewtask")
    public String viewTask(String taskid, Model model) {
        if (taskid.startsWith("rb-")) {
            R r = userService.getRegBill(taskid);
            if(r.getState() == 200){
                model.addAttribute("msg",r.getMessage());
                return "/info";
            }
            RegBill regBill = (RegBill) r.getData();

            if(FormState.RBQuYao.equals(regBill.getState())){
                List<Medicine> mds = doctorService.getMdList(taskid);
                model.addAttribute("regbill",regBill);
                model.addAttribute("mdlist",mds);
                return "/mdlist";
            }else {
                model.addAttribute("regbill", r.getData());
                return "/rginfo";
            }

        }
        if (taskid.startsWith("rr-")) {

            model.addAttribute("msg", "regright");
        }

        return "/info";
    }

    @RequestMapping("/uddrinfo")
    @ResponseBody
    public R udDrInfo(String deptid, HttpSession session){
        Doctor doctor = (Doctor) session.getAttribute("drinfo");
        if(doctor == null){
            return new R(200,"无权访问");
        }

        doctor.setDeptid(deptid);

        R r = doctorService.udDrinfo(doctor);
        return r;
    }

}

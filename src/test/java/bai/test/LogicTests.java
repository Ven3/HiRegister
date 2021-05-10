package bai.test;

import bai.ApplicationStart;
import bai.entity.*;
import bai.service.AdminService;
import bai.service.DoctorService;
import bai.service.SAdminService;
import bai.service.UserService;
import bai.utils.IDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogicTests {

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    SAdminService sAdminService;

    public void test() {
//        List<RegBill> regBills = userService.getAppoingtment("dr-2014214179");
//        regBills.forEach(regBill -> {
//            System.err.println(regBill.getRegbillid());
//        });
    }

    @Test
    public void changeRbState() {
        RegBill regBill = adminService.getRegbill("rb-30378f6f298d-20180505194215");

        R r = userService.changeRbState("rb-30378f6f298d-20180505194215", "C");

        regBill = adminService.getRegbill("rb-30378f6f298d-20180505194215");
        System.out.println(regBill.getRegbillid());
    }

    @Test
    public void getRegbill() {
        R r = userService.getRegBill("rb-30378f6f298d-20180505194215");
    }


    @Test
    public void testDrDao() {

        doctorService.getMedicineByMid("md-1034");

        List<Medicine> medicines = doctorService.getMedicineByName("");

        List<Storage> storageItems = doctorService.getStorage();
        System.out.println(medicines);
    }

    @Test
    public void testStorage() {

        doctorService.getItemsByType("青霉素");
        doctorService.getItemsByName("西");
        doctorService.getItemsByMid("md-1018");
    }


    @Test
    public void getDept(){
        userService.getDeptList("1");
    }

    @Test
    public void getUsers(){
        Pages pages = sAdminService.getUsers(1,10);
    }

    @Test
    public void getAdmins(){
        List users = userService.getAdmins();
        List sAdmins = userService.getSAdmins();
    }


    @Test
    public void testGetMdc(){
        //根据供应商查询药品信息
        List<Medicine> mds1 = adminService.getMedicineByPdId("prd-002");
        System.out.println(mds1);
        //根据药品名称查询药品信息
        List mds2 = adminService.getMedicineByName("头孢氨苄");
        System.out.println(mds2.size());
        //根据药品编号查询药品信息
        Medicine mdc = adminService.getMedicineByMid("md-1012");
        System.out.println(mdc);
    }

    @Test
    public void testMdPack(){
        List<Medicine> mds = adminService.getMdPack("rb-ff03d1b248c3-20180520214619");
        System.out.println(mds.size());
    }

}

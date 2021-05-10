package bai.service;

import bai.dao.AdminDao;
import bai.dao.DoctorDao;
import bai.dao.UserDao;
import bai.entity.*;
import bai.utils.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    UserDao userDao;

    @Autowired
    DoctorDao doctorDao;

    /**
     * 根据挂号单号查询挂号单信息
     *
     * @param rbid
     * @return
     */
    public RegBill getRegbill(String rbid) {

        RegBill regBill = adminDao.getRegBill(rbid);
        if (regBill != null) {
            try {
                regBill.setRginfos(userDao.getRegBillInfos(rbid));
                regBill.setExpences(userDao.getregBillEps(rbid));
                regBill.setDoctor(userDao.getDrinfo(regBill.getDrid()));
                regBill.setUserinfo(userDao.getUserInfo(regBill.getUserid()));
            } catch (Exception e) {
                return null;
            }
        }
        return regBill;
    }

    /**
     * 新增库存
     *
     * @param item
     */
    public void saveStorage(Storage item) {
        adminDao.saveStorage(item);
    }

    /**
     * 更新库存信息
     *
     * @param newItem
     */
    public boolean updateStorage(Storage newItem) {
        try {
            doctorDao.updateStorage(newItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 根据药品编号获取库存药品信息
     *
     * @param mid
     * @return
     */
    public Storage getStorage(String mid) {
        Storage storage = null;
        try {
            storage = adminDao.getStorage(mid);
        } catch (Exception e) {
            e.printStackTrace();
            storage = null;
        }
        return storage;
    }


    public boolean updateMedicine(Medicine medicine) {
        try {
            adminDao.updateMedicine(medicine);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 获取权限类任务列表
     *
     * @param infoid
     * @return
     */
    public List<RegRight> getRegRights(String infoid) {
        try {
            List<RegRight> regRights = adminDao.getRightTask(infoid);
            for (RegRight regRight : regRights) {
                regRight.setFromuser(userDao.getUserInfo(regRight.getUserid()));
                regRight.setHandlerinfo(userDao.getUserInfo(regRight.getHandler()));
                regRight.setState(FormState.decodeRbState(regRight.getState()));
            }
            return regRights;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void handleRegRight(String rrid, String state) {
        adminDao.handleRegRight(rrid, state);
    }

    public void addRegRightReason(String taskid, String reason) {
        adminDao.addRightReason(taskid, reason);
    }

    /**
     * 授予权限
     *
     * @param taskid
     */
    public boolean giveRight(String taskid) {
        RegRight regRight = adminDao.getRegRight(taskid);
        if (regRight == null) {
            return false;
        }
        regRight.setFromuser(userDao.getUserInfo(regRight.getUserid()));
        regRight.setHandlerinfo(userDao.getUserInfo(regRight.getHandler()));

        UserInfo userInfo = userDao.getUserInfo(regRight.getUserid());
        userInfo.setRoleid(regRight.getTorole());
        try {
            adminDao.giveRight(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if("1".equals(regRight.getFromrole()) || "3".equals(regRight.getFromrole()) || "5".equals(regRight.getFromrole())){
            if("2".equals(regRight.getTorole()) || "4".equals(regRight.getTorole()) || "6".equals(regRight.getTorole())){
                Doctor doctor = new Doctor();
                doctor.setId("dr-"+regRight.getFromuser().getEmail());
                doctor.setDrinfoid(regRight.getUserid());
                doctor.setHiredate(DateTool.getDate());
                adminDao.saveDrInfo(doctor);
            }
        }





        handleRegRight(taskid, FormState.AUDone);
        addRegRightReason(taskid, "授权成功");
        return true;

    }

    public List<Producer> getProducers() {
        List<Producer> producers = adminDao.getProducers();
        if (producers == null) {
            return null;
        } else {
            return producers;
        }
    }

    public List<Medicine> getMedicineByPdId(String pdid) {
        List<Medicine> mds = adminDao.getMedicine("pdid", pdid);
        if (mds != null) {
            for (Medicine medicine : mds) {
                medicine.setProducer(doctorDao.getProducer(medicine.getPdid()));
            }
        }
        return mds;
    }

    public List<Medicine> getMedicineByName(String name) {
        List<Medicine> mds = adminDao.getMedicine("name", "%" + name + "%");
        if (mds != null) {
            for (Medicine medicine : mds) {
                medicine.setProducer(doctorDao.getProducer(medicine.getPdid()));
            }
        }
        return mds;
    }

    public Medicine getMedicineByMid(String mid) {
        List<Medicine> mds = adminDao.getMedicine("mid", mid);
        if (mds.get(0) == null) {
            return null;
        }
        Medicine medicine = mds.get(0);
        medicine.setProducer(doctorDao.getProducer(medicine.getPdid()));
        return medicine;
    }

    public List<Medicine> getMedicineList() {
        List<Medicine> mds = adminDao.getMedicineList();
        if (mds != null) {
            for (Medicine medicine : mds) {
                medicine.setProducer(doctorDao.getProducer(medicine.getPdid()));
            }
        }
        return mds;
    }

    /**
     * 根据挂号单获取处方药列表
     */
    public List<Medicine> getMdPack(String rbid) {
        List<Medicine> mds = adminDao.getMdPack(rbid);
        return mds;

    }

    public List<RegBill> getReqMdcRegbill() {
        List<RegBill> regBills = null;
        try {
            regBills = adminDao.getMdRegbill();
        } catch (Exception e) {
            return null;
        }
        regBills.forEach(regBill -> {
            regBill.setUserinfo(userDao.getUserInfo(regBill.getUserid()));
            regBill.setDoctor(userDao.getDrinfo(regBill.getDrid()));
            userDao.udTotal(regBill.getRegbillid());
        });
        return regBills;
    }

    //购买药品的逻辑
    public boolean buyMedicine(String mid, String amount) {

        return true;
    }
}

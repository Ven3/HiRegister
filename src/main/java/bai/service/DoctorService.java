package bai.service;

import bai.dao.DoctorDao;
import bai.dao.UserDao;
import bai.entity.*;
import bai.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private UserDao userDao;

    /**
     * 查询我的医疗类任务记录
     *
     * @param doctor
     * @return
     */
    public List<RegBill> getAppoingtment(Doctor doctor) {
        List<RegBill> regBills = null;
        try{
            regBills = doctorDao.getOpt(doctor.getId());
        }catch (Exception e){
            return null;
        }
        regBills.forEach(regBill -> {
            regBill.setUserinfo(userDao.getUserInfo(regBill.getUserid()));
            regBill.setDoctor(doctor);
            userDao.udTotal(regBill.getRegbillid());
        });
        return regBills;
    }


    public Medicine getMedicineByMid(String mid) {

        List<Medicine> mds = doctorDao.getMedicine("mid", mid);
        if (mds.size() == 0)
            return null;
        mds.forEach(md -> {
            md.setProducer(doctorDao.getProducer(md.getPdid()));
        });
        return mds.get(0);
    }


    public List<Medicine> getMedicineByName(String name) {
        List<Medicine> mds = (List<Medicine>) doctorDao.getMedicine("name", "%" + name + "%");
        if (mds.size() == 0) {
            return null;
        }
        mds.forEach(md -> {
            md.setProducer(doctorDao.getProducer(md.getPdid()));
        });
        return mds;
    }

    public List<Storage> getStorage() {
        List<Storage> storageItems = doctorDao.getStorage();

        if (storageItems.size() < 0) {
            return null;
        }
        storageItems.forEach(item -> {
            item.setMedicine(this.getMedicineByMid(item.getMid()));
        });
        return storageItems;
    }

    public List<Storage> getItemsByName(String name) {
        List<Storage> items = doctorDao.getItems("name", "%" + name + "%");
        items.forEach(item -> {
            item.setMedicine(this.getMedicineByMid(item.getMid()));
        });
        return items;
    }

    public List<Storage> getItemsByMid(String mid) {
        List<Storage> items = doctorDao.getItems("mid", "%" + mid + "%");
        items.forEach(item -> {
            item.setMedicine(this.getMedicineByMid(item.getMid()));
        });
        return items;
    }

    public List<Storage> getItemsByType(String type) {
        List<Storage> items = doctorDao.getItems("type", "%" + type + "%");
        items.forEach(item -> {
            item.setMedicine(this.getMedicineByMid(item.getMid()));
        });
        return items;
    }

    /**
     * 新增诊断记录
     * @param rbid
     * @param rbcontent
     * @return
     */
    public boolean saveContent(String rbid, String rbcontent) {

        RegBillInfo rbinfo = new RegBillInfo();
        rbinfo.setRbinfoid(IDGenerator.getTimeId("rbep"));
        rbinfo.setRbid(rbid);
        rbinfo.setContent(rbcontent);
        rbinfo.setCreatetime(new Date(new java.util.Date().getTime()));
        try {
            doctorDao.saveRbInfo(rbinfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取诊断记录
     * @param rbid
     * @return
     */
    public List<RegBillInfo> getRbInfo(String rbid) {
        List<RegBillInfo> infos = userDao.getRegBillInfos(rbid);
        return infos;
    }

    /**
     * 新增费用项
     * @return
     */
    public boolean saveRbEp(RegBillEp regBillEp){
        regBillEp.setEpid(IDGenerator.getTimeId("rbep"));
        regBillEp.setCreatetime(new Date(new java.util.Date().getTime()));
        try{
            userDao.addExpence(regBillEp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取费用项
     * @param rbid
     * @return
     */
    public List<RegBillEp> getRbEps(String rbid){
        List<RegBillEp> regBillEps = userDao.getregBillEps(rbid);
        return regBillEps;
    }

    /**
     * 删除诊断记录
     * @param rbinfoid
     * @return
     */
    public boolean delRbInfo(String rbinfoid){
        boolean flag = false;
        try {
            doctorDao.delRbInfo(rbinfoid);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 删除费用项
     * @param epid
     * @return
     */
    public boolean delRbEp(String epid){
     boolean flag = false;
     try{
         doctorDao.delRbEp(epid);
         flag = true;
     }catch (Exception e){
         e.printStackTrace();
     }

     return flag;
    }


    /**
     * 获取挂号单中的药品列表
     * @param rbid
     * @return
     */
    public List<Medicine> getMdList(String rbid) {
        List<Medicine> mds = userDao.getRbMdList(rbid);
        return mds;
    }

    public R udDrinfo(Doctor doctor) {
        try{
            doctorDao.udDrInfo(doctor);
            return new R(100,"更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new R(200,"更新失败");
        }
    }
}

package bai.dao;

import bai.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {

    /**
     * 根据挂号单号查找挂号单
     * @param rbid
     * @return
     */
    RegBill getRegBill(String rbid);

    /**
     * 新增库存
     * @param storage
     */
    void saveStorage(Storage storage);


    /**
     * 根据药品编号获取库存中药品信息
     * @param mid
     * @return
     */
    Storage getStorage(String mid);

    /**
     * 更新药品信息
     * @param medicine
     */
    void updateMedicine(Medicine medicine);

    List<RegRight> getRightTask(String infoid);

    void handleRegRight(String rrid, String state);

    void addRightReason(String taskid, String reason);

    RegRight getRegRight(String rrid);

    void giveRight(UserInfo userInfo);

    List<Producer> getProducers();

    List<Medicine> getMedicine(String colum, String filter);

    List<Medicine> getMedicineList();

    List<Medicine> getMdPack(String rbid);

    List<RegBill> getMdRegbill();

    void buyMd(String mid, String amount);

    void saveDrInfo(Doctor doctor);
}

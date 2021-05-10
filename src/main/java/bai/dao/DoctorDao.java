package bai.dao;

import bai.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDao {

    /**
     * 查询drid用户的所有挂号单
     * @param drid
     * @return
     */
    List<RegBill> getOpt(String drid);


    /**
     * 查看药品库存
     * @return
     */
    List<Storage> getStorage();

    /**
     * 库存搜索
     * @param colum
     * @param pattern
     * @return
     */
    List<Storage> getItems(String colum, String pattern);

    /**
     * 查询药物信息
     * @param colum 列名
     * @param param 参数
     * @return
     */
    List<Medicine> getMedicine(String colum, String param);


    /**
     * 获取厂家信息
     * @param pdid
     * @return
     */
    Producer getProducer(String pdid);

    /**
     * 更新库存
     * @param storage
     */
    void updateStorage(Storage storage);


    /**
     * 保存诊断记录
     * @param rbinfo
     */
    void saveRbInfo(RegBillInfo rbinfo);

    /**
     * 删除诊断记录
     * @param rbinfoid
     */
    void delRbInfo(String rbinfoid);

    /**
     * 删除费用项
     * @param epid
     */
    void delRbEp(String epid);

    void udDrInfo(Doctor doctor);
}

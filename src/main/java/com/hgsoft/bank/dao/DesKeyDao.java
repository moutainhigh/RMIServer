package com.hgsoft.bank.dao;

import com.hgsoft.bank.entity.BankCardLock;
import com.hgsoft.bank.entity.DesKey;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author : 吴锡霖
 * file : DesKeyDao.java
 * date : 2017-06-01
 * time : 20:19
 */
@Repository
public class DesKeyDao extends BaseDao {

    @Resource
    private SequenceUtil sequenceUtil;
    
    public BankCardLock findByVehicle(String vehiclePlate, String vehicleColor) {
        String sql = "select * from csms_bank_card_lock where vehiclePlate=? and vehicleColor=?";
        try {
            return queryRowObject(sql, BankCardLock.class, vehiclePlate, vehicleColor);

        } catch (EmptyResultDataAccessException e) {
           return null; 
        }
    }

    public DesKey findDesKey(String sql, String terminalNo, String bankNo) {
        try {
            return queryRowObject(sql, DesKey.class, terminalNo, bankNo);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

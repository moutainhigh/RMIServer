package com.hgsoft.bank.dao;

import com.hgsoft.bank.entity.BankCardLock;
import com.hgsoft.common.dao.BaseDao;
import com.hgsoft.utils.SequenceUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author : 吴锡霖
 * file : BankCardLockDao.java
 * date : 2017-06-01
 * time : 20:19
 */
@Repository
public class BankCardLockDao extends BaseDao {

    @Resource
    SequenceUtil sequenceUtil;
    
    public BankCardLock findByVehicle(String vehiclePlate, String vehicleColor) {
        String sql = "select * from csms_bank_card_lock where vehiclePlate=? and vehicleColor=?";
        try {
            return queryRowObject(sql, BankCardLock.class, vehiclePlate, vehicleColor);

        } catch (EmptyResultDataAccessException e) {
           return null; 
        }
    }

    public void save(BankCardLock bankCardLock, int maxLockDay) {
        Long id = sequenceUtil.getSequenceLong("SEQ_CSMS_BANK_CARD_LOCK_NO");
        StringBuffer sql = new StringBuffer();
        int lockDay = bankCardLock.getLockDay();
        if (lockDay > maxLockDay) {
            lockDay = maxLockDay;
        }
        sql.append("insert into csms_bank_card_lock (id, operNo, terminalNo,")
                .append(" businessTime, vehiclePlate, vehicleColor, vehicleType,")
                .append(" vehicleFlag, vehicleWeightLimits, owner, address, ")
                .append("usingNature, model, identificationCode, vehicleEngineNo,")
                .append(" registeredDate, issueDate, bankNo, lockDay) values (?,")
                .append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        save(sql.toString(), id, bankCardLock.getOperNo(), 
                bankCardLock.getTerminalNo(), bankCardLock.getBusinessTime(),
                bankCardLock.getVehiclePlate(), bankCardLock.getVehicleColor(), 
                bankCardLock.getVehicleType(), bankCardLock.getVehicleFlag(), 
                bankCardLock.getVehicleWeightLimits(), bankCardLock.getOwner(), 
                bankCardLock.getAddress(), bankCardLock.getUsingNature(), 
                bankCardLock.getModel(), bankCardLock.getIdentificationCode(), 
                bankCardLock.getVehicleEngineNo(), bankCardLock.getRegisteredDate(),
                bankCardLock.getIssueDate(), bankCardLock.getBankNo(), lockDay);
    }

    public void delete(BankCardLock bankCardLock) {
        String sql = "delete from csms_bank_card_lock where vehiclePlate=? and vehicleColor=?";
        delete(sql, bankCardLock.getVehiclePlate(), bankCardLock.getVehicleColor());
    }
}

package com.hgsoft.bank.service;

import com.hgsoft.bank.dao.BankCardLockDao;
import com.hgsoft.bank.dao.CommonBusinessInfoDao;
import com.hgsoft.bank.dao.DesKeyDao;
import com.hgsoft.bank.entity.BankCardLock;
import com.hgsoft.bank.entity.DesKey;
import com.hgsoft.bank.serviceInterface.ICommonBusinessInfoService;
import com.hgsoft.customer.dao.VehicleInfoDao;
import com.hgsoft.exception.ApplicationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CommonBusinessInfoService implements ICommonBusinessInfoService {
	private static Logger logger = Logger.getLogger(AuthInfoService.class.getName());
	
	@Resource
	private CommonBusinessInfoDao commonBusinessInfoDao;
	@Resource
	private DesKeyDao desKeyDao;
	@Resource
	private VehicleInfoDao vehicleInfoDao;
	@Resource
	private BankCardLockDao bankCardLockDao;
	
	//todo:从数据库获取
	private static final int MAX_LOCK_DAY = 30;
	
	/**
	 *车牌信息锁定
	 */
	@Override
	public void vehicleInfoLock(BankCardLock bankCardLock){
		try {
			logger.debug("车牌信息锁定");
			/*VehicleInfo vehicleInfo = vehicleInfoDao.loadByPlateAndColor(
					bankCardLock.getVehiclePlate(),
					bankCardLock.getVehicleColor());
			if (vehicleInfo == null) {
				throw new ApplicationException("该车牌不存在");
			}*/
			boolean hasBindCard = vehicleInfoDao.ifHasBindCard(bankCardLock.getVehiclePlate(),
					bankCardLock.getVehicleColor());
			if (hasBindCard) {
				throw new ApplicationException("该车牌已经被绑定");
			}
			BankCardLock _bankCardLock = bankCardLockDao
                    .findByVehicle(bankCardLock.getVehiclePlate(),
					bankCardLock.getVehicleColor());
			
			if (_bankCardLock == null) {
                logger.debug("允许锁定,插入到锁定表");
                bankCardLockDao.save(bankCardLock, MAX_LOCK_DAY);
				return;
			}
            if (_bankCardLock.hasExpire()) {
                logger.debug("已经超期,删除重新插入");
                bankCardLockDao.delete(bankCardLock);
                bankCardLockDao.save(bankCardLock, MAX_LOCK_DAY);
                return;
            }
            String lockedBankNo = _bankCardLock.getBankNo();
			String bankNo = bankCardLock.getBankNo();
			if (bankNo.equals(lockedBankNo)) {
                throw new ApplicationException("已经锁定过,无需再锁定");
			}
            throw new ApplicationException("其他银行已经锁定,不允许锁定");
		} catch (Exception e) {
			logger.error("车牌信息锁定失败", e);
			throw new ApplicationException("车牌信息锁定失败,"+e.getMessage());
		}
	}

    /**
     *车牌信息解锁
     */
    @Override
    public void vehicleInfoUnlock(BankCardLock bankCardLock){
        try {
            logger.debug("车牌信息解锁");
			/*VehicleInfo vehicleInfo = vehicleInfoDao.loadByPlateAndColor(
					bankCardLock.getVehiclePlate(),
					bankCardLock.getVehicleColor());
			if (vehicleInfo == null) {
				throw new ApplicationException("该车牌不存在");
			}*/
            BankCardLock _bankCardLock = bankCardLockDao
                    .findByVehicle(bankCardLock.getVehiclePlate(),
                            bankCardLock.getVehicleColor());

            if (_bankCardLock == null) {
                logger.debug("未锁定,解锁失败");
                throw new ApplicationException("该车牌未锁定,解锁失败");
            }
            String lockedBankNo = _bankCardLock.getBankNo();
            String bankNo = bankCardLock.getBankNo();
            if (bankNo.equals(lockedBankNo)) {
                logger.debug("银行编码相等：" + bankNo + ",允许解锁");
                bankCardLockDao.delete(bankCardLock);
                return;
            }
            throw new ApplicationException("其他银行已经锁定,不允许解锁");
        } catch (Exception e) {
            logger.error("车牌信息解锁失败", e);
            throw new ApplicationException("车牌信息解锁失败,"+e.getMessage());
        }
    }
    
	/**
	 *车牌信息查询
	 */
	@Override
	public Map<String, Object> getLicenseInfo(String vehiclePlate,
			String vehicleColor) {
		try {
			Map<String, Object> licenseInfo = commonBusinessInfoDao
					.getLicenseInfo(vehiclePlate, vehicleColor);
			if (licenseInfo == null) {
				return null;
			}
			boolean hasBindCard = vehicleInfoDao.ifHasBindCard(vehiclePlate,
					vehicleColor);
			int flag;
			BankCardLock _bankCardLock = bankCardLockDao
					.findByVehicle(vehiclePlate, vehicleColor);
			/*
			* 0：还没被锁定，允许锁定或绑定。
			  1：已经被锁定，不允许继续操作。
			  2：已经被绑定，不允许继续操作。
			* */
			if (_bankCardLock != null && !_bankCardLock.hasExpire()) {
				flag = 1;
				licenseInfo.put("LOCKUNIT", _bankCardLock.getBankNo());
			} else if (hasBindCard) {
				flag = 2;
			} else {
				flag = 0;
			}
			licenseInfo.put("LOCKFLAG", flag);
			return licenseInfo;
		} catch (Exception e) {
			logger.error("车牌信息查询", e);
			throw new ApplicationException("车牌信息查询" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getVehicleInfo(String cardNo, String cardType) {
		try {
			//储值卡
			if("22".equals(cardType)) {
				return commonBusinessInfoDao.getVehicleInfoByPreC(cardNo);
			//记帐卡
			} else if ("23".equals(cardType)) {
				return commonBusinessInfoDao.getVehicleInfoByAccC(cardNo);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("卡片车牌信息查询", e);
			throw new ApplicationException("卡片车牌信息查询" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getCardStateInfo(String cardNo, String cardType) {
		try {
			// 储值卡
			if ("22".equals(cardType) || "05".equals(cardType)) {
				return commonBusinessInfoDao.getCardStateInfoByPreC(cardNo,
						cardType);
			// 记帐卡
			} else if ("23".equals(cardType) || "06".equals(cardType)) {
				return commonBusinessInfoDao.getCardStateInfoByAccC(cardNo,
						cardType);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("卡片状态查询接口", e);
			throw new ApplicationException("卡片状态查询接口" + e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getflagandTollcarInfo(String date,
			String vehiclePlate, String vehicleColor, String VehicleType,
			String vehicleWeightLimits) {
		try {
			return commonBusinessInfoDao.getflagandTollcarInfo(date,
					vehiclePlate, vehicleColor, VehicleType,
					vehicleWeightLimits);
		} catch (Exception e) {
			logger.error("取锁定标识和国标收费车型", e);
			throw new ApplicationException("取锁定标识和国标收费车型" + e.getMessage());
		}
	}

	@Override
	public DesKey findDesKey(String terminalNo, String bankNo) {
		String sql = "select * from csms_bank_3des_key where terminalNo=? and bankNo=?";
		return desKeyDao.findDesKey(sql, terminalNo, bankNo);
	}

	@Override
	public void updateDesKey(DesKey desKey) {
		String sql = "update csms_bank_3des_key set desKey=? where id=?";
		desKeyDao.update(sql, desKey.getDesKey(), desKey.getId());
	}

}

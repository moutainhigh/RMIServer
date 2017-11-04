package com.hgsoft.clearInterface.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.clearInterface.dao.CardObuDao;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.entity.AccountCSecondIssued;
import com.hgsoft.clearInterface.entity.PrepaidCSecondIssued;
import com.hgsoft.clearInterface.entity.UserStateInfo;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.prepaidC.entity.PrepaidC;

@Service
public class CardSecondIssuedService{
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;
	@Resource
	private CardObuDao cardObuDao;

	/**
	 * 保存儲值卡发行信息至清算系统
	 * @param prepaidC
	 */
	public void savePrepaidCard(PrepaidC prepaidC){
		PrepaidCSecondIssued cardSecondIssued = new PrepaidCSecondIssued();
		cardSecondIssued.setCardCode(prepaidC.getCardNo());
		cardSecondIssued.setCardType("22");
		cardSecondIssued.setSdate(prepaidC.getSaleTime());
		cardSecondIssued.setStatus(0);
		cardSecondIssued.setUpdatetime(new Date());
		cardSecondIssuedDao.savePrepaidCCardIssued(cardSecondIssued);
	}
	
	/**
	 * 保存删除储值卡删除信息至清算系统
	 * @param prepaidC
	 */
	public void deletePrepaidCard(PrepaidC prepaidC,VehicleInfo vehicleInfo){
		Date currentDate = new Date();
		UserStateInfo userStateInfo = new UserStateInfo();
		userStateInfo.setGenTime(currentDate);
		userStateInfo.setDealFlag(3);
		userStateInfo.setVehColor(new Integer(vehicleInfo.getVehicleColor()));
		userStateInfo.setLicense(vehicleInfo.getVehiclePlate());
		userStateInfo.setVehType(vehicleInfo.getVehicleType());
		userStateInfo.setCardCode(prepaidC.getCardNo());
		userStateInfo.setRemark("储值卡删除发行");
		userStateInfo.setCardType("22");
		cardObuDao.saveUserStateInfo(userStateInfo);
		
		PrepaidCSecondIssued cardSecondIssued = new PrepaidCSecondIssued();
		cardSecondIssued.setCardCode(prepaidC.getCardNo());
		cardSecondIssued.setCardType("22");
		cardSecondIssued.setSdate(prepaidC.getSaleTime());
		cardSecondIssued.setStatus(1);
		cardSecondIssued.setUpdatetime(currentDate);
		cardSecondIssuedDao.savePrepaidCCardIssued(cardSecondIssued);
	}
	
	/**
	 * 保存记帐卡信息至清算系统
	 * @param accountC
	 */
	public void saveAccountCard(AccountCInfo accountC,AccountCApply accountCApply){
		AccountCSecondIssued cardSecondIssued = new AccountCSecondIssued();
		cardSecondIssued.setCardCode(accountC.getCardNo());
		cardSecondIssued.setCardType("23");
		cardSecondIssued.setSdate(accountC.getIssueTime());
		cardSecondIssued.setStatus(0);
		cardSecondIssued.setBankAccount(accountCApply.getBankAccount());
		cardSecondIssued.setBankNo(accountCApply.getObaNo());
		cardSecondIssued.setUpdatetime(new Date());
		cardSecondIssuedDao.saveAccountCCardIssued(cardSecondIssued);
	}
	
	
	/**
	 * 保存记帐卡删除信息至清算系统
	 * @param accountC
	 */
	public void deleteAccountCard(AccountCInfo accountC){
		
	}
}

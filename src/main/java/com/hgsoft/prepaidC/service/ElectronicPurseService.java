package com.hgsoft.prepaidC.service;

import com.hgsoft.account.dao.MainAccountInfoDao;
import com.hgsoft.account.dao.RefundInfoDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.account.serviceInterface.IAccountFundChangeService;
import com.hgsoft.clearInterface.entity.CardBalanceData;
import com.hgsoft.prepaidC.dao.ElectronicPurseDao;
import com.hgsoft.prepaidC.entity.DbasCardFlow;
import com.hgsoft.prepaidC.entity.ElectronicPurse;
import com.hgsoft.prepaidC.serviceInterface.IElectronicPurseService;
import com.hgsoft.utils.DateUtil;
import com.hgsoft.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ElectronicPurseService implements IElectronicPurseService{
	
	@Resource
	private ElectronicPurseDao electronicPurseDao;
	@Resource
	private RefundInfoDao refundInfoDao;
	@Resource
	private IAccountFundChangeService accountFundChangeService;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	@Resource
	private MainAccountInfoDao mainAccountInfoDao;
	
	@Resource
	public void setElectronicePurseDao(ElectronicPurseDao electronicPurseDao) {
		this.electronicPurseDao = electronicPurseDao;
	}
	
	@Override
	public ElectronicPurse findByPrepaidID(Long prepaidID) {
		return electronicPurseDao.findByPrepaidID(prepaidID);
	}
	
	@Override
	public void saveTransferMoney(){
		Date currentDate = new Date();
		//获取所有已注销或者换卡，并且没有完成充值且过了资金争议区的资金转移确认表的记录
		List<DbasCardFlow> transMoneyList = electronicPurseDao.findTransMoneyList(DateUtil.formatDate(currentDate, "yyyyMMddHHmmss"));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(DbasCardFlow dbasCardFlow:transMoneyList){
			if(StringUtil.isEquals(dbasCardFlow.getCardType(), "22")){
				//如果是注销的，需要更新资金转移确认表
				if(StringUtil.isEquals(dbasCardFlow.getSerType(), "01")){
					//获取主账户信息
					SubAccountInfo subAccountInfo = subAccountInfoDao.findByPrepaidCNo(dbasCardFlow.getCardNo());
					if(subAccountInfo == null){
						continue;
					}
					if(!saveTransferMoney(dbasCardFlow)){
						continue;
					}
					
					//更新主账户退款记录表
					refundInfoDao.updateStatusAndCurReBa("1", dbasCardFlow.getCardAmt(), dbasCardFlow.getCardNo());
					//保存账户资金变动数据
					accountFundChangeService.saveAccountFundChange(subAccountInfo.getMainId(),dbasCardFlow.getCardAmt(), dbasCardFlow.getCardNo());
					//更新主账户信息
					mainAccountInfoDao.updateStatusAndCurReBa(subAccountInfo.getMainId(), dbasCardFlow.getCardAmt());
				}
				if(StringUtil.isEquals(dbasCardFlow.getSerType(), "02")
						&&StringUtil.isEquals(dbasCardFlow.getReadFlag(), "0")){
					saveTransferMoney(dbasCardFlow);
				}
				
				
			}
			
		}
	}
	
	public Boolean saveTransferMoney(DbasCardFlow dbasCardFlow){
		
		String cardNo = dbasCardFlow.getCardNo();
		
		CardBalanceData cardBalanceData = electronicPurseDao.findCardBalanceByCardNo(cardNo,DateUtil.formatDate(dbasCardFlow.getExpireTime(), "yyyyMMddHHmmss"));
		
		if(cardBalanceData != null){
			BigDecimal balance = cardBalanceData.getCardBalance();
			Date balanceDate = cardBalanceData.getBalanceTime();
			Date updateTime = new Date();
			electronicPurseDao.updateCardMoney(dbasCardFlow.getId(),balance,balanceDate,updateTime,0);
			return true;
		}else{
			return false;
		}
		
	}

}

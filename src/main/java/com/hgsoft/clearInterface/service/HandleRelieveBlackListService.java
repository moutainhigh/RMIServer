package com.hgsoft.clearInterface.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.HandleRelieveBlackListDao;
import com.hgsoft.clearInterface.entity.HandleRelieveBlackList;
import com.hgsoft.clearInterface.serviceInterface.IHandleRelieveBlackListService;
import com.hgsoft.common.dao.EtcTollingBaseDao;

@Service
public class HandleRelieveBlackListService extends EtcTollingBaseDao implements IHandleRelieveBlackListService{

	@Resource
	private HandleRelieveBlackListDao handleRelieveBlackListDao;
	@Override
	public Boolean saveHandleRelieveBlackList(HandleRelieveBlackList handleRelieveBlackList) {
		try{
			handleRelieveBlackListDao.saveHandleRelieveBlackList(handleRelieveBlackList);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Boolean saveHandleRelieveBlackList(String bankAccount, String cardNo, Date handPayTime, Double handPayFee,
			Double etcMoney, Double lateFee,String payaccount,String payflag, String remark) {
		try {
			HandleRelieveBlackList handleRelieveBlackList = new HandleRelieveBlackList(bankAccount, cardNo, handPayTime,
					handPayFee, etcMoney, lateFee, payaccount, payflag, remark, new Date());
			handleRelieveBlackListDao.saveHandleRelieveBlackList(handleRelieveBlackList);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

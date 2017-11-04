package com.hgsoft.clearInterface.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.CardObuDao;
import com.hgsoft.clearInterface.entity.CardStateInfo;
import com.hgsoft.clearInterface.entity.PrepaidCBalance;
import com.hgsoft.clearInterface.entity.UserStateInfo;
import com.hgsoft.clearInterface.serviceInterface.ICardObuService;
import com.hgsoft.common.dao.EtcTollingBaseDao;

@Service
public class CardObuService extends EtcTollingBaseDao
implements ICardObuService{

	@Resource
	private CardObuDao cardObuDao;
	@Override
	public Boolean prepaidCIssue(String cardCode, Date sDate, Integer status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean accountCIssue(String cardCode, String bankNo, String bankAccount, Date sDate, Integer status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean accountCUserInfo(String organ, String accountType, String bankNo, String accName, String bankAccount,
			String bankName, String spanBankNo, String reconBankNo, String branchBankNo, String virType,
			String virCount, BigDecimal maxAcr, String systemType, Date reqTime, Date checkTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean userStatusInfo(Date genTime, String stateFlag, String serType, String cardCode, String cardType,
			String userType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCardBalance(String cardCode,BigDecimal systemBalance,BigDecimal returnMoney,
			Date genTime,String account,String remark,Date updateTime){
		PrepaidCBalance prepaidCBalance = new PrepaidCBalance( cardCode, systemBalance, returnMoney,
				 genTime, account, remark, updateTime);
		cardObuDao.saveCardBalance(prepaidCBalance);
		
	}
	@Override
	public void saveUserStateInfo(Date genTime, Integer delFlag, String cardCode, String cardType, Integer vehColor,
			String license, String vehType,String obuCode, String obuSeq, Date obuIssueTime, Date obuExpireTime,String remark) {
		UserStateInfo userStateInfo = new UserStateInfo( genTime, delFlag,	 cardCode,	
				 cardType, vehColor, license, vehType,	obuCode,
				 obuSeq, obuIssueTime, obuExpireTime, remark);
		cardObuDao.saveUserStateInfo(userStateInfo);
	}

	@Override
	public void saveCardStateInfo(Date genTime, Integer stateFlag, String serType, String cardCode, String cardType,
			String userType) {
		CardStateInfo cardStateInfo = new CardStateInfo(genTime, stateFlag,  serType,  cardCode,  cardType,	 userType);
		cardObuDao.saveCardStateInfo(cardStateInfo);
	}

	@Override
	public void saveCardUnusable(Date tradeTime, int stateFlag, String serType, String cardNo, String cardType,
			String userType,Integer vehColor,String license,String vehType) {
		//保存卡片状态信息
		CardStateInfo cardStateInfo = new CardStateInfo(tradeTime, stateFlag,  serType,  cardNo,  cardType,	 userType);
		cardObuDao.saveCardStateInfo(cardStateInfo);
		UserStateInfo userStateInfo = new UserStateInfo(tradeTime,3,	cardNo,	
				cardType,vehColor,license,vehType,null,	null,null,null,"记账卡旧卡注销");
		//保存用户信息
		cardObuDao.saveUserStateInfo(userStateInfo );
	}

}

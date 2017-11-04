package com.hgsoft.account.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.account.dao.AccountFundChangeDao;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.AccountFundChange;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.account.serviceInterface.IAccountFundChangeService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
@Service
public class AccountFundChangeService implements IAccountFundChangeService  {
	@Resource
	private AccountFundChangeDao accountFundChangeDao;
	@Resource
	private SequenceUtil sequenceUtil;
	@Resource
	private SubAccountInfoDao subAccountInfoDao;

	@Override
	public void saveAccountFundChange(Long mainAccountId,BigDecimal balance,String cardNo){
		AccountFundChange accountFundChange=new AccountFundChange();
		accountFundChange = getBeforeAccountFundChange(mainAccountId, accountFundChange);
		accountFundChange.setChangeType("46");//对应资金变动业务操作类型即type
		accountFundChange.setMemo("储值卡终止使用资金争议期后资金变动");
		accountFundChange.setCurrAvailableRefundBalance(balance);
		accountFundChange.setBeforeAvailableRefundBalance(balance);
		//新增账户资金变动流水
		accountFundChangeDao.saveChange(accountFundChange);
	}
	public void saveAccountFundChangeList(List<Map<String,Object>> list){
		BigDecimal balance = null;
		Long mainAccountInfoId = null;
		for(Map<String,Object> map:list){
			balance = (BigDecimal) map.get("BALANCE");
			mainAccountInfoId = (Long) map.get("MAINACCOUNTID");
			AccountFundChange accountFundChange=new AccountFundChange();
			accountFundChange = getBeforeAccountFundChange(mainAccountInfoId, accountFundChange);
			accountFundChange.setChangeType("46");//对应资金变动业务操作类型即type
			accountFundChange.setMemo("储值卡终止使用资金争议期后资金变动");
			accountFundChange.setCurrAvailableRefundBalance(balance);
			accountFundChange.setBeforeAvailableRefundBalance(balance);
			//新增账户资金变动流水
			accountFundChangeDao.saveChange(accountFundChange);
		}
	}
	
	private AccountFundChange getBeforeAccountFundChange(Long mainAccountInfoId,AccountFundChange accountFundChange){
		BigDecimal SEQ_CSMSAccountFundChange_NO = sequenceUtil.getSequence("SEQ_CSMSAccountFundChange_NO");
		accountFundChange.setId(Long.valueOf(SEQ_CSMSAccountFundChange_NO.toString()));
		accountFundChange.setFlowNo(getFlowNo());
		
		accountFundChange.setBeforeAvailableBalance(new BigDecimal("0"));
		accountFundChange.setBeforeFrozenBalance(new BigDecimal("0"));
		accountFundChange.setBeforepreferentialBalance(new BigDecimal("0"));
		accountFundChange.setBeforeAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setBeforeRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setMainId(mainAccountInfoId);
		
		accountFundChange.setCurrAvailableBalance(new BigDecimal("0"));
		accountFundChange.setCurrFrozenBalance(new BigDecimal("0"));
		accountFundChange.setCurrpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setCurrAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setCurrRefundApproveBalance(new BigDecimal("0"));
		
		accountFundChange.setAfterAvailableBalance(new BigDecimal("0"));
		accountFundChange.setAfterFrozenBalance(new BigDecimal("0"));
		accountFundChange.setAfterpreferentialBalance(new BigDecimal("0"));
		accountFundChange.setAfterAvailableRefundBalance(new BigDecimal("0"));
		accountFundChange.setAfterRefundApproveBalance(new BigDecimal("0"));

		return accountFundChange;
	}
	
	private String getFlowNo(){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		int randomNum1 = (int)((Math.random()*9+1)*10000000);
		int randomNum2 = (int)((Math.random()*9+1)*10000000);
		String flowNo=fomat.format(date)+randomNum1+randomNum2;
		return flowNo;
	}
	
	@Override
	public Pager list(Pager pager, Date starTime,Date endTime,
			AccountFundChange accountFundChange) {
		// TODO Auto-generated method stub
		return accountFundChangeDao.list(pager, starTime, endTime, accountFundChange);
	}

}

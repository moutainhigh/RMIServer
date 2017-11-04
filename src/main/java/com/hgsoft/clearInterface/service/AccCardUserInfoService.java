package com.hgsoft.clearInterface.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.clearInterface.dao.CardSecondIssuedDao;
import com.hgsoft.clearInterface.entity.AccCardUserInfoSend;
import com.hgsoft.clearInterface.serviceInterface.IAccCardUserInfoService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.SequenceUtil;

@Service
public class AccCardUserInfoService implements IAccCardUserInfoService {

	@Resource
	SequenceUtil sequenceUtil;
	
	@Resource
	private CardSecondIssuedDao cardSecondIssuedDao;
	
	@Override
	public void saveAccCardUserInfo(AccountCApply accountCApply, Customer customer, String remark,Integer systemType) {
		AccCardUserInfoSend accCardUserInfoSend = new AccCardUserInfoSend();
		accCardUserInfoSend.setId(sequenceUtil.getSequenceLong("SEQ_ACCCARDUSERINFO_SEND"));
		accCardUserInfoSend.setOrgan(customer.getOrgan());
		accCardUserInfoSend.setAccountType(new Integer(accountCApply.getAccountType()));
		String accountType = accountCApply.getAccountType();
		if(StringUtils.isNotBlank(accountType) && !accountType.equals("3")){
			accCardUserInfoSend.setBankNo(Long.valueOf(accountCApply.getObaNo()));
			accCardUserInfoSend.setBankName(accountCApply.getBank());//本行划扣（指某个银行，没有具体到支行；和bankSpan一定其中一个为空一个不为空）
		}else{
			accCardUserInfoSend.setBankName(accountCApply.getBankSpan());//跨行划扣（和bank一定其中一个为空一个不为空）
		}
		accCardUserInfoSend.setBankAccount(accountCApply.getBankAccount());
		
		if(StringUtils.isNotBlank(accountType) && accountType.equals("3")){
			//todo 这里开户银行不知道需不需要保存到这个清算表
//			accCardUserInfoSend.set(newAccountCApply.getBankAcceptNo()!=null?newAccountCApply.getBankAcceptNo().toString():null);
			accCardUserInfoSend.setBranchBankno(accountCApply.getBankAcceptNo()!=null?accountCApply.getBankAcceptNo().toString():null);
			accCardUserInfoSend.setReconBankNo(accountCApply.getBankClearNo()!=null?accountCApply.getBankClearNo().toString():null);
		}
		accCardUserInfoSend.setSpanBankNo(accountCApply.getObaNo()!=null?Long.valueOf(accountCApply.getObaNo()):null);
		accCardUserInfoSend.setVirType(new Integer(accountCApply.getVirType()));
		accCardUserInfoSend.setVirCount(accountCApply.getVirenum());
		accCardUserInfoSend.setMaxAcr(accountCApply.getMaxacr()!=null?accountCApply.getMaxacr():BigDecimal.ZERO);
		accCardUserInfoSend.setSystemType(systemType);// 0 普通客户   1香港快易通    2澳门通
		accCardUserInfoSend.setReqTime(new Timestamp(accountCApply.getOperTime().getTime()));
		accCardUserInfoSend.setCheckTime(new Timestamp(accountCApply.getAppTime().getTime()));
		accCardUserInfoSend.setReMark(remark);
		accCardUserInfoSend.setAccName(accountCApply.getAccName());
		accCardUserInfoSend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		Long boardListNo = new Long(String.valueOf((new Date()).getTime())+"1013");
		accCardUserInfoSend.setBoardListNo(new Long(boardListNo));
		cardSecondIssuedDao.saveAccountCUser(accCardUserInfoSend);
	}

}

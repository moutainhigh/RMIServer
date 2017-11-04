package com.hgsoft.account.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hgsoft.account.dao.SubAccountInfoDao;
import com.hgsoft.account.entity.SubAccountInfo;
import com.hgsoft.account.serviceInterface.ISubAccountInfoService;
import com.hgsoft.utils.JdbcUtil;
/**
 * 
 * @ClassName: SubAccountInfoService
 * @Description: 子账户
 * @author gaosiling
 * @date 2016年1月14日08:19:57
*/
@Service
public class SubAccountInfoService implements ISubAccountInfoService{
	
	
	@Resource
	JdbcUtil jdbcUtil;

	@Resource
	private SubAccountInfoDao subAccountInfoDao;
	
	@Override
	public SubAccountInfo findByMainIdAndType(Long mainId,String subAccountType){
		return subAccountInfoDao.findByMainIdAndType(mainId, subAccountType);
	}
	
	@Override
	public SubAccountInfo find(SubAccountInfo subAccountInfo) {
		return subAccountInfoDao.find(subAccountInfo);
	}
	
	@Override
	public SubAccountInfo findByApplyId(Long applyId){
		return subAccountInfoDao.findByApplyId(applyId);
	}

	@Override
	public SubAccountInfo findByBankAccount(String bankAccount) {
		return subAccountInfoDao.findByBankAccount(bankAccount);
	}

	@Override
	public SubAccountInfo findByCardNo(String cardno) {
		return subAccountInfoDao.findByCardNo(cardno);
	}

	@Override
	public SubAccountInfo findByCusotomerIdAndType(Long customerId, String subAccountType) {
		return subAccountInfoDao.findByCustomerIdAndType(customerId, subAccountType);
	}
}

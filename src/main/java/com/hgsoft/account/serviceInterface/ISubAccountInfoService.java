package com.hgsoft.account.serviceInterface;

import com.hgsoft.account.entity.SubAccountInfo;

public interface ISubAccountInfoService {
	
	public SubAccountInfo findByMainIdAndType(Long mainId,String subAccountType);

	public SubAccountInfo find(SubAccountInfo subAccountInfo);
	
	public SubAccountInfo findByApplyId(Long applyId);
	
	public SubAccountInfo findByBankAccount(String bankAccount);

	public SubAccountInfo findByCardNo(String cardno);

	public SubAccountInfo findByCusotomerIdAndType(Long customerId,String subAccountType);
}

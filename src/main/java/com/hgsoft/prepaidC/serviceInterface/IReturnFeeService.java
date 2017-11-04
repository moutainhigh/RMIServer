package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.prepaidC.entity.ReturnFee;

import java.math.BigDecimal;
import java.util.List;

public interface IReturnFeeService {
	
	public List<ReturnFee> findByCardNoState(String cardNo,String state);
	public List<ReturnFee> findByIDStr(String idStr);
	public List<ReturnFee> findByBussinessID(Long bussinessID);

//	List<String> getReturnMoney(List<String> list, SysAdmin sysAdmin, CusPointPoJo cusPointPoJo);

	BigDecimal findSumReturnByCardNo(String cardNo);

	int save(ReturnFee returnFee);
}

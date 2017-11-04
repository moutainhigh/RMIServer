package com.hgsoft.prepaidC.serviceInterface;


import java.math.BigDecimal;
import java.util.Date;

import com.hgsoft.prepaidC.entity.PrepaidCBussiness;
import com.hgsoft.utils.Pager;

public interface IPrepaidCBussinessService {
	public PrepaidCBussiness findByCardNoAndState(String cardNo,String state);
	public PrepaidCBussiness findByOldCardNoAndState(String oldCardNo,String state);
	public Pager findBussinessListByPager(Pager pager, String cardNo, String startTime, String endTime);
	public PrepaidCBussiness findById(Long id);
	public BigDecimal findPrepaidCBussinessId();
	public PrepaidCBussiness findBussinessNotReturn(String cardNo,String state);

	public int updateTradeStateFail(PrepaidCBussiness prepaidCBussiness);
	public int updateTradeStateSuccess(PrepaidCBussiness prepaidCBussiness);
	public PrepaidCBussiness findNewRechargeBusinessByCardNo(String cardNo);
	public PrepaidCBussiness findNewRechargeBusinessByCardNoMaxTradetime(String cardNo, Date maxTradetime);
	public int findSuccessRechargeNum(String cardNo);
	PrepaidCBussiness findRechargeBusinessByCardNoTradetime(String cardNo, Date tradetime);
}

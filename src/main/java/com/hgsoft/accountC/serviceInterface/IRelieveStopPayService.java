package com.hgsoft.accountC.serviceInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.clearInterface.entity.RelieveStopPay;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.utils.Pager;

/**
 * @FileName IRelieveStopPayService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年2月23日 下午3:56:36 
*/
public interface IRelieveStopPayService {
	
	public Integer checkStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, String stopPayFlag);
	public Pager findApplyRelieveStoppayList(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv);
	public Pager findManualRelieveStoppayList(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv);
	
	public Map<String, Object> saveApplyRelieveStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, Customer customer, AccountCBussiness accountCBussiness, Map<String,Object> params);
	public Map<String,Object> saveRelieveStopPay(PaymentCardBlacklistRecv paymentCardBlacklistRecv , Customer customer,AccountCBussiness accountCBussiness,Map<String,Object> params);
	public Map<String,Object> stopDetail(Pager pager,PaymentCardBlacklistRecv paymentCardBlacklistRecv);
	//列表页面详情按钮方法
	public Pager relieveStopDetail(Pager pager, RelieveStopPay relieveStopPay);
	
	public String getLateFeeRate(String key);
	public BigDecimal getLateFee(BigDecimal etcFee,String feeRate,Date startDate,Date endDate);
	public List<Map<String,Object>> getEtcFee(String cardno,String startTime,String endTime);
	public Map<String, Object> saveApplyRelieveStopPayByBankNoCustomer(String bankNo, Customer customer,
			AccountCBussiness accountCBussiness, AccountCApply accountCApply,Map<String, Object> params);
	public boolean checkStopBlackList(String bankAcount);//查看该银行账号是否为止付黑名单

	
	
}

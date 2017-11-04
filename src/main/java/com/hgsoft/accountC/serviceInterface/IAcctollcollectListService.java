package com.hgsoft.accountC.serviceInterface;

import java.util.Map;

import com.hgsoft.accountC.entity.AccountCApply;
import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AcctollcollectList;
import com.hgsoft.accountC.entity.HandPayment;
import com.hgsoft.clearInterface.entity.PaymentCardBlacklistRecv;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.utils.Pager;

public interface IAcctollcollectListService {
	public Pager findByPage(Pager pager,Customer customer,AccountCApply accountCApply);
	public AcctollcollectList findById(Long id);
	public Map<String,Object> savePay(PaymentCardBlacklistRecv paymentCardBlacklistRecv, Customer customer, AccountCBussiness accountCBussiness,Map<String,Object> params);
	public Map<String, Object> payDetail(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv);//缴款详情页面 兼判断是否做手工缴纳通信费业务
	public Pager listDetailPage(Pager pager, HandPayment handPayment);//列表详情页面
	public Pager findAcctollcollectList(Pager pager, PaymentCardBlacklistRecv paymentCardBlacklistRecv);
}

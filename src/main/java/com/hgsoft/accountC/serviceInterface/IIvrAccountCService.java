package com.hgsoft.accountC.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.utils.Pager;

public interface IIvrAccountCService {
	public Pager getCustomerInfo(Pager pager,String ivrValidateCustomer,String artificialValidateCustomer);
	public void updateCustomerInfo(String aaid,String cid,String zipCode,String invoicePrn,String name,String fax,String organ,String email,String linkMan,String idType,String idCode,String limitMoney,String tel,String addr);
	public Pager getAccountCMessage(Pager pager,String ivrValidateCustomer,String artificialValidateCustomer);
	public void updateAccountCMessage(String bgid,String seritem);
	public Pager getAccountInfo(Pager pager,String code,String flag);
	public void updateAccountcInfoState(String cardno);
	public AccountCInfo getAccountCInfoById(String cardNo);
	/**
	 * 根据客户号查询客户总保证金
	 * @param userNo 
	 * @return
	 */
	public String getAccountCTotalBail(String userNo);
	
	public List<Map<String, Object>>  findCustomerByCardNo(String cardno);
	
}

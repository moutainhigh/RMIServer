package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.Cancel;
import com.hgsoft.utils.Pager;

import java.util.Map;

public interface IAccountCStopCardService {
	public Pager findStopCardByCustomer(Pager pager,Customer customer);

	public String saveStopCard(ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness, Cancel cancel, Customer customer, AccountCInfo accountCInfo, String systemType, Map<String,Object> params);
	
	public String saveStopJointCard(ServiceFlowRecord serviceFlowRecord, AccountCBussiness accountCBussiness, Cancel cancel,
			Customer customer, AccountCInfo accountCInfo,String systemType);
	
	public String saveStopJointCard(Customer customer, AccountCInfo accountCInfo, AccountCBussiness accountCBussiness,
			Cancel cancel, boolean isNeedBlacklist);
	
	public Pager findStopCardByCustomer(Pager pager, Customer customer, AccountCInfo accountCInfo);
}

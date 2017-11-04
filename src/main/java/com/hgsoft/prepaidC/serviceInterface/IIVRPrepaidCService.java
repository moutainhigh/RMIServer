package com.hgsoft.prepaidC.serviceInterface;


import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.BillGet;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerHis;
import com.hgsoft.customer.entity.Invoice;

public interface IIVRPrepaidCService {
	public List<Map<String, Object>> findCustomerInfoByCardNo(String cardno);
	public List<Map<String, Object>>  findCustomerByCardNo(String cardno);
	public void updateCustomerInfo(Customer oldCustomer,
			CustomerHis customerHis, BillGet billGet, Invoice temp);
}

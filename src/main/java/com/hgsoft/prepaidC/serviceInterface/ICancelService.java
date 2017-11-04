package com.hgsoft.prepaidC.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.prepaidC.entity.Cancel;

import java.util.List;

public interface ICancelService {
	public Cancel find(Cancel cancel);
	public Cancel findById(Long id);
	public void saveCancel(Customer customer, Cancel cancel, ServiceFlowRecord serviceFlowRecord);
	public void updateCancel(Cancel cancel);
	public List<Cancel> findCancelLists(String customerId, String flag, String code, String creditCardNo, String bankCode, String source) ;
	public void deleteById(Long id);
}

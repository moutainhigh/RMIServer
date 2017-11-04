package com.hgsoft.acms.other.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.ServiceAssess;

public interface IServiceAssessServiceACMS {
	public boolean lgout(Long customerID,String assessLevel);
	public ServiceAssess findByCustomerIdAndTime(Customer customer);
	public void updateServiceAssess(ServiceAssess serviceAssess);
	public void addServiceAssess(ServiceAssess serviceAssess);
	public ServiceAssess findByCustomerIdAndBeginTime(Customer customer);
}

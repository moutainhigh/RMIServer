package com.hgsoft.other.serviceInterface;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.other.entity.ServiceAssess;

public interface IServiceAssessService {
	public boolean lgout(Long customerID,String assessLevel);
	public ServiceAssess findByCustomerIdAndTime(Customer customer);
	public void updateServiceAssess(ServiceAssess serviceAssess);
	public void addServiceAssess(ServiceAssess serviceAssess);
	public ServiceAssess findByCustomerIdAndBeginTime(Customer customer);
}

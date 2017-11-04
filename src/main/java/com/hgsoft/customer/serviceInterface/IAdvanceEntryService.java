package com.hgsoft.customer.serviceInterface;

import java.util.List;

import com.hgsoft.customer.entity.CustomerAdvance;
import com.hgsoft.customer.entity.CustomerImp;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleAdvance;



public interface IAdvanceEntryService {

	CustomerAdvance findCustomerAdvance(String idType, String idCode);

	void saveBatchCustomer(List<CustomerImp> advanceList,ServiceFlowRecord serviceFlowRecord);

	void saveBatchVehicle(List<VehicleAdvance> advanceList);

	VehicleAdvance findVehicleAdvance(String vehiclePlate,String vehicleColor);
	
}

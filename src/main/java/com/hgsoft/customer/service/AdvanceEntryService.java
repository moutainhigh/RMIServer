package com.hgsoft.customer.service;

import com.hgsoft.customer.dao.AdvanceEntryDao;
import com.hgsoft.customer.dao.CustomerImpDao;
import com.hgsoft.customer.entity.CustomerAdvance;
import com.hgsoft.customer.entity.CustomerImp;
import com.hgsoft.customer.entity.ServiceFlowRecord;
import com.hgsoft.customer.entity.VehicleAdvance;
import com.hgsoft.customer.serviceInterface.IAdvanceEntryService;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.unifiedInterface.service.PrepaidCUnifiedInterfaceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdvanceEntryService implements IAdvanceEntryService{
	
	@Resource
	private AdvanceEntryDao advanceEntryDao;
	@Resource
	private CustomerImpDao customerImpDao;
	private static Logger logger = Logger.getLogger(PrepaidCUnifiedInterfaceService.class.getName());

	@Override
	public CustomerAdvance findCustomerAdvance(String idType, String idCode) {
		return advanceEntryDao.findCustomerAdvance(idType, idCode);
	}

	@Override
	public void saveBatchCustomer(List<CustomerImp> advanceList,ServiceFlowRecord serviceFlowRecord) {
		try{
			customerImpDao.batchSaveCustomer(advanceList,serviceFlowRecord);
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"预录入客户保存失败");
			throw new ApplicationException();
		}
		
	}

	@Override
	public void saveBatchVehicle(List<VehicleAdvance> advanceList) {
		try{
			advanceEntryDao.batchSaveVehicle(advanceList);
		}catch (ApplicationException e){
			e.printStackTrace();
			logger.error(e.getMessage()+"预录入车辆保存失败");
			throw new ApplicationException();
		}
	}

	@Override
	public VehicleAdvance findVehicleAdvance(String vehiclePlate,String vehicleColor) {
		return advanceEntryDao.findVehicleAdvance(vehiclePlate,vehicleColor);
	}

}

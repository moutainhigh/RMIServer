package com.hgsoft.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.VehicleBussinessDao;
import com.hgsoft.customer.entity.VehicleBussiness;
import com.hgsoft.customer.serviceInterface.IVehicleBussinessService;
@Service
public class VehicleBussinessService implements IVehicleBussinessService{
	
	@Resource
	private VehicleBussinessDao vehicleBussinessDao;

	@Override
	public VehicleBussiness findBeforeStopVehicleBussiness(String cardNo) {
		return vehicleBussinessDao.findBeforeStopVehicleBussiness(cardNo);
	}
	
	
}

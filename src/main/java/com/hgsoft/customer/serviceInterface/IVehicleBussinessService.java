package com.hgsoft.customer.serviceInterface;

import com.hgsoft.customer.entity.VehicleBussiness;

public interface IVehicleBussinessService {
	/**
	 * 根据卡号查找上次有卡挂起或无卡挂起的车辆业务记录
	 * @param cardNo
	 * @return VehicleBussiness
	 */
	public VehicleBussiness findBeforeStopVehicleBussiness(String cardNo);
}

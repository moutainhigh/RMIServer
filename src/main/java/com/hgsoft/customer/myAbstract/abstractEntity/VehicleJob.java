package com.hgsoft.customer.myAbstract.abstractEntity;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.CustomerBussiness;
import com.hgsoft.customer.entity.VehicleBussiness;

/**
 * 车辆业务抽象类
 * @author Administrator
 * @date 2017年8月30日
 */
public abstract class VehicleJob {
	public abstract void saveServiceWater(Customer customer,VehicleBussiness vehicleBussiness,CustomerBussiness customerBussiness);
	public abstract void saveVehicleBussiness(VehicleBussiness vehicleBussiness);
}

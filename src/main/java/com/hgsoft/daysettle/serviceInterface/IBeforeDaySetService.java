package com.hgsoft.daysettle.serviceInterface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.daysettle.entity.BeforeDaySet;
import com.hgsoft.utils.Pager;

public interface IBeforeDaySetService {

	public Pager findByPager(Pager pager, Date starTime, Date endTime,
			Customer customer, VehicleInfo vehicleInfo, String type,
			String settleDay,Long placeId,Long id,List<String> pointList);

	public boolean update(BeforeDaySet beforeDaySet, BigDecimal price);

}

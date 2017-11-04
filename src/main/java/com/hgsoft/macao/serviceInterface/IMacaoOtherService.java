package com.hgsoft.macao.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleBussiness;

public interface IMacaoOtherService {
	public void saveStart(Customer customer,AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo, VehicleBussiness vehicleBussiness);
	public void saveStop(String flag,Customer customer,AccountCInfo accountCInfo, CarObuCardInfo carObuCardInfo,VehicleBussiness vehicleBussiness);
	public List<Map<String, Object>> listNotBind(String cardNo);
}

package com.hgsoft.system.serviceInterface;

import java.math.BigDecimal;
import java.util.List;

import com.hgsoft.system.entity.ServiceFundMonitor;
import com.hgsoft.system.entity.ServiceFundMonitorHis;

public interface IServiceFundMonitorService {

	public List<ServiceFundMonitor> findAll();
	
	public ServiceFundMonitor findByCustomPoint(Long id);

	public ServiceFundMonitor findFundMonitorRechargeByCustomPoint(Long customPointId);

	public void saveFundMonitor(ServiceFundMonitor serviceFundMonitor);

	public ServiceFundMonitor findByFundId(Long id);

	public void deleteFundMonitor(Long id, ServiceFundMonitorHis serviceFundMonitorHis);

	public void updateFundMonitor(ServiceFundMonitor oldServiceFundMonitor,ServiceFundMonitorHis serviceFundMonitorHis);

	public int updateFundMonitorRechargeByCustomPoint(Long customPointId, BigDecimal diffMoney);
}

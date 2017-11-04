package com.hgsoft.httpInterface.serviceInterface;

import java.util.Map;

import com.hgsoft.utils.Pager;

public interface IVehicleModifyApplyService {
	
	public Pager list(Pager pager, String organ,String vehiclePlate, String vehicleColor, 
			String appState,String approverName,String appTime,String createTime);
	
	public boolean approval(Long id, String appState, Long approverId,String approverNo, String approverName, String appTime);
	public Map<String,Object> detailList(Long id,String rootPath);

}

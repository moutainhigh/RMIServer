package com.hgsoft.httpInterface.serviceInterface;

import java.util.Map;

import com.hgsoft.utils.Pager;

public interface ITagMigrateInterfaceService {
	public Pager getVehicleList(Pager pager,String tagNo,String vehiclePlate,String vehicleColor,String authState);
	public Map<String,Object> getVehicleInfo(String viid);
	public String updateVehicleState(String trid,String authDate,String authId,String authNo,String authName,String authState);
}
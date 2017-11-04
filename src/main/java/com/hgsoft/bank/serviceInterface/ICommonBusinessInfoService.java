package com.hgsoft.bank.serviceInterface;

import com.hgsoft.bank.entity.BankCardLock;
import com.hgsoft.bank.entity.DesKey;

import java.util.Map;

public interface ICommonBusinessInfoService{

	void vehicleInfoLock(BankCardLock bankCardLock);

	void vehicleInfoUnlock(BankCardLock bankCardLock);

	public Map<String,Object> getLicenseInfo(String vehiclePlate, String vehicleColor);
	
	public Map<String,Object> getVehicleInfo(String cardNo,String cardType);
	
	public Map<String,Object> getCardStateInfo(String cardNo,String cardType);
	
	public Map<String,Object> getflagandTollcarInfo(String date,String vehiclePlate,String vehicleColor,String VehicleType,String vehicleWeightLimits);

	DesKey findDesKey(String terminalNo, String bankNo);

	void updateDesKey(DesKey desKey);
}

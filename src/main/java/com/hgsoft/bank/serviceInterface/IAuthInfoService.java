package com.hgsoft.bank.serviceInterface;

import com.hgsoft.bank.entity.AuthInfo;
import com.hgsoft.system.entity.CusPointPoJo;

import java.util.Map;

public interface IAuthInfoService{

	public Map<String, String> save(AuthInfo authInfo);

	public Map<String, String> delete(AuthInfo authInfo);

	public AuthInfo findByAuth(AuthInfo authInfo);

//	public Map<String,Object> getLicenseInfo(String vehiclePlate,String vehicleColor);

	void updatePassword(AuthInfo authInfo);

	CusPointPoJo findCusPointPoJoByTerminalNo(String terminalNo);

	void updateLastLoginTime(Long id);

}

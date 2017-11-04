package com.hgsoft.associateAcount.serviceInterface;

import java.util.Map;

import com.hgsoft.associateAcount.entity.ReqInfo;
import com.hgsoft.associateReport.entity.ServiceApp;

public interface ILogOutService {
	public Map<String,Object> saveLogOut(ReqInfo reqInfo, ServiceApp serviceApp,String cardType);
	public Map<String,Object> saveBlackList(ReqInfo reqInfo, ServiceApp serviceApp,String cardType);
}

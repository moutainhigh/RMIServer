package com.hgsoft.associateAcount.serviceInterface;

import java.util.Map;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.associateReport.entity.ServiceApp;

public interface ILostService {
	public Map<String,Object> saveLost(AccountCInfo accountCInfo, ServiceApp serviceApp,String cardType);
	public boolean saveLost(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness);
}

package com.hgsoft.associateAcount.serviceInterface;

import java.util.Map;

import com.hgsoft.accountC.entity.AccountCBussiness;
import com.hgsoft.accountC.entity.AccountCInfo;
import com.hgsoft.associateReport.entity.ServiceApp;

public interface IUnLostService {
	public Map<String, Object> saveUnLost(AccountCInfo accountCInfo,ServiceApp serviceApp);
	public boolean saveUnLost(AccountCInfo accountCInfo, AccountCBussiness accountCBussiness);
}

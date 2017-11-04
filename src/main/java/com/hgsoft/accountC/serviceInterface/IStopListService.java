package com.hgsoft.accountC.serviceInterface;

import com.hgsoft.accountC.entity.StopList;
import com.hgsoft.clearInterface.entity.StopAcList;

import java.util.Map;

public interface IStopListService {
	public StopAcList find(StopAcList stopAcList);
	public Map<String,Object> findByBankAccount(String bankAccount);
}

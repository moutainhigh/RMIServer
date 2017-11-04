package com.hgsoft.httpInterface.serviceInterface;

import java.math.BigDecimal;
import java.util.Map;

import com.hgsoft.httpInterface.entity.InterfaceRecord;


public interface IInventoryServiceForAgent {
	public Map<String, Object> omsInterface(String cardno, String type, InterfaceRecord interfaceRecord,String outType,Long placeID,Long operID,String operName,String cardType,String flagStr,Long productInfo,BigDecimal price,String serviceType);
	
	public Map<String, Object> initializedOrNot(String cardType,String productCode,String au_token);

}

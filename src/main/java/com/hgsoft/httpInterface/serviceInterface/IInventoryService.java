package com.hgsoft.httpInterface.serviceInterface;

import java.math.BigDecimal;
import java.util.Map;

import com.hgsoft.httpInterface.entity.InterfaceRecord;


public interface IInventoryService {
	public String issue(String productCode, String customPoint, String createId, String createName, String operTime, String type,String cardType,String flagStr,String productInfo,String au_token);
	public String reclaim(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token,String newCardSourceType);
	public String refund(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token);
	public String issueReplacement(String productCode, String customPoint, String createId, String createName, String operTime, String type,String cardType,String flagStr,String productInfo,String au_token);
	public String reclaimReplacement(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token);
	public String refundReplacement(String productCode, String customPoint, String createId, String createName, String operTime,String cardType,String au_token);
	
	public Map<String, Object> omsInterface(String cardno, String type, InterfaceRecord interfaceRecord,String outType,Long placeID,Long operID,String operName,String cardType,String flagStr,Long productInfo,BigDecimal price,String serviceType,String newCardSourceType);
	public String pickup(String startCode,String endCode, String customPoint, String createId, String createName, String operTime,String flagStr,String productInfo,String au_token);
	public String refundPickup(String startCode,String endCode, String customPoint, String createId, String createName, String operTime,String flagStr,String productInfo,String au_token);
	public Map<String, Object> tagInterface(InterfaceRecord interfaceRecord,String startCode,String endCode,Long placeID,Long operID,String operName,String flagStr,Long productInfo,String type,BigDecimal price,String serviceType);

	public Map<String, Object> getProductInfo();
	public Map<String, Object> getProductTypeByCode(String cardType,String productCode);

}

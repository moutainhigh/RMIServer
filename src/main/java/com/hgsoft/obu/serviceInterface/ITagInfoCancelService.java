package com.hgsoft.obu.serviceInterface;

import java.util.Map;

import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.utils.Pager;

public interface ITagInfoCancelService {
	
	
	
	public Pager tagCancelList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long id);
	public Pager tagCancelListForAMMS(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long id,String bankCode);
	
	public Map<String, Object> tagCancelDetail(Long tagInfoId);
	
	public void saveCancelTagInfo(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord);
	public void saveCancelTagInfo(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag,Map<String,Object> params);
	public void saveCancelTagInfoForAMMS(Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag,Map<String,Object> params);
	public CarObuCardInfo getCarObuCardInfo(Long vehicleId);
	public boolean writeBackImportCard(String tagNo);
}

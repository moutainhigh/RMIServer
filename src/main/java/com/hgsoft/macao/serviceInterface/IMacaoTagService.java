package com.hgsoft.macao.serviceInterface;

import java.math.BigDecimal;
import java.util.Map;

import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.utils.Pager;

public interface IMacaoTagService {
	public Map<String,Object> saveReplaceTagInfo(Customer customer,Map<String, Object> tagReplace,BigDecimal chargeFee, String newTagNo,
			Long tagInfoId, Long clientID, Long vehicleID,
			String replaceReason, Long installmanID, String memo, Long operID, 
			Long operplaceID,TagBusinessRecord tagBusinessRecord,Long productInfoId,
			BigDecimal realCost,String installmanName,String productTypeCode);
	
	public void saveCancelTagInfo(Customer customer,Long tagInfoId, Long clientID, Long vehicleID, Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String hasTag);
	public Map<String,Object> saveTagInfoMigrate(Customer customer,TagInfo tagInfo, Long clientID, Long vehicleID, 
			Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String replaceType,String recoverReason);
	public void saveTagStopTagInfo(String flag,Customer customer,Long tagInfoId, String tagNo,
			Long clientID,Long vehicleID,Long installmanID,
			String memo,Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord);
	public CarObuCardInfo getCarObuCardInfo(String vehiclePlate);
	
	
	public Pager tagCancelList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long id,MacaoCardCustomer macaoCardCustomer);
}

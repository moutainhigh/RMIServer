package com.hgsoft.acms.obu.serviceInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagTakeDetail;
import com.hgsoft.utils.Pager;

public interface ITagReplaceServiceACMS {
	public List tagReplaceList(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Pager tagReplaceListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Map<String, Object> tagReplaceDetail(Long tagInfoId);
	public Map<String, Object> saveReplaceTagInfo(BigDecimal chargeFee,String newTagNo,Long tagInfoId,
			Long clientID,Long vehicleID,String faultTypeReason, Long installmanID,
			String memo, Long operID, Long operplaceID,TagBusinessRecord tagBusinessRecord,
			Long productInfoId,BigDecimal realCost,String installmanName,String productTypeCode,String faultType);
	public Map<String, Object> saveReplaceTagInfo_Lian(BigDecimal chargeFee,String newTagNo,Long tagInfoId,
			Long clientID,Long vehicleID,String faultTypeReason, Long installmanID,
			String memo, Long operID, Long operplaceID,TagBusinessRecord tagBusinessRecord,
			Long productInfoId,BigDecimal realCost,String installmanName,String productTypeCode,String faultType);
	
	
	public Map<String, Object> saveReplaceTagInfoForAMMS(BigDecimal chargeFee,String newTagNo,Long tagInfoId,
			Long clientID,Long vehicleID,String recoverReason, Long installmanID,
			String memo, Long operID, Long operplaceID,TagBusinessRecord tagBusinessRecord,
			Long productInfoId,BigDecimal realCost,String installmanName,String productTypeCode,String faultType);
	public boolean checkBalance(Long mainId, BigDecimal chargeFee);
	public CarObuCardInfo findIsReplaceTag(Long newTagInfoId, Long vehicleID);
	public TagInfo findTagInfoByTagNo(String tagNo);
	public TagTakeDetail findTagTakeDetailByTagNo(String tagNo);
	public Pager tagReplaceListForLian(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,Customer sessionCustomer,Customer listCustomer);
	
	public Map<String, Object> saveReplaceForLian(BigDecimal chargeFee,
			String newTagNo, Long tagInfoId, Long clientID, Long vehicleID,
			String replaceReason, Long installmanID, String memo, Long id,
			Long cusPoint, TagBusinessRecord tagBusinessRecord,
			Long productInfoId,BigDecimal realCost,String installmanName,String productTypeCode);
	public void saveClear(Customer customer,String tagNo,String newTagNo,Long vehicleID);
	
	//澳门通用
	public Pager tagReplaceListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer);
}

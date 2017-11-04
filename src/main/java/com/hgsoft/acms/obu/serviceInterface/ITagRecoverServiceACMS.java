package com.hgsoft.acms.obu.serviceInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.system.entity.OMSParam;
import com.hgsoft.utils.Pager;

public interface ITagRecoverServiceACMS {
	public List tagRecoverList(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Pager tagRecoverListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Pager tagRecoverListByPagerForAMMS(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,String bankCode);
	public Map<String, Object> tagRecoverDetail(Long tagInfoId);
	public void saveRecoverTagInfo(BigDecimal chargeCost,Long tagInfoId, String tagNo,
			Long clientID,Long vehicleID,String recoverReason, Long installmanID,
			String memo, Long operID, Long operplaceID,TagBusinessRecord tagBusinessRecord,String replaceType);
	public void saveRecoverTagInfo(Customer customer,BigDecimal chargeCost,Long tagInfoId, String tagNo,
			Long clientID,Long vehicleID,String recoverReason, Long installmanID,
			String memo, Long operID, Long operplaceID,TagBusinessRecord tagBusinessRecord,String replaceType);
	public Pager tagRecoverListForLian(Pager pager,TagInfo tagInfo,VehicleInfo vehicleInfo,Customer sessionCustomer,Customer listCustomer);
	
	//澳门通用
	public Pager tagRecoverListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer);
	public List<OMSParam> findFirstLevel(String omsType,String type);
	public List<OMSParam> findSecondLevel(String replaceType);
	public void updateWriteBackFlag(Long id);
	public Map<String, Object> findDetailForMacao(Long tagInfoId);
}

package com.hgsoft.obu.serviceInterface;

import java.util.List;
import java.util.Map;

import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.utils.Pager;

public interface ITagStopService {
	public List tagStopList(String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Pager tagStopListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID);
	public Pager tagStopListByPagerForAMMS(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,String bankCode);
	public Map<String, Object> tagStopDetail(Long tagInfoId);
	public void saveTtopTagInfo(Long tagInfoId, String tagNo,
			Long clientID,Long vehicleID,Long installmanID,
			String memo,Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord);
	/**
	 * 区分有标签和无标签挂起
	 * @param flag
	 * @param tagInfoId
	 * @param tagNo
	 * @param clientID
	 * @param vehicleID
	 * @param installmanID
	 * @param memo
	 * @param operID
	 * @param operplaceID
	 * @param tagBusinessRecord
	 * @return void
	 */
	public void saveTagStopInfo(String flag,Long tagInfoId, String tagNo,Long clientID,Long vehicleID,Long installmanID,String memo,Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord,Map<String,Object> params);
	public void saveTagStopInfoForAMMS(String flag,Long tagInfoId, String tagNo,Long clientID,Long vehicleID,Long installmanID,String memo,Long operID,Long operplaceID,TagBusinessRecord tagBusinessRecord,Map<String,Object> params);
	public Pager tagStopListForLian(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer sessionCustomer,
			Customer listCustomer);
	public CarObuCardInfo findByVehicleID(Long vehicleID);
	//澳门通用
	public Pager tagStopListByPager(Pager pager,String tagNo, String vehicleColor,
			String vehiclePlate, String idType, String idCode, String endSixNo,Long customerID,MacaoCardCustomer macaoCardCustomer);
	public CarObuCardInfo getCarObuCardInfo(Long vehicleId);
}

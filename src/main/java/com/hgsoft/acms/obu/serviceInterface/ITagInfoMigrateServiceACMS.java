package com.hgsoft.acms.obu.serviceInterface;

import java.util.Map;

import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.macao.entity.MacaoCardCustomer;
import com.hgsoft.obu.entity.TagBusinessRecord;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.utils.Pager;

public interface ITagInfoMigrateServiceACMS {
	
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer, String identificationCode6);
	public Pager tagInfoListForAMMS(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer, String identificationCode6,String bankCode);
	
	public Map<String, Object> tagInfoDetail(TagInfo tagInfo, Long clientID, Long vehicleID);
	
	public Map<String,Object> saveTagInfoMigrate(TagInfo tagInfo, Long clientID, Long vehicleID, 
			Long operatorId, Long operPlaceId,TagBusinessRecord tagBusinessRecord,String replaceType,String recoverReason);

	public Pager findVehicle(Customer customer,Pager pager);

	public boolean checkHasMigrateRequestRecord(TagInfo tagInfo);
	
	public Pager tagInfoMigrateAuthList(Pager pager, TagInfo tagInfo,
			VehicleInfo vehicleInfo, Customer customer,
			String identificationCode6);
	public Map<String, Object> saveTransfer(Customer customer,
			Customer newCustomer, TagInfo tagInfo);
	public Map<String, Object> saveTransfer(Customer customer,
			Customer newCustomer, TagInfo tagInfo,TagBusinessRecord tagBusinessRecord);
	public void saveClear(Customer customer,TagInfo tagInfo,VehicleInfo vehicleInfo);
	public boolean checkAccountC(String vehiclePlate,String vehicleColor);
	public MacaoCardCustomer getMacaoCardCustomer(VehicleInfo vehicleInfo);
	//澳门通用
	public Pager tagInfoList(Pager pager, TagInfo tagInfo, VehicleInfo vehicleInfo, Customer customer, String identificationCode6,MacaoCardCustomer macaoCustomer);
	public CarObuCardInfo getCarObuCardInfo(Long tagInfoId);
	public VehicleInfo getVehicleInfo(Long id);
	public boolean checkVehicle(String vehiclePlate);
	public boolean isStopTag(Long id);
}

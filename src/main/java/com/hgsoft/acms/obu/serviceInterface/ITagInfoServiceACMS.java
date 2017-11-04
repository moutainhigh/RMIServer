package com.hgsoft.acms.obu.serviceInterface;

import java.util.Date;
import java.util.Map;

import com.hgsoft.associateAcount.entity.DarkList;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.system.entity.CusPointPoJo;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.utils.Pager;

public interface ITagInfoServiceACMS {

	public Pager obuRecordTagInfoList(Pager pager,Date starTime ,Date endTime, TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo);
	public Pager obuRecordTagInfoListForAMMS(Pager pager,Date starTime ,Date endTime, TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,String bankCode);
	public TagInfo findById(Long id);
	public TagInfo findByTagNo(String tagNo);
	public Map<String, Object> verifyTagInfo(String tagNo);
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,Long customerId);
	public TagInfo findByVehicleInfoId(Long id);
	public Map<String,Object> save(TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,Long productInfoId);
	public Map<String,Object> saveForAMMS(TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,Long productInfoId);
	public String delete(TagInfo tagInfo,Customer customer);
	public TagInfo findByCutomerId(Long customerId);
	public void saveSetBind(String state,Long vehicleInfoId);
	public Map<String,Object> saveLianCard(TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo,Long productInfoId); 
	public boolean updateTagWriteBackFlag(Customer customer,Long id, SysAdmin sysadmin,
			CusPointPoJo cusPointPoJo);
	public boolean updateTagWriteBackFlag(Long id, SysAdmin sysadmin,
			CusPointPoJo cusPointPoJo);
	public String deleteLianCard(TagInfo tagInfo, Customer customer);
	public Map<String, Object> verifyTagInfoLianCard(String tagNo);
	public void saveDarkList(TagInfo tagInfo,DarkList darkList,String genCau,String state);
	public Map<String, Object> updateIsWriteObu(Long id);
	public boolean checkTagBusinessRecord(String tagNo);
	String delete_ACMS(TagInfo tagInfo, Customer customer);
	public String checkForDelete(TagInfo tagInfo);
}


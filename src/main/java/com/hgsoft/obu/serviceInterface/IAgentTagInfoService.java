package com.hgsoft.obu.serviceInterface;

import java.util.Date;
import java.util.Map;

import com.hgsoft.customer.entity.Customer;
import com.hgsoft.customer.entity.VehicleInfo;
import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.utils.Pager;

public interface IAgentTagInfoService {

	public Pager obuRecordTagInfoList(Pager pager,Date starTime ,Date endTime, TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo);
	public TagInfo findById(Long id);
	public TagInfo findByTagNo(String tagNo);
	public Map<String, Object> verifyTagInfo(String tagNo);
	public VehicleInfo findByVehicleInfo(VehicleInfo vehicleInfo,Long customerId);
	public TagInfo findByVehicleInfoId(Long id);
	public Map<String,Object> save(TagInfo tagInfo,Customer customer,VehicleInfo vehicleInfo);
	public String delete(TagInfo tagInfo,Customer customer);
	public TagInfo findByCutomerId(Long customerId);
	public void saveSetBind(String state,Long vehicleInfoId);
}

package com.hgsoft.acms.obu.serviceInterface;

import java.util.Map;

import com.hgsoft.obu.entity.TagInfo;
import com.hgsoft.obu.entity.TagMainRecord;
import com.hgsoft.utils.Pager;

public interface ITagMaintainServiceACMS {

	public Pager tagMaintainList(Pager pager, String tagNo, String vehicleColor, String vehiclePlate,
			String idType, String idCode, String endSixNo, Long customerId);
	
	public Map<String, Object> findtagDetail(Long customerId, Long vehicleId, Long tagInfoId, Long tagMaintainId);
	
	public Map<String, Object> findRegisterNeededInfo(Long tagInfoId);
	
	
	public void updateMaintainInfo(TagMainRecord tagMainRecord);
	
	public String editReturnCustomer(TagMainRecord updateRecord);
	public String save(TagMainRecord tagMainRecord,TagInfo backTagInfo,Long productInfoId);
	public Map<String,Object> validateBackTagNo(String backupTagNo);
	public TagMainRecord findById(Long id);
}


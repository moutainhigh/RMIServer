package com.hgsoft.system.service;

import com.hgsoft.system.dao.BusinessAccreditDao;
import com.hgsoft.system.serviceInterface.IBusinessAccreditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class BusinessAccreditService implements IBusinessAccreditService{
	@Resource
	private BusinessAccreditDao businessAccreditDao;
	
	@Override
	public List<Map<String,Object>> findBusinessAccreditByURL(String url){
		return businessAccreditDao.findBusinessAccreditByURL(url);
	}
	
	@Override
	public List<Map<String,Object>> findBusinessAccreditByURL(String url, String subuumstemcode){
		return businessAccreditDao.findBusinessAccreditByURL(url, subuumstemcode);
	}
	/**
	 * 查找csms_businessaccredit_admin中是否有对应主管
	 */
	@Override
	public List<Map<String,Object>> findBusinessAccreditAdminByStaffNo(String staffno,String state,String url, String subuumstemcode){
		return businessAccreditDao.BusinessAccreditAdminByName(staffno,state, url, subuumstemcode);
	}
}

package com.hgsoft.system.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.SysAdminDao;
import com.hgsoft.system.entity.SysAdmin;
import com.hgsoft.system.serviceInterface.ISysAdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAdminService implements ISysAdminService{
	@Resource
	private SysAdminDao sysAdminDao;
	
	private static Logger logger = Logger.getLogger(SysAdminService.class.getName());
	
	
	
	
	/**
	 * 找所有用户
	 */
	@Override
	public List<Map<String, Object>> findSysAdminList() {
		return sysAdminDao.findSysAdmins();
	}



	/**
	 * 操作员的删除及批量插入（废弃了，现在已经不需要同步了 by关少锋 20171015）
	 */
	@Override
	public Map<String, String> saveOperateSysAdmin(List<SysAdmin> sysAdminList) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			//1.删除所有 该系统编码的操作员
			if(sysAdminList.size()>0)sysAdminDao.deleteBySubuumstemCode(sysAdminList.get(0).getSubuumstemCode());
			//2.同步所有操作员数据
			sysAdminDao.batchSaveSysAdmin(sysAdminList);
			
			resultMap.put("result","true");
		} catch (ApplicationException e) {
			logger.error("同步操作员数据失败");
			e.printStackTrace();
			throw new ApplicationException("同步操作员数据失败");
		} 
		
		return resultMap;
	}
	
	/**
	 * 
	 */
	@Override
	public SysAdmin findSysAdminById(Long id) {
		return sysAdminDao.findSysAdminById(id);
	}
	
	@Override
	public SysAdmin findSysAdminById(String staffno,String subuumstemCode) {
		return sysAdminDao.findByStaffno(staffno,subuumstemCode);
	}
	@Override
	public SysAdmin findAdminBySSU(String staffno,String subuumstemCode,String url) {
		return sysAdminDao.findAdminBySSU(staffno,subuumstemCode,url);
	}

	/**
	 * 根据用户名查找操作员
	 */
	@Override
	public SysAdmin findSysAdminByUserName(String userName) {
		return sysAdminDao.findByUserName(userName);
	}



	@Override
	public List<SysAdmin> findSysAdminsByCustomPoint(Long CustomPoint) {
		
		return sysAdminDao.findSysAdminsByCustomPoint(CustomPoint);
	}


	/**
	 * 根据层级和系统编码来查找操作员列表，否则会有重复数据
	 */
	@Override
	public List<SysAdmin> findSysAdminsByCustomPointAndCode(Long CustomPoint, String subuumstemCode) {
		return sysAdminDao.findSysAdminsByCustomPointAndCode(CustomPoint, subuumstemCode);
	}
}

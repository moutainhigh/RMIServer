package com.hgsoft.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.CusPointTreeEndDao;
import com.hgsoft.system.entity.CusPointTreeEnd;
import com.hgsoft.system.serviceInterface.ICusPointTreeEndService;

@Service
public class CusPointTreeEndService implements ICusPointTreeEndService{
	@Resource
	private CusPointTreeEndDao cusPointTreeEndDao;
	
	private static Logger logger = Logger.getLogger(CusPointTreeEndService.class.getName());
	
	@Override
	public List<Map<String, Object>> findCusPointTreeEndList() {
		return cusPointTreeEndDao.findCusPointTreeEnds();
	}

	@Override
	public List<Map<String, Object>> findCusPointTreeByPointType(String type,String area) {
		return cusPointTreeEndDao.findCusPointTreeByPointType(type,area);
	}
	@Override
	public List<Map<String, Object>> findCusPointAreaByPointCode(String str) {
		return cusPointTreeEndDao.findCusPointAreaByPointCode(str);
	}
	
	@Override
	public Map<String, Object> findCusPointTreeEndList(String id) {
		return cusPointTreeEndDao.findCusPointTreeEnds(id);
	}

//	@Override
//	public List<Map<String, Object>> findCusPointTreeByPointType(String type,String area) {
//		return cusPointTreeEndDao.findCusPointTreeByPointType(type,area);
//	}
//	@Override
//	public List<Map<String, Object>> findCusPointAreaByPointCode(String str) {
//		return cusPointTreeEndDao.findCusPointAreaByPointCode(str);
//	}


	/**
	 * 网点的数据同步
	 */
	@Override
	public Map<String, String> saveCusPointTreeEndList(List<CusPointTreeEnd> cusPointTreeEnds) {
		Map<String, String> resultMap = new HashMap<String,String>();
		try {
			//1.删除所有网点数据
			cusPointTreeEndDao.deleteAll();
			//2.同步所有网点数据
			cusPointTreeEndDao.batchSaveCusPointTreeEnd(cusPointTreeEnds);
			
			resultMap.put("result","true");
		} catch (ApplicationException e) {
			logger.error("同步网点数据失败");
			e.printStackTrace();
			throw new ApplicationException("同步网点数据失败");
		} 
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> findCusPointTreeByIdOne() {
		return cusPointTreeEndDao.findCusPointTreeByIdOne();
	}

	@Override
	public List<Map<String, Object>> pointTypeChangeByIdOne(String type,String area) {
		return cusPointTreeEndDao.pointTypeChangeByIdOne(type,area);
	}
	
}

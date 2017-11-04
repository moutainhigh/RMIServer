package com.hgsoft.system.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.InterfaceControlDao;
import com.hgsoft.system.entity.InterfaceControl;
import com.hgsoft.system.serviceInterface.IInterfaceControlService;
@Service
public class InterfaceControlService implements IInterfaceControlService{

	private static Logger logger = Logger.getLogger(InterfaceControlService.class.getName());
	
	
	@Resource
	private InterfaceControlDao interfaceControlDao;
	
	/**
	 * 根据    授权系统编码 查找对象
	 */
	@Override
	public InterfaceControl findByCode(String code) {
		return interfaceControlDao.findByCode(code);
	}

	@Override
	public Map<String, String> update(InterfaceControl interfaceControl) {
		Map<String, String> resultMap;
		try {
			resultMap = new HashMap<String, String>();
			
			interfaceControlDao.update(interfaceControl);
			
			resultMap.put("result", "true");
			resultMap.put("message", "修改成功");
		} catch (ApplicationException e) {
			logger.error("修改系统接口授权状态失败");
			e.printStackTrace();
			throw new ApplicationException();
		}
		return resultMap;
	}

}

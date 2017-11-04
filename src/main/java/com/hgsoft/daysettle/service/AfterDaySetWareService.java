package com.hgsoft.daysettle.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.daysettle.dao.AfterDaySetWareDao;
import com.hgsoft.daysettle.entity.AfterDaySetWare;
import com.hgsoft.daysettle.serviceInterface.IAfterDaySetWareService;
import com.hgsoft.exception.ApplicationException;

/**
 * 库存上报日结后差异修正
 * @author gaosiling
 * 2016年1月30日10:36:483
 */
@Service
public class AfterDaySetWareService implements IAfterDaySetWareService{
	
	@Resource
	private AfterDaySetWareDao afterDaySetWareDao;
	
	private static Logger logger = Logger.getLogger(AfterDaySetWareService.class.getName());
	
	/**
	 * 库存上报差异修正
	 * @param list
	 * @param reportOperID
	 * @param reportPlaceID
	 */
	@Override
	public void batchSaveAfterDaySetWare(List<AfterDaySetWare> list,Long reportOperID,Long reportPlaceID){
		try {
			afterDaySetWareDao.batchSaveAfterDaySetWare(list, reportOperID, reportPlaceID);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"保存库存上报差异修正记录失败");
			e.printStackTrace();
			throw new ApplicationException("保存库存上报差异修正记录失败");
		}
	}

}

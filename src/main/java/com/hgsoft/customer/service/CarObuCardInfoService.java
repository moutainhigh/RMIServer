package com.hgsoft.customer.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.customer.dao.CarObuCardInfoDao;
import com.hgsoft.customer.entity.CarObuCardInfo;
import com.hgsoft.customer.serviceInterface.ICarObuCardInfoService;
import com.hgsoft.obu.service.OBUActRecordService;

@Service
public class CarObuCardInfoService implements ICarObuCardInfoService{
	
	private static Logger logger = Logger.getLogger(OBUActRecordService.class.getName());
	
	@Resource
	private CarObuCardInfoDao carObuCardInfoDao;

	@Override
	public CarObuCardInfo findByTagid(Long id) {
		return carObuCardInfoDao.findByTagid(id);
	}
	@Override
	public CarObuCardInfo findByTagNo(String tagNo) {
		return carObuCardInfoDao.findByTagNo(tagNo);
	}
	
	@Override
	public CarObuCardInfo findByPrepaidCID(Long id) {
		return carObuCardInfoDao.findByPrepaidCID(id);
	}
	
	@Override
	public CarObuCardInfo findByVehicleID(Long id) {
		return carObuCardInfoDao.findByVehicleID(id);
	}

	@Override
	public CarObuCardInfo findByAccountCID(Long id) {
		
		return carObuCardInfoDao.findByAccountCID(id);
	}
	/**
	 * @Descriptioqn:
	 * @param cardNo
	 * @return
	 * @author lgm
	 * @date 2017年7月18日
	 */
	@Override
	public CarObuCardInfo findByPrepaidCNo(String cardNo) {
		return carObuCardInfoDao.findByPrepaidCNo(cardNo);
	}
}

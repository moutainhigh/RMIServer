package com.hgsoft.macao.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.macao.dao.CarUserCardInfoDao;
import com.hgsoft.macao.entity.CarUserCardInfo;
import com.hgsoft.macao.serviceInterface.ICarUserCardInfoService;
/**
 * 澳门通车辆、用户、卡片关联表
 */
@Service
public class CarUserCardInfoService implements ICarUserCardInfoService{
	@Resource
	private CarUserCardInfoDao cardUserCardInfoDao;
	@Override
	public CarUserCardInfo findByAccountCId(Long accountCId) {
		
		return cardUserCardInfoDao.findByAccountCId(accountCId);
	}
	
}

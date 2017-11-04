package com.hgsoft.clearInterface.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.HandlePayTollDao;
import com.hgsoft.clearInterface.entity.HandlePayToll;
import com.hgsoft.clearInterface.serviceInterface.IHandlePayTollService;
import com.hgsoft.common.dao.EtcTollingBaseDao;

@Service
public class HandlePayTollService extends EtcTollingBaseDao implements IHandlePayTollService{

	@Resource
	private HandlePayTollDao handlePayTollDao;
	@Override
	public Boolean saveHandlePayToll(HandlePayToll handlePayToll) {
		try {
			handlePayTollDao.saveHandlePayToll(handlePayToll);
			return true;
		} catch (Exception e) {
			return false;
		}
	}



}

package com.hgsoft.accountC.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.StopListDao;
import com.hgsoft.accountC.entity.StopList;
import com.hgsoft.accountC.serviceInterface.IStopListService;
import com.hgsoft.clearInterface.dao.StopAcListDao;
import com.hgsoft.clearInterface.entity.StopAcList;

import java.util.Map;

@Service
public class StopListService implements IStopListService{
	private static Logger logger = Logger.getLogger(StopListService.class
			.getName());
	@Resource
	private StopAcListDao stopAcListDao;

	@Override
	public StopAcList find(StopAcList stopAcList){
		return stopAcListDao.find(stopAcList);
	}

	@Override
	public Map<String,Object> findByBankAccount(String bankAccount){
		return stopAcListDao.findByBankAccount(bankAccount);
	}

}

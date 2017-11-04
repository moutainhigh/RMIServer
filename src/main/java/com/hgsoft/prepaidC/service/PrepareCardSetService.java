package com.hgsoft.prepaidC.service;

import com.hgsoft.prepaidC.dao.PrepareCardSetDao;
import com.hgsoft.prepaidC.entity.PrepareCardSet;
import com.hgsoft.prepaidC.serviceInterface.IPrepareCardSetService;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class PrepareCardSetService implements IPrepareCardSetService {
	@Resource
	private PrepareCardSetDao prepareCardSetDao;
	private static Logger logger = Logger.getLogger(PrepareCardSetService.class.getName());	
	@Resource
	SequenceUtil sequenceUtil;


	@Override
	public PrepareCardSet findByCardNo(String cardNo) {
		return prepareCardSetDao.findByCardNo(cardNo);
	}
}

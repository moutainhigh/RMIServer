package com.hgsoft.ygz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.ygz.dao.RealBusinessReqDao;
import com.hgsoft.ygz.entity.RealBusinessReq;
import com.hgsoft.ygz.service.RealBusinessReqService;

@Service
public class RealBusinessReqServiceImpl implements RealBusinessReqService{

	@Resource
	private  RealBusinessReqDao realBusinessReqDao;
	
	@Override
	public void save(RealBusinessReq realBusinessReq) {
		realBusinessReqDao.save(realBusinessReq);
	}

}

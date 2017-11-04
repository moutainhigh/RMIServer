package com.hgsoft.ygz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.ygz.dao.NoRealBusinessReqDao;
import com.hgsoft.ygz.entity.NoRealBusinessReq;
import com.hgsoft.ygz.service.NoRealBusinessReqService;

@Service
public class NoRealBusinessReqServiceImpl implements NoRealBusinessReqService{

	@Resource
	private NoRealBusinessReqDao noRealBusinessReqDao;
	
	@Override
	public void save(NoRealBusinessReq noRealBusinessReq) {
		noRealBusinessReqDao.save(noRealBusinessReq);
	}

}

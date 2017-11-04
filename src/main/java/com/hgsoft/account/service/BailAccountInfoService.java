package com.hgsoft.account.service;

import com.hgsoft.account.dao.BailAccountInfoDao;
import com.hgsoft.account.entity.BailAccountInfo;
import com.hgsoft.account.serviceInterface.IBailAccountInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @FileName BailAccountInfoService.java
 * @Description: TODO
 * @author zhengwenhai
 * @Date 2016年5月13日 上午10:34:03 
*/
@Service
public class BailAccountInfoService implements IBailAccountInfoService {

	@Resource
	private BailAccountInfoDao bailAccountInfoDao;
	
	@Override
	public BailAccountInfo findByMainId(Long id) {
		return bailAccountInfoDao.findByCustomerID(id);
	}

}

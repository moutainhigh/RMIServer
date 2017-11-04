package com.hgsoft.clearInterface.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hgsoft.clearInterface.dao.GbissueuserListDao;
import com.hgsoft.clearInterface.serviceInterface.IGbissueuserListService;

@Service
public class GbissueuserListService implements IGbissueuserListService {
	@Resource
	private GbissueuserListDao gbissueuserListDao;

	@Override
	public boolean find(String license, String licenseColor) {
		// TODO Auto-generated method stub
		return gbissueuserListDao.find(license, licenseColor);
	}

}

package com.hgsoft.servlet.service;


import com.hgsoft.sertype.service.SerTypeService;
import com.hgsoft.servlet.dao.TestDao;
import com.hgsoft.servlet.serviceInterface.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestService implements ITestService {

	private static Logger logger = LoggerFactory.getLogger(TestService.class.getName());

	@Resource
	private TestDao testDao;

	@Override
	public boolean testSql() {
		return testDao.testSql();
	}
}

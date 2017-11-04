package com.hgsoft.servlet.dao;

import com.hgsoft.common.dao.BaseDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDao extends BaseDao{
	private static Logger logger = LoggerFactory.getLogger(TestDao.class.getName());

	public boolean testSql() {
		String sql="select * from dual ";
		try {
			List list=queryList(sql);
			return true;
		} catch (Exception e) {
			logger.error("testSql error info======"+e.getMessage());
			return false;
		}

	}
}

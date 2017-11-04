package com.hgsoft.accountC.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hgsoft.accountC.dao.MigrateDetailDao;
import com.hgsoft.accountC.entity.MigrateDetail;
import com.hgsoft.accountC.serviceInterface.IMigrateDetailService;
import com.hgsoft.customer.entity.Customer;
import com.hgsoft.exception.ApplicationException;
import com.hgsoft.utils.Pager;

/**
 * 记帐卡迁移明细
 * @FileName MigrateDetailService.java
 * @author gaosiling
 * @Date 2016年2月25日10:36:10
*/
@Service
public class MigrateDetailService implements IMigrateDetailService{

	private static Logger logger = Logger.getLogger(MigrateService.class.getName());
	
	@Resource
	private MigrateDetailDao migrateDetailDao;
	
	@Override
	public Pager findByPager(Pager pager,MigrateDetail migrateDetail) {
		try {
			return migrateDetailDao.findByPager(pager, migrateDetail);
		} catch (ApplicationException e) {
			logger.error(e.getMessage()+"查询记帐卡迁移明细失败");
			e.printStackTrace();
			throw new ApplicationException("查询记帐卡迁移明细失败");
		}
	}
	
	@Override
	public List<Map<String, Object>> find(MigrateDetail migrateDetail){
		return migrateDetailDao.find(migrateDetail);
	}
}

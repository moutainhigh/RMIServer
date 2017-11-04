package com.hgsoft.system.service;

import com.hgsoft.exception.ApplicationException;
import com.hgsoft.system.dao.AdminLogDao;
import com.hgsoft.system.entity.AdminLog;
import com.hgsoft.system.serviceInterface.IAdminLogService;
import com.hgsoft.utils.Pager;
import com.hgsoft.utils.SequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统操作员日志
 * @author gaosiling
 * 2016-06-17 11:07:54
 */
@Service
public class AdminLogService implements IAdminLogService{
	
	private static Logger logger = Logger.getLogger(IAdminLogService.class.getName());
	
	@Resource
	private AdminLogDao adminLogDao;
	
	@Resource
	SequenceUtil sequenceUtil;
	
	@Override
	public void save(AdminLog adminLog){
		if(adminLog==null){
			logger.error("系统操作员记录日志失败");
			return;
		} 
		logger.info("系统操作员日志记录");
		Long seq = sequenceUtil.getSequenceLong("seq_csmsadminlog_no");
		adminLog.setId(seq);
		adminLogDao.save(adminLog);
	}
	
	@Override
	public Pager findServiceByPager(Pager pager,Long operId,String type,Date startTime,Date endTime) {
		try {
			return adminLogDao.findServiceByPager(pager, operId, type, startTime, endTime);
		} catch (ApplicationException e) {
			throw new ApplicationException("查操作员业务日志失败");
		}
	}

	@Override
	public Pager findByPager(Pager pager, AdminLog adminLog, Date startTime,Date endTime) {
		try {
			return adminLogDao.findByPager(pager, adminLog, startTime, endTime);
		} catch (ApplicationException e) {
			throw new ApplicationException("查操作员日志失败");
		}
	}

	@Override
	public AdminLog findById(Long id) {
		try {
			return adminLogDao.findById(id);
		} catch (ApplicationException e) {
			throw new ApplicationException("查操作员日志失败"+id);
		}
	}

	@Override
	public void saveRemark(AdminLog adminLog) {
		AdminLog newAdminLog = new AdminLog();
		newAdminLog.setId(adminLog.getId());
		newAdminLog.setRemark(adminLog.getRemark());
		try {
			adminLogDao.update(adminLog);
		} catch (ApplicationException e) {
			throw new ApplicationException("更新操作员日志备注失败");
		}
	}

}
